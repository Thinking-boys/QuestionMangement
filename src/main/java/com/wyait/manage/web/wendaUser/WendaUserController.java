package com.wyait.manage.web.wendaUser;

import com.wyait.manage.entity.CommentSearchDTO;
import com.wyait.manage.entity.WendaUserSearchDTO;
import com.wyait.manage.pojo.Comment;
import com.wyait.manage.service.CommentService;
import com.wyait.manage.service.UserService;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wendaUser")
public class WendaUserController {

    @Autowired
    UserService userService;

    /**
     * 评论列表
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String commentList() {
        return "/wendaUser/userList";
    }


    /**
     * 分页查询用户列表
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/getUsers", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getUsers(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit, WendaUserSearchDTO wendaUserSearchDTO) {
//		ErrorController
        PageDataResult pdr = new PageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            pdr = userService.getWenDaUsers( wendaUserSearchDTO, page, limit );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdr;
    }

    /**
     * 用户状态修改 是否禁止
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/setDeleteUser", method = RequestMethod.POST)
    @ResponseBody
    public String setDeleteQuestion(@RequestParam("id") Integer id,
                                    @RequestParam("isDelete") Integer isDelete) {
        String msg = "";
        try {
            if (null == id || null == isDelete) {
                return "请求参数有误，请您稍后再试";
            }
            boolean b = userService.updateDeleteStatus( isDelete, id );
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

}
