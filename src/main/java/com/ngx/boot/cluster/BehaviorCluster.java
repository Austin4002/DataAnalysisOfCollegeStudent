package com.ngx.boot.cluster;

import com.ngx.boot.algorithm.kmeans.BehKmeans;
import com.ngx.boot.bean.StuCheck;
import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.service.StuCheckService;
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
 * @author : 牛庚新
 * @date :
 */
@Slf4j
@Component
public class BehaviorCluster {
    private Map<String,Double> clusterCenter = new HashMap<>();

    int max = 34;
    int min = 1;

    @Autowired
    private StuCheckService stuCheckService;

    @Autowired
    private StuInfoService stuInfoService;

//    @PostConstruct
    public void getRandomBySumTime() throws Exception {

        int i = new Random().nextInt(max - min) + min;
        int j = new Random().nextInt(max - min) + min;
        while(i == j){
            j = new Random().nextInt(max - min) + min;
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/behaviorCenter.txt"));
        bw.write("1,"+i);
        bw.newLine();
        bw.write("1,"+j);
        bw.close();
//        "src/main/resources/borrow.dat"
        BufferedWriter bw2 = new BufferedWriter(new FileWriter("src/main/resources/behavior.dat"));
        List<StuCheck> list = stuCheckService.list();
        list.forEach(item->{
            int sumtime =item.getSumTime();
            try {
                bw2.write("1,"+sumtime);
                bw2.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bw2.close();
        //1 创建连接
        Configuration conf = new Configuration();
        //2 连接端口
        conf.set("fs.defaultFS", "hdfs://spark1:9000");
        //3 获取连接对象
        FileSystem fs = FileSystem.get(conf);
        //本地文件上传到 hdfs
        fs.copyFromLocalFile(new Path("src/main/resources/behavior.dat"), new Path("/data"));
        fs.close();

        ArrayList<Double> doubles = BehKmeans.behmeans("src/main/resources/behaviorCenter.txt", "hdfs://192.168.195.11:9000/data/behavior.dat");

        if(doubles.get(0) > doubles.get(1)){
            clusterCenter.put("bound_max",doubles.get(0));
            clusterCenter.put("bound_min",doubles.get(1));
        } else {
            clusterCenter.put("bound_max",doubles.get(1));
            clusterCenter.put("bound_min",doubles.get(0));
        }

        log.error("behavior_max--->{},behavior_min--->{}",clusterCenter.get("bound_max"),clusterCenter.get("bound_min"));
        this.generateStuBehaviorTagsTags();
    }

    public void generateStuBehaviorTagsTags(){
        // 去重查询表中所有的学生学号
        List<StuCheck> stuList = stuCheckService.getStuNoDistinct();
        double clusterMax = clusterCenter.get("bound_max");
        double clusterMin = clusterCenter.get("bound_min");
        // 根据每个学号查询所有学生的借阅时间及次数
        stuList.forEach(item ->{
            String stuNo = item.getStuNo();
            // 查询该学生的借阅总时间及次数，求平均借阅时间
            double timeAvg = stuCheckService.getAvgTime(stuNo);
            String behaviorTags = null;
            if (timeAvg < clusterMin ){
                behaviorTags = "懒惰-不爱走动-事不遂心则热情锐减";
            } else if (timeAvg >= clusterMin && timeAvg <= clusterMax){
                behaviorTags = "平常心-平常普通-轻松愉快";
            } else if (timeAvg > clusterMax){
                behaviorTags = "勤奋上进-活泼开朗-较强的耐心和自制力";
            }
            StuInfo stuInfo = new StuInfo();
            stuInfo.setStuNo(stuNo);
            stuInfo.setBehavior(behaviorTags);
            stuInfoService.updateById(stuInfo);
        });

        log.error("---------------->generateStuBehaviorTags执行完毕");


    }

}
