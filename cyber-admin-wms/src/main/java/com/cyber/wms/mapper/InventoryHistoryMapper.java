package com.cyber.wms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyber.common.mybatis.core.mapper.BaseMapperPlus;
import com.cyber.wms.domain.bo.InventoryHistoryBo;
import com.cyber.wms.domain.entity.InventoryHistory;
import com.cyber.wms.domain.vo.InventoryHistoryVo;
import org.apache.ibatis.annotations.Param;

/**
 * 库存记录Mapper接口
 *
 * @author zcc
 * @date 2024-07-22
 */
public interface InventoryHistoryMapper extends BaseMapperPlus<InventoryHistory, InventoryHistoryVo> {

    Page<InventoryHistoryVo> selectVoPageByBo(Page<Object> page, @Param("bo") InventoryHistoryBo bo);
}
