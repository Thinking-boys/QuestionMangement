package com.wyait.manage.dao;

import com.wyait.manage.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
    int addOne(Users users);

    Users getUserById(@Param( "userId" ) int userId);

    Users getUsersByUsername(@Param( "username" ) String username);

}
