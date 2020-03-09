package com.wyait.manage.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class categoryQuestion {
    private int id;
    private int categoryId;
    private int questionId;
    private String categoryName;
}
