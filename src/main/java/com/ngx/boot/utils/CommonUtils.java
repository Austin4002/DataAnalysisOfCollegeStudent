package com.ngx.boot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {


    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    //获取聚类中心点
    public static List<Integer> getClusterCenter(String filename) {
        String pre = "src/main/resources/";
        File file = new File(pre + filename);

        BufferedReader reader = null;
        List<Integer> stringList = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                String[] split = tempStr.split(",");
                stringList.add(Integer.parseInt(split[1]));
            }
            reader.close();
            return stringList;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        //从小到大排序
        return stringList.stream().sorted().collect(Collectors.toList());


    }


}
