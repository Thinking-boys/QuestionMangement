package com.wyait.manage.service;

import com.wyait.manage.entity.CommentSearchDTO;
import com.wyait.manage.entity.QuestionSearchDao;
import com.wyait.manage.pojo.Comment;
import com.wyait.manage.pojo.Question;
import com.wyait.manage.pojo.categoryQuestion;
import com.wyait.manage.utils.PageDataResult;

import java.util.List;

public interface CommentService {
    PageDataResult getComments(CommentSearchDTO commentSearchDTO, int page, int limit);

    //根据评论的id获取评论的信息
    Comment getCommentById(int commentId, int type);

    boolean updateDeleteStatus(int status,int id);

}
