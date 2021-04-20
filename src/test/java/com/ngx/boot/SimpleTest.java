package com.ngx.boot;

import com.ngx.boot.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

/**
 * @author : 朱坤
 * @date :
 */
@Slf4j
public class SimpleTest {


    @Test
    public void testRandomDouble(){
        int precimal = 1;//保留的小数位数
        double min = 2.7;//最小值
        double max = 4.4;//最大
        Random rand = new Random();;
        double value = rand.nextDouble() * (max-min) + min;
        String result = new BigDecimal(value).setScale(precimal, ROUND_HALF_DOWN).toPlainString();
        System.out.println("result: " + result);

    }

    @Test
    public void testrandom2(){
        double min = 2.7;//最小值
        double max = 4.4;//最大
        int precimal = 1;//保留的小数位数
        double i = new Random().nextDouble() * (max-min) + min;
        double j = new Random().nextDouble() * (max-min) + min;

        String istr = new BigDecimal(i).setScale(precimal, ROUND_HALF_DOWN).toPlainString();
        String jstr = new BigDecimal(j).setScale(precimal, ROUND_HALF_DOWN).toPlainString();
        while (istr.equals(jstr)) {
            double k = new Random().nextDouble() * (max-min) + min;
            jstr = new BigDecimal(k).setScale(precimal, ROUND_HALF_DOWN).toPlainString();
            System.out.println(1);
        }
        System.out.println(jstr+"------"+istr);
    }

    @Test
    public void testFile(){
        List<Integer> clusterCenter = CommonUtils.getClusterCenter("consumeCenter.txt");
        List<Integer> collect = clusterCenter
                .stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(collect.toString());
//        log.error(clusterCenter.toString());
    }
}
