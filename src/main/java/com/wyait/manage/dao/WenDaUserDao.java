package com.wyait.manage.dao;


import com.wyait.manage.entity.UserResultDTO;
import com.wyait.manage.entity.WendaUserSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WenDaUserDao {
    List<UserResultDTO> getUsers(@Param("wendaUserSearchDTO") WendaUserSearchDTO wendaUserSearchDTO);

    int updateDeleteStatus(@Param("isDelete") int isDelete, @Param("id") int id);

}
