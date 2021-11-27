package com.rue.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rue.bean.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author ruetrash
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}