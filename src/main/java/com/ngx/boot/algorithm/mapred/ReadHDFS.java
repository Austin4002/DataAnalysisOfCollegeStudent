package com.ngx.boot.algorithm.mapred;

/**
 * @author : 朱坤
 * @date :
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class ReadHDFS {

    public static ArrayList<String> getStringByTXT() throws Exception{

        // TODO Auto-generated method stub
        Configuration conf = new Configuration();
        StringBuffer buffer = new StringBuffer();
        FSDataInputStream fsr = null;
        BufferedReader bufferedReader = null;
        String lineTxt = null;

            FileSystem fs = FileSystem.get(URI.create("hdfs://192.168.195.11:9000/data/input/step4/part-00000"),conf);
            fsr = fs.open(new Path("hdfs://192.168.195.11:9000/data/input/step4/part-00000"));
            bufferedReader = new BufferedReader(new InputStreamReader(fsr));
            ArrayList<String> text = new ArrayList<>();
            while ((lineTxt = bufferedReader.readLine()) != null)
            {
                text.add(lineTxt);
            }

        return text;
    }



    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        Configuration conf = new Configuration();
        String txtFilePath = "hdfs://192.168.195.11:9000/data/input/step4/part-00000";
        ArrayList<String>  mbline = getStringByTXT();
        for (int i=0;i<mbline.size();i++){

            System.out.println(mbline.get(i));
        }

    }

}