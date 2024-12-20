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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cyber.wms.domain.bo.ReceiptOrderBo;
import com.cyber.wms.domain.vo.ReceiptOrderVo;
import com.cyber.wms.service.ReceiptOrderService;

import java.util.List;

/**
 * 入库单
 *
 * @author zcc
 * @date 2024-07-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/receiptOrder")
public class ReceiptOrderController extends BaseController {

    private final ReceiptOrderService receiptOrderService;

    /**
     * 查询入库单列表
     */
    @SaCheckPermission("wms:receipt:all")
    @GetMapping("/list")
    public TableDataInfo<ReceiptOrderVo> list(ReceiptOrderBo bo, PageQuery pageQuery) {
        return receiptOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出入库单列表
     */
    @SaCheckPermission("wms:receipt:all")
    @Log(title = "入库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ReceiptOrderBo bo, HttpServletResponse response) {
        List<ReceiptOrderVo> list = receiptOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "入库单", ReceiptOrderVo.class, response);
    }

    /**
     * 获取入库单详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("wms:receipt:all")
    @GetMapping("/{id}")
    public R<ReceiptOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(receiptOrderService.queryById(id));
    }

    /**
     * 暂存入库单
     */
    @SaCheckPermission("wms:receipt:all")
    @Log(title = "入库单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ReceiptOrderBo bo) {
        bo.setOrderStatus(ServiceConstants.ReceiptOrderStatus.PENDING);
        receiptOrderService.insertByBo(bo);
        return R.ok();
    }

    /**
     * 入库
     */
    @SaCheckPermission("wms:receipt:all")
    @Log(title = "入库单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/warehousing")
    public R<Void> doWarehousing(@Validated(AddGroup.class) @RequestBody ReceiptOrderBo bo) {
        bo.setOrderStatus(ServiceConstants.ReceiptOrderStatus.FINISH);
        receiptOrderService.receive(bo);
        return R.ok();
    }

    /**
     * 修改入库单
     */
    @SaCheckPermission("wms:receipt:all")
    @Log(title = "入库单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ReceiptOrderBo bo) {
        receiptOrderService.updateByBo(bo);
        return R.ok();
    }

    /**
     * 删除入库单
     *
     * @param id 主键串
     */
    @SaCheckPermission("wms:receipt:all")
    @Log(title = "入库单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> remove(@NotNull(message = "主键不能为空")
                          @PathVariable Long id) {
        receiptOrderService.deleteById(id);
        return R.ok();
    }
}
