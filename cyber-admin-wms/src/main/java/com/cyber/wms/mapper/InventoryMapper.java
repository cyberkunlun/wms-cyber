package com.cyber.wms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyber.common.mybatis.core.mapper.BaseMapperPlus;
import com.cyber.wms.domain.bo.InventoryBo;
import com.cyber.wms.domain.entity.Inventory;
import com.cyber.wms.domain.vo.InventoryVo;
import org.apache.ibatis.annotations.Param;

/**
 * 库存Mapper接口
 *
 * @author zcc
 * @date 2024-07-19
 */
public interface InventoryMapper extends BaseMapperPlus<Inventory, InventoryVo> {

    Page<InventoryVo> queryItemBoardList(Page<InventoryVo> page, @Param("bo") InventoryBo bo);
    Page<InventoryVo> queryWarehouseBoardList(Page<InventoryVo> page, @Param("bo") InventoryBo bo);

}
