package com.wyait.manage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.wyait.manage.dao.CommentDao;
import com.wyait.manage.dao.QuestionDao;
import com.wyait.manage.dao.UserDao;
import com.wyait.manage.entity.CommentResultDTO;
import com.wyait.manage.entity.CommentSearchDTO;
import com.wyait.manage.pojo.Comment;
import com.wyait.manage.pojo.Users;
import com.wyait.manage.utils.DateUtil;
import com.wyait.manage.utils.PageDataResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    UserDao userDao;
    @Autowired
    CommentDao commentDao;
    @Autowired
    QuestionDao questionDao;

    @Override
    public PageDataResult getComments(CommentSearchDTO commentSearchDTO, int page, int limit) {
        //时间处理
        if (null != commentSearchDTO) {
            if (StringUtils.isNotEmpty( commentSearchDTO.getInsertTimeStart() )
                    && StringUtils.isEmpty( commentSearchDTO.getInsertTimeEnd() )) {
                commentSearchDTO.setInsertTimeEnd( DateUtil.date2Str( new Date(), null ) );
            } else if (StringUtils.isEmpty( commentSearchDTO.getInsertTimeStart() )
                    && StringUtils.isNotEmpty( commentSearchDTO.getInsertTimeEnd() )) {
                commentSearchDTO.setInsertTimeStart( DateUtil.date2Str( new Date( Integer.MIN_VALUE ), null ) );
            }
            if (StringUtils.isNotEmpty( commentSearchDTO.getInsertTimeStart() )
                    && StringUtils.isNotEmpty( commentSearchDTO.getInsertTimeEnd() )) {
                if (commentSearchDTO.getInsertTimeEnd().compareTo(
                        commentSearchDTO.getInsertTimeStart() ) < 0) {
                    String temp = commentSearchDTO.getInsertTimeStart();
                    commentSearchDTO.setInsertTimeStart( commentSearchDTO.getInsertTimeEnd() );
                    commentSearchDTO.setInsertTimeEnd( temp );
                }
            }
        }
        //将搜索的用户名转为用户id
        if (commentSearchDTO.getUsername() != null && StringUtils.isNotBlank( commentSearchDTO.getUsername() )) {
            Users usersByUsername = userDao.getUsersByUsername( commentSearchDTO.getUsername() );
            commentSearchDTO.setUsername( String.valueOf( usersByUsername.getId() ) );
        }
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage( page, limit );
        List<CommentResultDTO> comments = commentDao.getComments( commentSearchDTO );
        PageInfo<CommentResultDTO> pageInfo = new PageInfo<>( comments );
//        // 设置获取到的总记录数total：
        pdr.setTotals( Long.valueOf( pageInfo.getTotal() ).intValue() );
        if(comments!=null && comments.size()>0){
            for (CommentResultDTO c:comments){
                c.setUserId( userDao.getUserById( Integer.valueOf(c.getUserId()) ).getUsername() );
                c.setTitle( questionDao.getQuestionById( c.getEntityId() ).getTitle() );
            }
        }
        pdr.setList( comments );
        return pdr;
    }

    @Override
    public Comment getCommentById(int commentId, int type) {
        return commentDao.getCommentById( commentId,type );
    }

    @Override
    public boolean updateDeleteStatus(int status, int id) {
        return commentDao.updateDeleteStatus( status,id )>0?true:false;
    }
}
