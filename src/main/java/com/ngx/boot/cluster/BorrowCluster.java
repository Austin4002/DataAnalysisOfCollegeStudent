package com.ngx.boot.cluster;

import com.ngx.boot.algorithm.kmeans.BorKmeans;
import com.ngx.boot.bean.StuBorrow;
import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.service.StuBorrowService;
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
public class BorrowCluster {

    private Map<String,Double> clusterCenter = new HashMap<>();

    int max = 48;
    int min = 1;

    @Autowired
    private StuBorrowService stuBorrowService;

    @Autowired
    private StuInfoService stuInfoService;

    //@PostConstruct
    public void getRandomByBorTime() throws Exception {

        log.error("------------->getRandomByBorTime被执行");

        int i = new Random().nextInt(max - min) + min;
        int j = new Random().nextInt(max - min) + min;
        while(i == j){
            j = new Random().nextInt(max - min) + min;
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/borrowCenter.txt"));
        bw.write("1,"+i);
        bw.newLine();
        bw.write("1,"+j);
        bw.close();
//        "src/main/resources/borrow.dat"
        BufferedWriter bw2 = new BufferedWriter(new FileWriter("src/main/resources/borrow.dat"));
        List<StuBorrow> list = stuBorrowService.list();
        list.forEach(item->{
            Double time =item.getBorTime();
            try {
                bw2.write("1,"+time);
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
        fs.copyFromLocalFile(new Path("src/main/resources/borrow.dat"), new Path("/data"));
        fs.close();

        ArrayList<Double> doubles = BorKmeans.bormeans("src/main/resources/borrowCenter.txt", "hdfs://192.168.195.11:9000/data/borrow.dat");

//          将结果放入redis------失败了....
//        redisTemplate.opsForValue().set("clusterCenter",clusterCenter);
//        String center =(String) redisTemplate.opsForValue().get("clusterCenter");
//        System.out.println(center);

//        我们将使用常量池，真垃圾，以下代码是葛云翔写的
        if(doubles.get(0) > doubles.get(1)){
            clusterCenter.put("bound_max",doubles.get(0));
            clusterCenter.put("bound_min",doubles.get(1));
        } else {
            clusterCenter.put("bound_max",doubles.get(1));
            clusterCenter.put("bound_min",doubles.get(0));
        }
        this.generateStuConsumeTags();
    }

    public void generateStuConsumeTags(){
        // 去重查询表中所有的学生学号
        List<StuBorrow> stuList = stuBorrowService.getStuNoDistinct();
        double clusterMax = clusterCenter.get("bound_max");
        double clusterMin = clusterCenter.get("bound_min");
        // 根据每个学号查询所有学生的借阅时间及次数
        stuList.forEach(item ->{
            String stuNo = item.getStuNo();
            // 查询该学生的借阅总时间及次数，求平均借阅时间
            double borrowAvg = stuBorrowService.getAvgBorrowTimeByNo(stuNo);
            String learnTags = null;
            if (borrowAvg < clusterMin ){
//                String[] learnTags = new String[]{"阅读兴趣低","对书无感","很少阅读书籍"};
                learnTags = "阅读兴趣低-对书无感-很少阅读书籍";
            } else if (borrowAvg >= clusterMin && borrowAvg <= clusterMax){
                learnTags = "阅读兴趣一般-偶尔阅读-普通阅读";
            } else if (borrowAvg > clusterMax){
                learnTags = "热爱阅读-博览群书-文学气质";
            }
            StuInfo stuInfo = new StuInfo();
            stuInfo.setStuNo(stuNo);
            stuInfo.setLearn(learnTags);
            stuInfoService.updateById(stuInfo);
        });

        log.error("---------------->generateStuConsumeTags执行完毕");

    }

}
