package com.ngx.boot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {



    public static String getCurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }





}
