package com.tp_order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tp_order.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:19
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Order> {

}
