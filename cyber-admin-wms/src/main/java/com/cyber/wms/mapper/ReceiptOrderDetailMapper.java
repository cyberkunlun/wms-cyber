package com.cyber.wms.mapper;

import com.cyber.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;
import com.cyber.wms.domain.entity.ReceiptOrderDetail;
import com.cyber.wms.domain.vo.ReceiptOrderDetailVo;

import java.util.List;

/**
 * 入库单详情Mapper接口
 *
 * @author zcc
 * @date 2024-07-19
 */
public interface ReceiptOrderDetailMapper extends BaseMapperPlus<ReceiptOrderDetail, ReceiptOrderDetailVo> {

}
