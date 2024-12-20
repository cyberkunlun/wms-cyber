package com.cyber.wms.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cyber.common.core.constant.ServiceConstants;
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
import com.cyber.wms.domain.bo.MovementOrderBo;
import com.cyber.wms.domain.vo.MovementOrderVo;
import com.cyber.wms.service.MovementOrderService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 移库单
 *
 * @author zcc
 * @date 2024-08-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/movementOrder")
public class MovementOrderController extends BaseController {

    private final MovementOrderService movementOrderService;

    /**
     * 查询移库单列表
     */
    @SaCheckPermission("wms:movement:all")
    @GetMapping("/list")
    public TableDataInfo<MovementOrderVo> list(MovementOrderBo bo, PageQuery pageQuery) {
        return movementOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出移库单列表
     */
    @SaCheckPermission("wms:movement:all")
    @Log(title = "移库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MovementOrderBo bo, HttpServletResponse response) {
        List<MovementOrderVo> list = movementOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "移库单", MovementOrderVo.class, response);
    }

    /**
     * 获取移库单详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:movement:all")
    @GetMapping("/{id}")
    public R<MovementOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(movementOrderService.queryById(id));
    }

    /**
     * 新增移库单
     */
    @SaCheckPermission("wms:movement:all")
    @Log(title = "移库单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MovementOrderBo bo) {
        bo.setOrderStatus(ServiceConstants.MovementOrderStatus.PENDING);
        movementOrderService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 修改移库单
     */
    @SaCheckPermission("wms:movement:all")
    @Log(title = "移库单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MovementOrderBo bo) {
        movementOrderService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 移库
     */
    @SaCheckPermission("wms:movement:all")
    @Log(title = "移库单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/move")
    public R<Void> move(@Validated(AddGroup.class) @RequestBody MovementOrderBo bo) {
        bo.setOrderStatus(ServiceConstants.MovementOrderStatus.FINISH);
        movementOrderService.move(bo);
        return R.ok();
    }

    /**
     * 删除移库单
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:movement:all")
    @Log(title = "移库单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空")
                          @PathVariable Long id) {
        movementOrderService.deleteById(id);
        return R.ok();
    }
}
