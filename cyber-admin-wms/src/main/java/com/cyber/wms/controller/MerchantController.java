package com.cyber.wms.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cyber.common.core.domain.R;
import com.cyber.common.core.validate.AddGroup;
import com.cyber.common.core.validate.EditGroup;
import com.cyber.common.excel.utils.ExcelUtil;
import com.cyber.common.idempotent.annotation.RepeatSubmit;
import com.cyber.common.log.annotation.Log;
import com.cyber.common.log.enums.BusinessType;
import com.cyber.common.mybatis.core.page.PageQuery;
import com.cyber.common.mybatis.core.page.TableDataInfo;
import com.cyber.common.web.core.BaseController;
import com.cyber.wms.domain.vo.MerchantVo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cyber.wms.domain.bo.MerchantBo;
import com.cyber.wms.service.MerchantService;

import java.util.List;

/**
 * 往来单位
 *
 * @author zcc
 * @date 2024-07-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/merchant")
public class MerchantController extends BaseController {

    private final MerchantService merchantService;

    /**
     * 查询往来单位列表
     */
    @SaCheckPermission("wms:merchant:list")
    @GetMapping("/list")
    public TableDataInfo<MerchantVo> list(MerchantBo bo, PageQuery pageQuery) {
        return merchantService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询往来单位列表
     */
    @SaCheckPermission("wms:merchant:list")
    @GetMapping("/listNoPage")
    public R<List<MerchantVo>> listNoPage(MerchantBo bo) {
        return R.ok(merchantService.queryList(bo));
    }

    /**
     * 导出往来单位列表
     */
    @SaCheckPermission("wms:merchant:list")
    @Log(title = "往来单位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MerchantBo bo, HttpServletResponse response) {
        List<MerchantVo> list = merchantService.queryList(bo);
        ExcelUtil.exportExcel(list, "往来单位", MerchantVo.class, response);
    }

    /**
     * 获取往来单位详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:merchant:list")
    @GetMapping("/{id}")
    public R<MerchantVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(merchantService.queryById(id));
    }

    /**
     * 新增往来单位
     */
    @SaCheckPermission("wms:merchant:edit")
    @Log(title = "往来单位", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MerchantBo bo) {
        merchantService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改往来单位
     */
    @SaCheckPermission("wms:merchant:edit")
    @Log(title = "往来单位", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MerchantBo bo) {
        merchantService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除往来单位
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:merchant:edit")
    @Log(title = "往来单位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空")
                          @PathVariable Long id) {
        merchantService.deleteById(id);
        return R.ok();
    }
}
