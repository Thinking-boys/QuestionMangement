package com.wyait.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSearchDao {
    private Integer page;
    private Integer limit;
    private String title;
    private String insertTimeStart;
    private String insertTimeEnd;
    private String user;
}
