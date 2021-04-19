package com.ngx.boot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ngx.boot.bean.BookInfo;
import com.ngx.boot.service.BookInfoService;
import com.ngx.boot.service.StuBorrowService;
import com.ngx.boot.service.StuCheckService;
import com.ngx.boot.service.StuInfoService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

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
    public void testTimeCount(){
        double avgTimeCount = stuCheckService.getAvgTimeCount("201919222");
    }


    @Test
    public void test2() throws Exception {
        //生成消费聚类中心点，写入文件
        double i = new Random().nextDouble() * (max-min) + min;
        double j = new Random().nextDouble() * (max-min) + min;
        String istr = new BigDecimal(i).setScale(precimal, ROUND_HALF_DOWN).toPlainString();
        String jstr = new BigDecimal(j).setScale(precimal, ROUND_HALF_DOWN).toPlainString();
        while (istr.equals(jstr)) {
            double k = new Random().nextDouble() * (max-min) + min;
            jstr = new BigDecimal(k).setScale(precimal, ROUND_HALF_DOWN).toPlainString();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/borrowCenter.txt"));
        bw.write("1," + i);
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/scoreCenter.txt"));
        bw.write("1," + istr);
        bw.newLine();
        bw.write("1," + jstr);
        bw.write("1," + j);
        bw.close();

        BufferedWriter bw2 = new BufferedWriter(new FileWriter("src/main/resources/score.dat"));
        List<StuScore> list = stuScoreService.list();
        list.forEach(item -> {
            double stuGpa = item.getStuGpa();
            try {
                bw2.write("1," + String.valueOf(stuGpa));
                bw2.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bw2.close();
//        list.stream().mapToDouble(StuScore::getStuGpa).forEach(stuGpa -> {
//            try {
//                bw2.write("1," + stuGpa);
//                bw2.newLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });

        String clusterCenter = doubles.get(0) + "-" + doubles.get(1);

//          将结果放入redis------失败了....
//        redisTemplate.opsForValue().set("clusterCenter",clusterCenter);
//        String center =(String) redisTemplate.opsForValue().get("clusterCenter");
//        System.out.println(center);

//        我们将使用常量池，真垃圾，一下代码是葛云翔写的

//        return null;
        System.out.println("结束");
    }

    @Test
    public void testMaxAll() {
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
