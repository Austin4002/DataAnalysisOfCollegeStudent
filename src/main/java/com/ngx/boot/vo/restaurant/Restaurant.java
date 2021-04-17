package com.ngx.boot.vo.restaurant;

import lombok.Data;

import java.util.List;

/**
 * @author : 朱坤
 * @date :
 */
@Data
public class Restaurant {

    //消费频次
    private List<ConType> conFre;
    //消费金额
    private List<ConType> conAmount;
    //各窗口消费频次
    private List<WindowType> windowFre;
    //个窗口消费金额
    private List<WindowType> windowAmount;


}
