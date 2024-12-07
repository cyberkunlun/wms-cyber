package com.cyber.wms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyber.common.mybatis.core.mapper.BaseMapperPlus;
import com.cyber.wms.domain.bo.ItemSkuBo;
import com.cyber.wms.domain.entity.ItemSku;
import com.cyber.wms.domain.vo.ItemSkuMapVo;
import com.cyber.wms.domain.vo.ItemSkuVo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface ItemSkuMapper extends BaseMapperPlus<ItemSku, ItemSkuVo> {

    IPage<ItemSkuMapVo> selectByBo(IPage<ItemSkuVo> page, @Param("bo") ItemSkuBo bo);

    List<ItemSkuMapVo> queryItemSkuMapVos(Collection<Long> ids);

    ItemSkuMapVo queryItemSkuMapVo(Long id);
}
