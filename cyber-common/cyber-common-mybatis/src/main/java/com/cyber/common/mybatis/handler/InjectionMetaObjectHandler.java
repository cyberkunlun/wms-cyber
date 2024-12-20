package com.cyber.common.mybatis.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cyber.common.core.domain.bo.LoginUser;
import com.cyber.common.core.exception.ServiceException;
import com.cyber.common.core.utils.StringUtils;
import com.cyber.common.mybatis.core.domain.BaseEntity;
import com.cyber.common.mybatis.core.domain.BaseHistoryEntity;
import com.cyber.common.satoken.utils.LoginHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * MP注入处理器
 *
 * @author Lion Li
 * @date 2021/4/25
 */
@Slf4j
public class InjectionMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
                LocalDateTime current = ObjectUtil.isNotNull(baseEntity.getCreateTime())
                    ? baseEntity.getCreateTime() : LocalDateTime.now();;
                baseEntity.setCreateTime(current);
                baseEntity.setUpdateTime(current);
                if (ObjectUtil.isNull(baseEntity.getCreateBy())) {
                    String username = StringUtils.isNotBlank(baseEntity.getCreateBy())
                        ? baseEntity.getCreateBy() : getLoginUsername();
                    // 当前已登录 且 创建人为空 则填充
                    baseEntity.setCreateBy(username);
                    // 当前已登录 且 更新人为空 则填充
                    baseEntity.setUpdateBy(username);
                }
            }

            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseHistoryEntity baseHistoryEntity) {
                LocalDateTime current = ObjectUtil.isNotNull(baseHistoryEntity.getCreateTime())
                    ? baseHistoryEntity.getCreateTime() : LocalDateTime.now();;
                baseHistoryEntity.setCreateTime(current);
            }
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (ObjectUtil.isNotNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity baseEntity) {
                // 更新时间填充(不管为不为空)
                baseEntity.setUpdateTime(LocalDateTime.now());
                String username = getLoginUsername();
                // 当前已登录 更新人填充(不管为不为空)
                if (StringUtils.isNotBlank(username)) {
                    baseEntity.setUpdateBy(username);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("自动注入异常 => " + e.getMessage(), HttpStatus.HTTP_UNAUTHORIZED);
        }
    }

    /**
     * 获取登录用户名
     */
    private String getLoginUsername() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            log.warn("自动注入警告 => 用户未登录");
            return null;
        }
        return ObjectUtil.isNotNull(loginUser) ? loginUser.getUsername() : null;
    }

}
