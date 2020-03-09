package com.wyait.manage.service;

import com.wyait.manage.entity.QuestionSearchDao;
import com.wyait.manage.pojo.Question;
import com.wyait.manage.pojo.categoryQuestion;
import com.wyait.manage.utils.PageDataResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QueustionService {
    PageDataResult getQuestions(QuestionSearchDao questionSearchDao, int page, int limit);
    Question getQuestionById(int id);

    boolean updateDeleteStatus(int status,int id);

    boolean updateTopStatus(int status,int id);
    //查当前问题所属于哪些类别
    List<categoryQuestion> getCategoryNameByQuestion(int questionId);

    //获取问题类别列表数据
    PageDataResult getCategorys(String categoryName, int page, int limit);
    boolean addCategory(String categoryName);
}
