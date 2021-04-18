package com.ngx.boot.cluster;

import com.ngx.boot.algorithm.kmeans.ConsumeKmeans;
import com.ngx.boot.bean.StuConsume;
import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.service.StuConsumeService;
import com.ngx.boot.service.StuInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author : 朱坤
 * @date :
 */

@Slf4j
@Component
public class ConsumeCluster {

    private Map<String, Double> clusterCenter = new HashMap<>();

    int max = 35;
    int min = 9;

    @Autowired
    private StuConsumeService stuConsumeService;

    @Autowired
    private StuInfoService stuInfoService;

    //@PostConstruct
    public void getConsumeByConmoney() throws Exception {
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

        List<StuConsume> list = stuConsumeService.list();
        list.forEach(item -> {
            double money = item.getConMoney();
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

        log.error("consume_max--->{},consume_min--->{}",clusterCenter.get("bound_max"),clusterCenter.get("bound_min"));
        this.generateStuScoreTags();
    }

    public void generateStuScoreTags(){
        // 去重查询表中所有的学生学号
        List<StuConsume> stuList = stuConsumeService.getStuNoDistinct();
        double clusterMax = clusterCenter.get("bound_max");
        double clusterMin = clusterCenter.get("bound_min");
        //根据学号查询所有学生的消费金额及消费频次
        stuList.forEach(item->{
            String stuNo = item.getStuNo();
            //查询该学生的消费总金额及消费频次，求平均消费金额
            double consumeAvg = stuConsumeService.getAvgConsumeMoneyByNo(stuNo);
            String consumeTags = null;
            if (consumeAvg < clusterMin ){
//                String[] learnTags = new String[]{"阅读兴趣低","对书无感","很少阅读书籍"};
                consumeTags = "消费水平低-紧凑度日-物质消费较低";
            } else if (consumeAvg >= clusterMin && consumeAvg <= clusterMax){
                consumeTags = "消费水平一般-平平淡淡-物质消费一般";
            } else if (consumeAvg > clusterMax){
                consumeTags = "消费水平高-挥金如土-物质消费较高";
            }
            StuInfo stuInfo = new StuInfo();
            stuInfo.setStuNo(stuNo);
            stuInfo.setConsume(consumeTags);
            stuInfoService.updateById(stuInfo);

        });
        log.error("---------------->generateStuConsumeTags执行完毕");
    }




}
