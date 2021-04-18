package com.ngx.boot.cluster;

import com.ngx.boot.algorithm.kmeans.ScoreKmeans;
import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.bean.StuScore;
import com.ngx.boot.service.StuInfoService;
import com.ngx.boot.service.StuScoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

/**
 * @author : 牛庚新
 * @date :
 */
@Slf4j
@Component
public class ScoreCluster {

    private Map<String, Double> clusterCenter = new HashMap<>();

    int precimal = 1;//保留的小数位数
    double min = 1.0;//最小值
    double max = 4.4;//最大

    @Autowired
    private StuScoreService stuScoreService;

    @Autowired
    private StuInfoService stuInfoService;

    //@PostConstruct
    public void getScoreByGPA() throws Exception {
        //生成消费聚类中心点，写入文件
        double i = new Random().nextDouble() * (max-min) + min;
        double j = new Random().nextDouble() * (max-min) + min;
        String istr = new BigDecimal(i).setScale(precimal, ROUND_HALF_DOWN).toPlainString();
        String jstr = new BigDecimal(j).setScale(precimal, ROUND_HALF_DOWN).toPlainString();
        while (istr.equals(jstr)) {
            double k = new Random().nextDouble() * (max-min) + min;
            jstr = new BigDecimal(k).setScale(precimal, ROUND_HALF_DOWN).toPlainString();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/scoreCenter.txt"));
        bw.write("1," + istr);
        bw.newLine();
        bw.write("1," + jstr);
        bw.close();

        BufferedWriter bw2 = new BufferedWriter(new FileWriter("src/main/resources/score.dat"));

        List<StuScore> list = stuScoreService.list();
//        list.forEach(item -> {
//            double stuGpa = item.getStuGpa();
//            try {
//                System.out.println(stuGpa);
//                bw2.write("1," + stuGpa);
//                bw2.newLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        list.stream().mapToDouble(StuScore::getStuGpa).forEach(stuGpa -> {
            try {
                bw2.write("1," + stuGpa);
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
        fs.copyFromLocalFile(new Path("src/main/resources/score.dat"), new Path("/data"));
        fs.close();

        //改成ScoreKmeans.scomeans
        ArrayList<Double> doubles = ScoreKmeans.scomeans("src/main/resources/scoreCenter.txt", "hdfs://192.168.195.11:9000/data/score.dat");
        //使用常量池进行数据比较
        if(doubles.get(0) > doubles.get(1)){
            clusterCenter.put("bound_max",doubles.get(0)+1.0);
            clusterCenter.put("bound_min",doubles.get(1)+1.0);
        } else {
            clusterCenter.put("bound_max",doubles.get(1)+1.0);
            clusterCenter.put("bound_min",doubles.get(0)+1.0);
        }

        log.error("score_max--->{},score_min--->{}",clusterCenter.get("bound_max"),clusterCenter.get("bound_min"));
        this.generateStuScoreTags();

    }

    public void generateStuScoreTags(){
        // 去重查询表中所有的学生学号
        List<StuScore> stuList = stuScoreService.getStuNoDistinct();
        double clusterMax = clusterCenter.get("bound_max");
        double clusterMin = clusterCenter.get("bound_min");
        stuList.forEach(item->{
            String stuNo = item.getStuNo();
            //根据学号查询学生对应的stu_gpa字段的数据求和取平均值，然后与a，b两个数值比较
            double gpaAvg = stuScoreService.getAvgGPA(stuNo);
            String scoreTags = null;
            if (gpaAvg < clusterMin ){
                scoreTags = "成绩较差-学渣-学习态度消极";
            } else if (gpaAvg >= clusterMin && gpaAvg <= clusterMax){
                scoreTags = "成绩一般-稳定-学习态度一般";
            } else if (gpaAvg > clusterMax){
                scoreTags = "成绩优秀-学霸-学习态度积极";
            }
            StuInfo stuInfo = new StuInfo();
            stuInfo.setStuNo(stuNo);
            stuInfo.setScore(scoreTags);
            stuInfoService.updateById(stuInfo);

        });
        log.error("---------------->generateStuScoreTags执行完毕");
    }
}
