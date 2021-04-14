package com.ngx.boot.algorithm.mapred;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
//import org/.conan.myhadoop.hdfs.HdfsDAO;
//import org.apache.hadoop.mapred.JobClient;
//import org.apache.hadoop.yarn.api.impl.pb.client.*;

public class Step1 {

    public static class Step1_ToItemPreMapper extends MapReduceBase implements Mapper<Object, Text, IntWritable, Text> {
        private final static IntWritable k = new IntWritable();
        private final static Text v = new Text();

        public void map(Object key, Text value, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
            String[] tokens = Recommend.DELIMITER.split(value.toString());
            int userID = Integer.parseInt(tokens[0]);
            String itemID = tokens[1];
            String pref = tokens[2];
            k.set(userID);
            v.set(itemID + ":" + pref);
            output.collect(k, v);
        }
    }

    public static class Step1_ToUserVectorReducer extends MapReduceBase implements Reducer<IntWritable, Text, IntWritable, Text> {
        private final static Text v = new Text();

        public void reduce(IntWritable key, Iterator<Text> values, OutputCollector<IntWritable, Text> output, Reporter reporter) throws IOException {
            StringBuilder sb = new StringBuilder();
            while (values.hasNext()) {
                sb.append("," + values.next());
            }
            v.set(sb.toString().replaceFirst(",", ""));
            output.collect(key, v);
        }
    }
//    public static void Look(String uri) {
//    	
//    	 //String uri = "hdfs://zzti:9000/data/test/in/movie.data";
//    	    Configuration conf = new Configuration();
//    	    InputStream in = null;
//    	    try {
//    	    	FileSystem fs = FileSystem.get(URI.create(uri), conf);
//        	    
//    	      in = fs.open(new Path(uri));
//    	      IOUtils.copyBytes(in, System.out, 4096, false);
//    	    }catch(Exception e){
//    	    	e.printStackTrace();
//    	    }
//    	    finally {
//    	      IOUtils.closeStream(in);
//    	    }
//    }
   
    public static void run(Map<String, String> path) throws IOException, ClassNotFoundException, InterruptedException {
        JobConf conf = Recommend.config();
        
        String input = path.get("Step1Input");
        String output = path.get("Step1Output");

        HdfsDAO hdfs = new HdfsDAO(Recommend.HDFS, conf);
//        hdfs.rmr(output);
        hdfs.rmr(input);
        hdfs.mkdirs(input);
        hdfs.copyFile(path.get("data"), input);
        //hdfs.ls(input);
        FileInputFormat.setInputPaths(conf, new Path(input));
        FileOutputFormat.setOutputPath(conf, new Path(output));
        
        conf.setMapOutputKeyClass(IntWritable.class);
        conf.setMapOutputValueClass(Text.class);

        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(Step1_ToItemPreMapper.class);
        conf.setCombinerClass(Step1_ToUserVectorReducer.class);
        conf.setReducerClass(Step1_ToUserVectorReducer.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

          RunningJob job = JobClient.runJob(conf);
     
          while (!job.isComplete()) {
             job.waitForCompletion();
         }
     

    }

}








