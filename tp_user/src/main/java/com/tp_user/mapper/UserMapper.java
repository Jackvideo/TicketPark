package com.tp_user.mapper;

import com.tp_user.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-10 11:17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
