package com.wyait.manage.dao;


import com.wyait.manage.entity.CommentResultDTO;
import com.wyait.manage.entity.CommentSearchDTO;
import com.wyait.manage.entity.QuestionDTO;
import com.wyait.manage.entity.QuestionSearchDao;
import com.wyait.manage.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao {
    /**
     * 分页查询问题数据
     * @return
     */
    List<CommentResultDTO> getComments(@Param("commentSearchDTO") CommentSearchDTO commentSearchDTO);

    Comment getCommentById(@Param( "id" ) int id,@Param( "entityType" ) int entityType);

    int updateDeleteStatus(@Param("isDelete") int isDelete, @Param("id") int id);

    //获取用户发布评论数量
    Integer getCommentCountByUserId(@Param( "userId" ) int userId);
}
