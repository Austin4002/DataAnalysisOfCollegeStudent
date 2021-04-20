package com.ngx.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ngx.boot.bean.StuConsume;

import java.util.List;

public interface StuConsumeService extends IService<StuConsume> {

    List<StuConsume> getStuNoDistinct();

    double getAvgConsumeMoneyByNo(String stuNo);

    double getMaxConsumeMoneyByNo(String stuNo);

    Integer getFrequencyConsumeData(String stuNo);

    Integer getAmountByRestaurant(String year, String month,Integer restaurantNumber);

    Integer getAmountByRestaurantAndWindow(String year, String month, Integer restaurantNumber, Integer windowNumber);

    Integer getAmountSumByIdAndMonth(String stuNo, int month);
}
