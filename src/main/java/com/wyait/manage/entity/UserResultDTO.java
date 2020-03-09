package com.wyait.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResultDTO {
    private long id;
    private String username;
    private String password;
    private String salt;
    private String headUrl;
    //用户状态 0为正常 1为被管理员禁用
    private Integer status;
    private Integer questionCount;
    private Integer commentCount;
}
