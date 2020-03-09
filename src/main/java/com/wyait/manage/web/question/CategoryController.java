package com.wyait.manage.web.question;

import com.wyait.manage.entity.CategorySearch;
import com.wyait.manage.service.QueustionService;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    QueustionService queustionService;

    /**
     * 列别列表
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/categoryList", method = RequestMethod.GET)
    public String questionList() {
        return "/question/categoryList";
    }

    /**
     * 分页查询类别列表
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/getCategorys", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getUsers(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit,
                                   CategorySearch categorySearch) {
        PageDataResult pdr = new PageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }

//            // 获取用户和角色列表
            pdr = queustionService.getCategorys( categorySearch.getCategoryName(), page, limit );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdr;
    }


    //新增类别
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    @ResponseBody
    public String addCategory(CategorySearch categorySearch) {
        try {
            boolean b = queustionService.addCategory( categorySearch.getCategoryName() );
            if (b) {
                return "ok";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "新增失败";
        }
        return "系统错误";
    }
}
