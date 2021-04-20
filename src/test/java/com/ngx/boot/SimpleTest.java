package com.ngx.boot;

import com.ngx.boot.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
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


}
