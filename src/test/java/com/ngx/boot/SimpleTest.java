package com.ngx.boot;

import com.ngx.boot.excelOperation.GeneratePerSonInfo;
import com.ngx.boot.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.catalyst.plans.logical.Generate;
import org.junit.Test;

import java.util.List;

/**
 * @author : 朱坤
 * @date :
 */
@Slf4j
public class SimpleTest {

    @Test
    public void test(){
        List<Integer> list =CommonUtils.getClusterCenter("borrowCenter.txt");
        list.forEach(System.out::println);
    }

    @Test
    public void test2(){
//        for (int i = 0; i < 100; i++) {
//            Integer major = GeneratePerSonInfo.getRestaurantNumber();
//            System.out.println(major);
//
//        }
    }

}
