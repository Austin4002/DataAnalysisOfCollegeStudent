package com.ngx.boot.algorithm.kmeans;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author : 朱坤
 * @date :
 */
public class ScoreKmeans {

    //学生成绩聚类（判断学生所处的成绩层次）
    static int nums =2;//聚类k值的个数(选取的k的值不能相同)
    static double[][] center = new double[nums][2];  //这里有k个中心点，为2维
    static int[] number = new int[nums];           //记录属于当前中心点的数据的个数，方便做除法
    static double[][] new_center = new double[nums][2];    //计算出来的新中心点


    public static ArrayList<Double> scomeans(String filepath, String filepath2) throws Exception {

        ArrayList<String> arrayList = new ArrayList<String>();
        File file = new File(filepath);
        InputStreamReader input = new InputStreamReader(new FileInputStream(file));
        BufferedReader bf = new BufferedReader(input);
        String str;
        while ((str = bf.readLine()) != null) {
            arrayList.add(str);
        }
        bf.close();
        input.close();

        // 对ArrayList中存储的字符串进行处理
        for (int i = 0; i < nums; i++) {
            for (int j = 0; j < nums; j++) {
                String s = arrayList.get(i).split(",")[j];
                center[i][j] = Double.parseDouble(s);
            }
        }


        SparkConf conf = new SparkConf().setAppName("kmeans").setMaster("local[*]");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        JavaRDD<String> datas = jsc.textFile(filepath2);     //从hdfs上读取data
        ArrayList<Double> fin = new ArrayList<>();

        while(true) {
            for (int i = 0; i< nums;i++)           //注意每次循环都需要将number[i]变为0
            {
                number[i]=0;
            }
            //将data分开，得到key: 属于某个中心点的序号（0/1/2/3），value: 与该中心点的距离
            JavaPairRDD<Integer, Tuple2<Double, Double>> data = datas.mapToPair(new PairFunction<String, Integer,Tuple2<Double, Double>>() {
                private static final long serialVersionUID = 1L;
                @Override
                public Tuple2<Integer,Tuple2<Double, Double>> call(String str) throws Exception {
                    final double[][] loc = center;
                    String[] datasplit = str.split(",");
                    double x = Double.parseDouble(datasplit[0]);
                    double y = Double.parseDouble(datasplit[1]);
                    double minDistance = 99999999;
                    int centerIndex = 0;
                    for(int i = 0;i < nums;i++){
                        double itsDistance = (x-loc[i][0])*(x-loc[i][0])+(y-loc[i][1])*(y-loc[i][1]);
                        if(itsDistance < minDistance){
                            minDistance = itsDistance;
                            centerIndex = i;
                        }
                    }
                    number[centerIndex]++;        //得到属于4个中心点的个数

                    return new Tuple2<Integer,Tuple2<Double, Double>>(centerIndex, new Tuple2<Double,Double>(x,y));
                    // the center's number & data
                }
            });

            //得到key: 属于某个中心点的序号， value:新中心点的坐标
            JavaPairRDD<Integer, Iterable<Tuple2<Double, Double>>> sum_center = data.groupByKey();
            //System.out.println(sum_center.collect());

            JavaPairRDD<Integer,Tuple2<Double, Double>> Ncenter = sum_center.mapToPair(new PairFunction<Tuple2<Integer, Iterable<Tuple2<Double, Double>>>,Integer,Tuple2<Double, Double>>() {
                private static final long serialVersionUID = 1L;
                @Override
                public Tuple2<Integer, Tuple2<Double, Double>> call(Tuple2<Integer, Iterable<Tuple2<Double, Double>>> a)throws Exception {
                    //System.out.println("i am here**********new center******");
                    int sum_x = 0;
                    int sum_y = 0;
                    Iterable<Tuple2<Double, Double>> it = a._2;

                    for(Tuple2<Double, Double> i : it) {
                        sum_x += i._1;
                        sum_y +=i._2;
                    }


                    double average_x = sum_x / number[a._1];
                    double average_y = sum_y/number[a._1];
                    //System.out.println("**********new center******"+a._1+" "+average_x+","+average_y);
                    return new Tuple2<Integer,Tuple2<Double,Double>>(a._1,new Tuple2<Double,Double>(average_x,average_y));
                }
            });


            //将中心点输出
            Ncenter.foreach(new VoidFunction<Tuple2<Integer,Tuple2<Double,Double>>>() {
                private static final long serialVersionUID = 1L;
                @Override
                public void call(Tuple2<Integer,Tuple2<Double,Double>> t) throws Exception {
                    new_center[t._1][0] = t._2()._1;
                    new_center[t._1][1] = t._2()._2;
                    //System.out.println("the new center: "+ t._1+"  "+t._2()._1+" , "+t._2()._2);
                }

            });

            //判断新的中心点和原来的中心点是否一样，一样的话退出循环得到结果，不一样的话继续循环（这里可以设置一个迭代次数）
            double distance = 0;
            for(int i=0;i<nums;i++) {
                distance += (center[i][0]-new_center[i][0])*(center[i][0]-new_center[i][0]) + (center[i][1]-new_center[i][1])*(center[i][1]-new_center[i][1]);
            }

            if(distance == 0.0) {
                //finished
                for(int j = 0;j<nums;j++) {
                    //System.out.println("the final center: "+"  "+center[j][0]+" , "+center[j][1]);
                    fin.add(center[j][1]);
                }
                break;
            }
            else {
                for(int i = 0;i<nums;i++) {
                    center[i][0] = new_center[i][0];
                    center[i][1] = new_center[i][1];
                    new_center[i][0] = 0;
                    new_center[i][1] = 0;
                    //System.out.println("the new center: "+"  "+center[i][0]+" , "+center[i][1]);
                }
            }
        }

        jsc.close();
        return fin;

    }

}
