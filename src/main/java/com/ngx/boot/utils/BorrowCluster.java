package com.ngx.boot.utils;

import com.ngx.boot.bean.StuBorrow;
import com.ngx.boot.service.StuBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author : 朱坤
 * @date :
 */
@Slf4j

public class BorrowCluster {

    private int max = 48;
    private int min = 1;

    @Autowired
    private StuBorrowService stuBorrowService;

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


//        return null;
    }

}
