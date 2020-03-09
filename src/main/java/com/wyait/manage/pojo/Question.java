package com.wyait.manage.pojo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by nowcoder on 2016/7/15.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private long id;
    private String title;
    private String content;
    private Date createdDate;
    private int userId;
    private int commentCount;
    private int likeCount;
    private BigDecimal grade;
    private int followCount;
    //0没有删除
    private int isDelete;
    //1是置顶
    private int isTop;


}
