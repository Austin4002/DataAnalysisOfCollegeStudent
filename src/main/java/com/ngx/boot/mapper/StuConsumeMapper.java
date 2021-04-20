package com.ngx.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ngx.boot.bean.StuConsume;

import java.util.List;

public interface StuConsumeMapper extends BaseMapper<StuConsume> {


    List<StuConsume> getStuNoDistinct();

    double getAvgConsumeMoneyByNo(String stuNo);

    double getMaxConsumeMoneyByNo(String stuNo);

    Integer getAmountByRestaurant(String year, String month,Integer restaurantNumber);

    Integer getAmountByRestaurantAndWindow(String year, String month, Integer restaurantNumber, Integer windowNumber);

    Integer getAmountSumByIdAndMonth(String stuNo, int month);
}
