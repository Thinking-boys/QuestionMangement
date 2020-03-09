package com.wyait.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResultDTO {
    private Integer id;
    private String userId;
    //问题ID
    private Integer entityId;
    //问题标题
    private String title;
    //评论内容
    private String content;
    private String createdDate;
    //是否删除0没有删除 1为删除
    private Integer status;
}
