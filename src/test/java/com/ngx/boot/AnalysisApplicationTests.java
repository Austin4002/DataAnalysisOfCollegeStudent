package com.ngx.boot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ngx.boot.bean.BookInfo;
import com.ngx.boot.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class AnalysisApplicationTests {

    private Map<String, Double> clusterCenter = new HashMap<>();

    int precimal = 1;//保留的小数位数
    double min = 2.7;//最小值
    double max = 4.4;//最大

    @Autowired
    private StuBorrowService stuBorrowService;
    @Autowired
    private BookInfoService bookInfoService;
    @Autowired
    private StuCheckService stuCheckService;

    @Autowired
    private StuInfoService stuInfoService;

    @Autowired
    private StuScoreService stuScoreService;


//    @Autowired
//    private RedisTemplate redisTemplate;

    @Test
    public void testWrapper() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("DISTINCT book_type");

        List<BookInfo> list = bookInfoService.list(wrapper);

        list.forEach(item -> {
            String bookType = item.getBookType();
            QueryWrapper queryWrapper = new QueryWrapper();
            Map<String, String> rvMap = new HashMap<>();
            rvMap.put("stu_no", "201919201");
            rvMap.put("book_type", bookType);
            queryWrapper.allEq(rvMap);

            int count = stuBorrowService.count(queryWrapper);
            System.out.println(bookType + ":" + count);
        });

    }



    @Test
    public void testMaxAll(){
        List<Integer> maxTime = stuCheckService.getAllMaxTime();
        maxTime.forEach(System.out::println);
    }


    @Test
    public void testTimeCount() {
        double avgTimeCount = stuCheckService.getAvgTimeCount("201919222");
    }

    @Test
    public void gradecount(){

        List<String> gradelist = stuInfoService.getGradeNumber();
        gradelist.forEach(System.out::println);
        AtomicInteger count1 = new AtomicInteger();
        AtomicInteger count2= new AtomicInteger();
        AtomicInteger count3= new AtomicInteger();
        AtomicInteger count4= new AtomicInteger();
        gradelist.forEach(item->{
            if (item.equals("大一")){
                count1.getAndIncrement();
            }
            else if(item.equals("大二")){

                count2.getAndIncrement();
            }
            else if(item.equals("大三")){

                count3.getAndIncrement();
            }
            else if(item.equals("大四")){

                count4.getAndIncrement();
            }
        });
        System.out.println(count1+"--"+count2+"--"+count3+"--"+count4);
    }


}
