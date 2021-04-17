package com.ngx.boot.utils;

import com.ngx.boot.algorithm.kmeans.ConsumeKmeans;
import com.ngx.boot.bean.StuConsume;
import com.ngx.boot.service.StuConsumeService;
import com.ngx.boot.service.StuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author : 朱坤
 * @date :
 */

@Slf4j
//@Component
public class ConsumeCluster {

    private Map<String, Double> clusterCenter = new HashMap<>();

    private int max = 35;
    private int min = 9;

    @Autowired
    private StuConsumeService stuConsumeService;


    @Autowired
    private StuInfoService stuInfoService;


    public List<Double> getConsumeByConmoney() throws Exception {
        //生成消费聚类中心点，写入文件
        int i = new Random().nextInt(max - min) + min;
        int j = new Random().nextInt(max - min) + min;
        while (i == j) {
            j = new Random().nextInt(max - min) + min;
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/consumeCenter.txt"));
        bw.write("1," + i);
        bw.newLine();
        bw.write("1," + j);
        bw.close();

        BufferedWriter bw2 = new BufferedWriter(new FileWriter("src/main/resources/consume.dat"));
        //List<StuBorrow> list = stuBorrowService.list();
        List<StuConsume> list = stuConsumeService.list();
        list.forEach(item -> {
            Double money = item.getConMoney();
            try {
                bw2.write("1," + money);
                bw2.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //1 创建连接
        Configuration conf = new Configuration();
        //2 连接端口
        conf.set("fs.defaultFS", "hdfs://spark1:9000");
        //3 获取连接对象
        FileSystem fs = FileSystem.get(conf);
        //本地文件上传到 hdfs
        fs.copyFromLocalFile(new Path("src/main/resources/consume.dat"), new Path("/data"));
        fs.close();

        ArrayList<Double> doubles = ConsumeKmeans.conmeans("src/main/resources/consumeCenter.txt", "hdfs://192.168.195.11:9000/data/consume.dat");

        //使用常量池进行数据比较
        if(doubles.get(0) > doubles.get(1)){
            clusterCenter.put("bound_max",doubles.get(0));
            clusterCenter.put("bound_min",doubles.get(1));
        } else {
            clusterCenter.put("bound_max",doubles.get(1));
            clusterCenter.put("bound_min",doubles.get(0));
        }
        return doubles;


    }


//    @PostConstruct
    public void generateStuTags(){
        // 去重查询表中所有的学生学号
        List<StuConsume> stuList = stuConsumeService.getStuNoDisctinct();
        double clusterMax = clusterCenter.get("bound_max");
        double clusterMin = clusterCenter.get("bound_min");
        //根据学号查询所有学生的消费金额及消费频次
        stuList.forEach(item->{
            String stuNo = item.getStuNo();
            //查询该学生的消费总金额及消费频次，求平均消费金额
            //double consumeAvg = stuConsumeService


        });

//        // 根据每个学号查询所有学生的借阅时间及次数
//        stuList.forEach(item ->{
//            String stuNo = item.getStuNo();
//            // 查询该学生的借阅总时间及次数，求平均借阅时间
//            double borrowAvg = stuBorrowService.getAvgBorrowTimeByNo(stuNo);
//            String learnTags = null;
//            if (borrowAvg < clusterMin ){
////                String[] learnTags = new String[]{"阅读兴趣低","对书无感","很少阅读书籍"};
//                learnTags = "阅读兴趣低-对书无感-很少阅读书籍";
//            } else if (borrowAvg >= clusterMin && borrowAvg <= clusterMax){
//                learnTags = "阅读兴趣一般-偶尔阅读-普通阅读";
//            } else if (borrowAvg > clusterMax){
//                learnTags = "热爱阅读-博览群书-文学气质";
//            }
//            StuInfo stuInfo = new StuInfo();
//            stuInfo.setStuNo(stuNo);
//            stuInfo.setLearn(learnTags);
//            stuInfoService.updateById(stuInfo);
//        });
//
//        log.error("---------------->generateStuTags被执行");

    }




}
