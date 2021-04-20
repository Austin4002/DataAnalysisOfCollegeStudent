package com.ngx.boot.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CommonUtils {

    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    //获取聚类中心点
    public static List<Integer> getClusterCenter(String filename) {

        List<Integer> stringList = new ArrayList<>();
        try {
            ClassPathResource classPathResource = new ClassPathResource(filename);
            // 判断文件是否存在
            boolean exists = classPathResource.exists();
            if(!exists){
                log.error("文件不存在！");
            }
            try {
                // 获取文件输入流
                InputStream inputStream = classPathResource.getInputStream();
                List<String> fileList = IOUtils.readLines(inputStream);
                for (String line : fileList) {
                    String[] split = line.split(",");
                    stringList.add(Integer.parseInt(split[1]));
                }
                log.error("fileList:{}",fileList);
                return stringList.stream().sorted().collect(Collectors.toList());

            } catch (IOException e) {
                log.info(e.getMessage());
            }
        } catch (RuntimeException e) {
            log.info(e.getMessage());
        }
        //从小到大排序
        return stringList;
    }


}
