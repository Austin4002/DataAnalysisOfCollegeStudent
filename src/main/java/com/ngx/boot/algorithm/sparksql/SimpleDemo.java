package com.ngx.boot.algorithm.sparksql;

/**
 * @author : 朱坤
 * @date :
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.sql.hive.api.java.JavaHiveContext;

//import org.apache.spark.sql.api.java.JavaSQLContext;
//import org.apache.spark.sql.api.java.Row;


/**
 * 注意：
 * 使用JavaHiveContext时
 * 1:需要在classpath下面增加三个配置文件：hive-site.xml,core-site.xml,hdfs-site.xml
 * 2:需要增加postgresql或mysql驱动包的依赖
 * 3:需要增加hive-jdbc,hive-exec的依赖
 *
 */
public class SimpleDemo {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("simpledemo").setMaster("spark://spark1:7077");
        JavaSparkContext sc = new JavaSparkContext(conf);
        //JavaSQLContext sqlCtx = new JavaSQLContext(sc);
        //JavaHiveContext hiveCtx = new JavaHiveContext(sc);

        //SparkSession ss = new SparkSession();

//        testQueryJson(sqlCtx);
//        testUDF(sc, sqlCtx);
//        testHive(hiveCtx);
        sc.stop();
        sc.close();





    }

    //测试spark sql直接查询JSON格式的数据
//    public static void testQueryJson(JavaSQLContext sqlCtx) {
//        JavaSchemaRDD rdd = sqlCtx.jsonFile("file:///D:/tmp/tmp/json.txt");
//        rdd.printSchema();
//
//        // Register the input schema RDD
//        rdd.registerTempTable("account");
//
//        JavaSchemaRDD accs = sqlCtx.sql("SELECT address, email,id,name FROM account ORDER BY id LIMIT 10");
//        List<Row> result = accs.collect();
//        for (Row row : result) {
//            System.out.println(row.getString(0) + "," + row.getString(1) + "," + row.getInt(2) + ","
//                    + row.getString(3));
//        }
//
//        JavaRDD<String> names = accs.map(new Function<Row, String>() {
//            @Override
//            public String call(Row row) throws Exception {
//                return row.getString(3);
//            }
//        });
//        System.out.println(names.collect());
//    }


    //测试spark sql的自定义函数
//    public static void testUDF(JavaSparkContext sc, JavaSQLContext sqlCtx) {
//        // Create a account and turn it into a Schema RDD
//        ArrayList<AccountBean> accList = new ArrayList<AccountBean>();
//        accList.add(new AccountBean(1, "lily", "lily@163.com", "gz tianhe"));
//        JavaRDD<AccountBean> accRDD = sc.parallelize(accList);
//
//        JavaSchemaRDD rdd = sqlCtx.applySchema(accRDD, AccountBean.class);
//
//        rdd.registerTempTable("acc");
//
//        // 编写自定义函数UDF
//        sqlCtx.registerFunction("strlength", new UDF1<String, Integer>() {
//            @Override
//            public Integer call(String str) throws Exception {
//                return str.length();
//            }
//        }, DataType.IntegerType);
//
//        // 数据查询
//        List<Row> result = sqlCtx.sql("SELECT strlength('name'),name,address FROM acc LIMIT 10").collect();
//        for (Row row : result) {
//            System.out.println(row.getInt(0) + "," + row.getString(1) + "," + row.getString(2));
//        }
//    }

    //测试spark sql查询hive上面的表
//    public static void testHive(JavaHiveContext hiveCtx) {
//        List<Row> result = hiveCtx.sql("SELECT stu_no,stu_name from analysisdata.stu_info").collect();
//        for (Row row : result) {
//            System.out.println(row.getString(0) + "," + row.getString(1));
//        }
//    }
}
