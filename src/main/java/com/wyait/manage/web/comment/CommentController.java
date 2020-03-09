package com.wyait.manage.web.comment;

import com.wyait.manage.entity.CommentSearchDTO;
import com.wyait.manage.pojo.Comment;
import com.wyait.manage.service.CommentService;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    /**
     * 评论列表
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/commentList", method = RequestMethod.GET)
    public String commentList() {
        return "/comment/commentList";
    }


    /**
     * 分页查询评论列表
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/getComments", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getUsers(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit, CommentSearchDTO commentSearchDTO) {
//		ErrorController
        PageDataResult pdr = new PageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            pdr=commentService.getComments( commentSearchDTO,page,limit );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdr;
    }
    /**
     * 更改评论是否被删除状态
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/setDeleteComment", method = RequestMethod.POST)
    @ResponseBody
    public String setDeleteQuestion(@RequestParam("id") Integer id,
                                    @RequestParam("isDelete") Integer isDelete) {
        String msg = "";
        try {
            if (null == id || null == isDelete) {
                return "请求参数有误，请您稍后再试";
            }
            //查对应评论是否存在
            Comment comment = commentService.getCommentById( id, 1 );
            if (null == comment) {
                return "您要修改评论状态的评论不存在";
            }
            boolean b = commentService.updateDeleteStatus( isDelete, id );
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
