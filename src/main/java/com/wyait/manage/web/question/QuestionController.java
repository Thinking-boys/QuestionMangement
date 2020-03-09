package com.wyait.manage.web.question;

import com.wyait.manage.entity.QuestionSearchDao;
import com.wyait.manage.entity.UserSearchDTO;
import com.wyait.manage.pojo.Question;
import com.wyait.manage.pojo.Role;
import com.wyait.manage.pojo.categoryQuestion;
import com.wyait.manage.service.AuthService;
import com.wyait.manage.service.QueustionService;
import com.wyait.manage.utils.PageDataResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QueustionService queustionService;

    /**
     * 问题列表
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/questionList", method = RequestMethod.GET)
    public String questionList() {
        return "/question/questionList";
    }

    /**
     * 分页查询问题列表
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/getQuestions", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getUsers(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit, QuestionSearchDao questionSearchDao) {
//		ErrorController
        PageDataResult pdr = new PageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            // 获取用户和角色列表
            pdr = queustionService.getQuestions( questionSearchDao, page, limit );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdr;
    }

    /**
     * 更改问题是否被删除状态
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/setDeleteQuestion", method = RequestMethod.POST)
    @ResponseBody
    public String setDeleteQuestion(@RequestParam("id") Integer id,
                                    @RequestParam("isDelete") Integer isDelete) {
        String msg = "";
        try {
            if (null == id || null == isDelete) {
                return "请求参数有误，请您稍后再试";
            }
            //查对应问题是否存在
            Question quest = queustionService.getQuestionById( id );
            if (null == quest) {
                return "您要修改问题状态的问题不存在";
            }
            boolean b = queustionService.updateDeleteStatus( isDelete, id );
            if (!b) {
                msg = "操作异常，请您稍后再试！";
            }
            msg = "ok";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "操作异常，请您稍后再试！";
        }
        return msg;
    }

    /**
     * 更改问题是否置顶状态
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/setTopQuestion", method = RequestMethod.POST)
    @ResponseBody
    public String setTopQuestion(@RequestParam("id") Integer id,
                                 @RequestParam("isTop") Integer isTop) {
        String msg = "";
        try {
            if (null == id || null == isTop) {
                return "请求参数有误，请您稍后再试";
            }
            //查对应问题是否存在
            Question quest = queustionService.getQuestionById( id );
            if (null == quest) {
                return "您要修改问题状态的问题不存在";
            }
            boolean b = queustionService.updateTopStatus( isTop, id );
            if (!b) {
                msg = "操作异常，请您稍后再试！";
            }
            msg = "ok";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "操作异常，请您稍后再试！";
        }
        return msg;
    }

    /**
     * 查询问题数据 用于运营回答问题
     *
     * @return map
     */
    @RequestMapping(value = "/getQuestionData", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserAndRoles(@RequestParam("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                map.put( "msg", "请求参数有误，请您稍后再试" );
                return map;
            }
            // 查询问题
            Question quest = queustionService.getQuestionById( id );
            if (null != quest) {
                map.put( "quest", quest );
                //查问题所属于的类别
                List<categoryQuestion> categoryNameByQuestion = queustionService.getCategoryNameByQuestion( id );
                if (categoryNameByQuestion != null && categoryNameByQuestion.size() > 0) {
                    map.put( "category", categoryNameByQuestion );
                }
                map.put( "msg", "ok" );
            } else {
                map.put( "msg", "查询用户信息有误，请您稍后再试" );
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put( "msg", "查询用户错误，请您稍后再试" );
        }
        return map;
    }
}
