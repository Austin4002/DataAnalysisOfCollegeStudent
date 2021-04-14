package com.ngx.boot.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class BehFigure {

    // 行为标签
    @TableId
    private String behaviorTag;
    // 行为值
    private String behaviorValue;


}