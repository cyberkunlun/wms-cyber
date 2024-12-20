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
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cyber.wms.domain.bo.ItemBo;
import com.cyber.wms.domain.vo.ItemVo;
import com.cyber.wms.service.ItemService;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wms/item")
public class ItemController extends BaseController {

    private final ItemService itemService;

    /**
     * 查询物料列表
     */
    @GetMapping("/list")
    @SaCheckPermission("wms:item:list")
    public TableDataInfo<ItemVo> list(ItemBo bo, PageQuery pageQuery) {
        return itemService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询物料列表
     */
    @GetMapping("/listNoPage")
    @SaCheckPermission("wms:item:list")
    public R<List<ItemVo>> list(ItemBo bo) {
        return R.ok(itemService.queryList(bo));
    }

    /**
     * 导出物料列表
     */
    @Log(title = "物料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @SaCheckPermission("wms:item:list")
    public void export(ItemBo bo, HttpServletResponse response) {
        List<ItemVo> list = itemService.queryList(bo);
        ExcelUtil.exportExcel(list, "物料", ItemVo.class, response);
    }

    /**
     * 获取物料详细信息
     *
     * @param id 主键
     */
    @GetMapping("/{id}")
    @SaCheckPermission("wms:item:list")
    public R<ItemVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(itemService.queryById(id));
    }

    /**
     * 新增物料
     */
    @Log(title = "物料", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @SaCheckPermission("wms:item:edit")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ItemBo form) {
        itemService.insertByForm(form);
        return R.ok();
    }
    /**
     * 修改物料
     */
    @Log(title = "物料", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    @SaCheckPermission("wms:item:edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ItemBo form) {
        itemService.updateByForm(form);
        return R.ok();
    }

    /**
     * 删除物料
     *
     * @param id 主键
     */
    @Log(title = "物料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    @SaCheckPermission("wms:item:edit")
    public R<Void> remove(@NotNull(message = "主键不能为空")
                          @PathVariable Long id) {
        itemService.deleteById(id);
        return R.ok();
    }
}
