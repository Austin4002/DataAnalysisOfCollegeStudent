package com.ngx.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ngx.boot.bean.StuConsume;
import com.ngx.boot.service.StuConsumeService;
import com.ngx.boot.vo.Result;
import com.ngx.boot.vo.restaurant.ConType;
import com.ngx.boot.vo.restaurant.Restaurant;
import com.ngx.boot.vo.restaurant.WindowType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 牛庚新
 * @date :
 */

@RestController
@Slf4j
@RequestMapping("/res")
public class RestaurantController {

    @Autowired
    private StuConsumeService stuConsumeService;

    @GetMapping("/turnover")
    public Result<Restaurant> analysisRestaurant(
            @RequestParam(value = "year", required = true) String year,
            @RequestParam(value = "month", required = false) String month) {
//        System.out.println("month--->"+month);
        Result<Restaurant> rs = new Result<>(500, "error");

        Restaurant restaurant = new Restaurant();


        QueryWrapper<StuConsume> restaurantNumberWrapper = new QueryWrapper<>();
        restaurantNumberWrapper.select("DISTINCT con_restaurant");
        //查询每个餐厅号的消费频次
        List<ConType> conFre = new ArrayList<>();
        //查询每个餐厅号的消费金额
        List<ConType> conAmount = new ArrayList<>();
        //查询每个餐厅的窗口消费频次
        ArrayList<WindowType> windowFre = new ArrayList<>();
        //查询每个餐厅的窗口消费金额
        ArrayList<WindowType> windowAmount = new ArrayList<>();

        //一共有restaurantNumber个餐厅
        int restaurantNumber = stuConsumeService.count(restaurantNumberWrapper);

        for (int i = 1; i <= restaurantNumber; i++) {
            QueryWrapper<StuConsume> restaurantFrequencyWrapper = new QueryWrapper<>();
            restaurantFrequencyWrapper.eq("stu_year", year);
            restaurantFrequencyWrapper.eq("con_restaurant", i);
            if (month != null && !month.equals("")) {
//                System.out.println("month不是空");
                restaurantFrequencyWrapper.eq("con_month", month);
            }

            int restaurantFrequency = stuConsumeService.count(restaurantFrequencyWrapper);
            int restaurantAmount = stuConsumeService.getAmountByRestaurant(year, month, i);
            //第i号餐厅消费频次
            conFre.add(new ConType(i + "号餐厅消费频次", restaurantFrequency));
            //第i号餐厅消费金额
            conAmount.add(new ConType(i + "号餐厅消费金额", restaurantAmount));


            QueryWrapper<StuConsume> windowNumberWrapper = new QueryWrapper<>();
            windowNumberWrapper.eq("con_restaurant",i);
            windowNumberWrapper.select("DISTINCT con_window");

            //第i号餐厅有windouNumer个窗口
            int windouNumer = stuConsumeService.count(windowNumberWrapper);
            //查询每个窗口的消费频次
            //循环查询
            for (int j = 1; j <=windouNumer ; j++) {
                QueryWrapper<StuConsume> windowFrequencyWrapper = new QueryWrapper<>();
                windowFrequencyWrapper.eq("stu_year",year);
                if (month != null && !month.equals("")) {
                    windowFrequencyWrapper.eq("con_month", month);
                }
                windowFrequencyWrapper.eq("con_restaurant", i);
                windowFrequencyWrapper.eq("con_window",j);

//                windowFre.add(new () );
                //i号餐厅j号窗口消费频次
                int windowFreCount = stuConsumeService.count(windowFrequencyWrapper);
                //i号餐厅j号窗口消费金额
                int windowAmountCount = stuConsumeService.getAmountByRestaurantAndWindow(year, month, i, j);
                windowFre.add(new WindowType(j+"号窗口",windowFreCount,i+"号餐厅消费频次" ));
                windowAmount.add(new WindowType(j+"号窗口",windowAmountCount,i+"号餐厅消费金额" ));
            }

        }
        restaurant.setConFre(conFre);
        restaurant.setConAmount(conAmount);
        restaurant.setWindowFre(windowFre);
        restaurant.setWindowAmount(windowAmount);

        rs.setCode(200);
        rs.setMsg("ok");
        rs.setData(restaurant);

        return rs;
    }


}
