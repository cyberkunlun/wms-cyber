package com.cyber.wms.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.cyber.common.mybatis.core.domain.BaseVo;
import com.cyber.wms.domain.entity.ItemBrand;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;

/**
 * 商品品牌视图对象 wms_item_brand
 *
 * @author zcc
 * @date 2024-07-30
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ItemBrand.class)
public class ItemBrandVo extends BaseVo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ExcelProperty(value = "")
    private Long id;

    /**
     * 品牌名称
     */
    @ExcelProperty(value = "品牌名称")
    private String brandName;


}
