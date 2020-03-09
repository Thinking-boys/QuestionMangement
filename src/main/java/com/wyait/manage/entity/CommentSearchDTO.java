package com.wyait.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentSearchDTO {
    private Integer page;
    private Integer limit;
    private String username;
    private Integer questionId;
    private String insertTimeStart;
    private String insertTimeEnd;
}
