package com.wyait.manage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.QuestionDao;
import com.wyait.manage.dao.UserDao;
import com.wyait.manage.entity.CategoryQuestion;
import com.wyait.manage.entity.QuestionDTO;
import com.wyait.manage.entity.QuestionSearchDao;
import com.wyait.manage.pojo.Question;
import com.wyait.manage.pojo.Users;
import com.wyait.manage.pojo.categoryQuestion;
import com.wyait.manage.utils.DateUtil;
import com.wyait.manage.utils.PageDataResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionServiceImpl implements QueustionService {

    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserDao userDao;

    @Override
    public PageDataResult getQuestions(QuestionSearchDao questionSearch, int page, int limit) {
//        // 时间处理
        if (null != questionSearch) {
            if (StringUtils.isNotEmpty( questionSearch.getInsertTimeStart() )
                    && StringUtils.isEmpty( questionSearch.getInsertTimeEnd() )) {
                questionSearch.setInsertTimeEnd( DateUtil.date2Str( new Date(), null ) );
            } else if (StringUtils.isEmpty( questionSearch.getInsertTimeStart() )
                    && StringUtils.isNotEmpty( questionSearch.getInsertTimeEnd() )) {
                questionSearch.setInsertTimeStart( DateUtil.date2Str( new Date( Integer.MIN_VALUE ), null ) );
            }
            if (StringUtils.isNotEmpty( questionSearch.getInsertTimeStart() )
                    && StringUtils.isNotEmpty( questionSearch.getInsertTimeEnd() )) {
                if (questionSearch.getInsertTimeEnd().compareTo(
                        questionSearch.getInsertTimeStart() ) < 0) {
                    String temp = questionSearch.getInsertTimeStart();
                    questionSearch.setInsertTimeStart( questionSearch.getInsertTimeEnd() );
                    questionSearch.setInsertTimeEnd( temp );
                }
            }
        }
        //将搜索的用户名转为用户id
        if(questionSearch.getUser()!=null && StringUtils.isNotBlank( questionSearch.getUser() )){
            Users usersByUsername = userDao.getUsersByUsername( questionSearch.getUser() );
            questionSearch.setUser( String.valueOf(usersByUsername.getId()) );
        }
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage( page, limit );
        List<QuestionDTO> questions = questionDao.getQuestions( questionSearch );
//        // 获取分页查询后的数据
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>( questions );
//        // 设置获取到的总记录数total：
        pdr.setTotals( Long.valueOf( pageInfo.getTotal() ).intValue() );
//        // 将问题所属用户名称提取到对应的字段中
        if(questions!=null && questions.size()>0){
            for (QuestionDTO q:questions){
                Users uu = userDao.getUserById( Integer.parseInt( q.getUserId() ) );
                q.setUserId( uu.getUsername() );
            }
        }
        pdr.setList( questions );
        return pdr;
    }
    @Override
    public Question getQuestionById(int id) {
        return questionDao.getQuestionById( id );
    }

    @Override
    public boolean updateDeleteStatus(int status, int id) {
        return questionDao.updateDeleteStatus( status,id )>0?true:false;
    }

    @Override
    public boolean updateTopStatus(int status, int id) {
        return questionDao.updateTopStatus( status,id )>0?true:false;
    }

    @Override
    public List<categoryQuestion> getCategoryNameByQuestion(int questionId) {
        List<categoryQuestion> cname=questionDao.getCategoryNameByQuestion(questionId);
        for(categoryQuestion c:cname){
            c.setCategoryName( questionDao.getCategoryNameById(c.getCategoryId()).getCategoryName() );
        }
        return cname;
    }

    @Override
    public PageDataResult getCategorys(String categoryName, int page, int limit) {
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage( page, limit );
        List<CategoryQuestion> categoryQuestions = questionDao.categoryQuestionCount( categoryName );
        PageInfo<CategoryQuestion> pageInfo=new PageInfo<>( categoryQuestions );
        pdr.setTotals( Long.valueOf( pageInfo.getTotal() ).intValue() );
        pdr.setList( categoryQuestions );
        return pdr;
    }

    @Override
    public boolean addCategory(String categoryName) {
        return questionDao.addCategory( categoryName )>0?true:false;
    }

}
