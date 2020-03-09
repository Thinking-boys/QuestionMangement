package com.wyait.manage.dao;


import com.wyait.manage.entity.CategoryQuestion;
import com.wyait.manage.entity.QuestionDTO;
import com.wyait.manage.entity.QuestionSearchDao;
import com.wyait.manage.pojo.Category;
import com.wyait.manage.pojo.Question;
import com.wyait.manage.pojo.categoryQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionDao {
    /**
     * 分页查询问题数据
     * @return
     */
    List<QuestionDTO> getQuestions(@Param("questionSearch") QuestionSearchDao questionSearch);

    Question getQuestionById(@Param( "id" ) int id);

    int updateDeleteStatus(@Param("isDelete") int isDelete, @Param("id") int id);

    int updateTopStatus(@Param( "isTop" ) int isTop,@Param( "id" ) int id);

    //根据问题id查其所属类别
    List<categoryQuestion> getCategoryNameByQuestion(@Param( "questionId" ) int questionId);

    Category getCategoryNameById(@Param( "id" ) int id);

    //擦汗类别数据
    List<CategoryQuestion> categoryQuestionCount(@Param( "categoryName" ) String categoryName);

    //新增类别
    int addCategory(@Param( "categoryName" ) String categoryName);

    //获取用户发布问题数量
    Integer getQuestionCountByUserId(@Param( "userId" ) int userId);
}
