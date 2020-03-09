package com.wyait.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//后台查询返回数据
public class QuestionDTO {
    private long id;
    private String title;
    private String content;
    private String createdDate;
    private String userId;
    private int commentCount;
    private int likeCount;
    private BigDecimal grade;
    private int followCount;
    //0没有删除
    private int isDelete;
    //1是置顶
    private int isTop;




}
