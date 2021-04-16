package com.ngx.boot.algorithm.mapred;

import org.apache.hadoop.mapred.JobConf;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Recommend {

	
    public static final String HDFS = "hdfs://192.168.195.11:9000";
    public static final Pattern DELIMITER = Pattern.compile("[\t,]");

//    public static void main(String[] args) {
//
//    	Recommend rm = new Recommend();
//    	try {
//			rm.mapred();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

   // public void mapred() throws Exception {
        public void mapred(String fpath) throws Exception {
    	
        Map<String, String> path = new HashMap<String,String>();
        //path.put("data", "src/main/resources/small2.txt");
        path.put("data", fpath);
        path.put("Step1Input", HDFS + "/data/input");
        path.put("Step1Output", path.get("Step1Input") + "/step1");
        path.put("Step2Input", path.get("Step1Output"));
        path.put("Step2Output", path.get("Step1Input") + "/step2");
        path.put("Step3Input1", path.get("Step1Output"));
        path.put("Step3Output1", path.get("Step1Input") + "/step3_1");
        path.put("Step3Input2", path.get("Step2Output"));
        path.put("Step3Output2", path.get("Step1Input") + "/step3_2");
        path.put("Step4Input1", path.get("Step3Output1"));
        path.put("Step4Input2", path.get("Step3Output2"));
        path.put("Step4Output", path.get("Step1Input") + "/step4");

        Step1.run(path);
        Step2.run(path);
        Step3.run1(path);
        Step3.run2(path);
        Step4.run(path);
        //System.exit(0);
        
    }

    public static JobConf config() {
    	
        JobConf conf = new JobConf(Recommend.class);
        conf.setJobName("Recommand");
        conf.addResource("classpath:/opt/SoftWare/Hadoop/hadoop-2.7.7/etc/hadoop/core-site.xml");
        conf.addResource("classpath:/opt/SoftWare/Hadoop/hadoop-2.7.7/etc/hadoop/hdfs-site.xml");
        conf.addResource("classpath:/opt/SoftWare/Hadoop/hadoop-2.7.7/etc/hadoop/mapred-site.xml");
        //conf.set("io.sort.mb", "1024");
        return conf;
        
        
    }

}













