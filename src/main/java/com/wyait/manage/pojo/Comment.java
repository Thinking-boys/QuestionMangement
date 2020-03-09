package com.wyait.manage.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;
    private String content;
    private int userId;
    private int entityId;
    private int entityType;
    private String createdDate;
    //帖子的状态 0为已删除   1为成功发表
    private int status;
}
