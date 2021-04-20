package com.ngx.boot.vo.Records;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StuInfoss {

    @NotNull
    private Integer current;

    private String major;

    @NotNull
    private Integer pageSize;

    private String stuId;

}
