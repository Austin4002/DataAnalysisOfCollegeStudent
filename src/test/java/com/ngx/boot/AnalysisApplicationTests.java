package com.ngx.boot;

import com.ngx.boot.algorithm.kmeans.BorKmeans;
import com.ngx.boot.service.StuBorrowService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

@SpringBootTest
class AnalysisApplicationTests {

    private int max = 48;
    private int min = 1;

    @Autowired
    private StuBorrowService stuBorrowService;

//    @Autowired
//    private RedisTemplate redisTemplate;


    @Test
    public void getRandomByBorTime() throws Exception {
//    public List<Double> getRandomByBorTime(){
//        List<Double> doubleList = new List<Double>();

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
//        BufferedWriter bw2 = new BufferedWriter(new FileWriter("src/main/resources/borrow.dat"));
//        List<StuBorrow> list = stuBorrowService.list();
//        list.forEach(item->{
//            Double time =item.getBorTime();
//            try {
//                bw2.write("1,"+time);
//                bw2.newLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        bw2.close();
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

        String clusterCenter = doubles.get(0) +"-" + doubles.get(1);

//          将结果放入redis------失败了....
//        redisTemplate.opsForValue().set("clusterCenter",clusterCenter);
//        String center =(String) redisTemplate.opsForValue().get("clusterCenter");
//        System.out.println(center);

//        我们将使用常量池，真垃圾，一下代码是葛云翔写的




//        return null;
    }

}
