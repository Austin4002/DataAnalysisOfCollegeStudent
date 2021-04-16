package com.ngx.boot.algorithm.SparkSqlTest;

/**
 * @author : 朱坤
 * @date :
 */
//import org.apache.sqoop.client.SqoopClient;
//
//import org.apache.sqoop.model.*;
//
//import org.apache.sqoop.submission.counter.Counter;
//
//import org.apache.sqoop.submission.counter.CounterGroup;
//
//import org.apache.sqoop.submission.counter.Counters;
//
//import org.apache.sqoop.validation.Status;
//
//
//
//import java.util.Arrays;
//
//import java.util.UUID;
//
//
//
//public class SqoopDataModel {
//
//    //创建静态客户端对象
//
//    static SqoopClient client;
//
//    //创建jdbc连接
//
//    public static MLink createMysqlLink() {
//
//        //使用内置的连接器
//
//        MLink link = client.createLink("generic-jdbc-connector");
//
//        // 随机生成名字,也可以自己自定
//
//        link.setName("jdbc-link" + UUID.randomUUID().toString().substring(0, 4));
//
//        link.setCreationUser("wangwang");
//
//
//
//        //获取连接配置对象
//
//        MLinkConfig linkConfig = link.getConnectorLinkConfig();
//
//        //指定连接jdbc路径uri、驱动、用户名和密码
//        linkConfig.getStringInput("linkConfig.connectionString").setValue("jdbc:mysql://192.168.18.1:3306/analysisdata");
//
//        linkConfig.getStringInput("linkConfig.jdbcDriver").setValue("com.mysql.jdbc.Driver");
//
//        linkConfig.getStringInput("linkConfig.username").setValue("root");
//
//        linkConfig.getStringInput("linkConfig.password").setValue("123456");
//
//// 这里必须指定 identifierEnclose, 它默认是双引号,mysql也会报错
//
////表示解析sql语句的单词界定符，这里我配置成空格
//
//        linkConfig.getStringInput("dialect.identifierEnclose").setValue(" ");
//
////保存连接
//
//        Status status = client.saveLink(link);
//
//        if (status.canProceed()) {
//
//            System.out.println("Created Link with Link Name : " + link.getName());
//
//            return link;
//
//        } else {
//
//            System.out.println("Something went wrong creating the link");
//
//            return null;
//
//        }
//
//    }
//
//
//    /**
//     *创建hdfs连接
//     *
//     */
//    public static MLink createHdfsLink() {
//
//        //使用内置的连接器
//
//        MLink link = client.createLink("hdfs-connector");
//
//        link.setName("hdfs-link" + UUID.randomUUID().toString().substring(0, 4));
//
//        link.setCreationUser("wangwang");
//
//        //获取连接配置对象，并配置hdfs路径及hadoop配置路径
//
//        MLinkConfig linkConfig = link.getConnectorLinkConfig();
//
//        linkConfig.getStringInput("linkConfig.uri").setValue("hdfs://192.168.195.11:9000/");
//        linkConfig.getStringInput("linkConfig.confDir").setValue("/Users/wangwang/softdir/hadoop-2.8.5/etc/hadoop");
//
//        //保存连接
//
//        Status status = client.saveLink(link);
//
//        if (status.canProceed()) {
//
//            System.out.println("Created Link with Link Name : " + link.getName());
//
//            return link;
//
//        } else {
//
//            System.out.println("Something went wrong creating the link");
//
//            return null;
//
//        }
//
//    }
//
//    /**
//     * job：mysql to hdfs
//     * @param fromLink
//     * @param toLink
//     * @return
//     */
//
//    public static String createMysqlToHdfsJob(MLink fromLink, MLink toLink) {
//
//        //创建job，参数1表示数据源link名称，参数2表示目的地link名称
//
//        MJob job = client.createJob(fromLink.getName(), toLink.getName());
//
//        job.setName("wangwang-job" + UUID.randomUUID());
//
//        job.setCreationUser("wangwang");
//
//        //获取数据源配置对象fromJobConfig，并配置数据库名和表名，以及字段名
//
//        MFromConfig fromJobConfig = job.getFromJobConfig();
//
//        fromJobConfig.getStringInput("fromJobConfig.schemaName").setValue("db1");
//
//        fromJobConfig.getStringInput("fromJobConfig.tableName").setValue("t_user");
//        fromJobConfig.getListInput("fromJobConfig.columnList").setValue(Arrays.asList("id", "user_name", "passwd"));
//
//        //获取目的地配置对象，并配置输出路径、输出格式、配置压缩比、是否覆盖空值
//
//        MToConfig toJobConfig = job.getToJobConfig();
//
//        //这里为了每次不对输出文件删除，我做了随机拼接操作，保证每次的输出路径不同，因为sqoop的hdfs导出路径要求不能存在
//
//        toJobConfig.getStringInput("toJobConfig.outputDirectory").setValue("/sqooptest" + UUID.randomUUID());
//
//        //如果不指定输出格式，则会出现以下异常
//
//        //Caused by: org.apache.sqoop.common.SqoopException: MAPRED_EXEC_0013:Cannot write to the data writer
//
//        toJobConfig.getEnumInput("toJobConfig.outputFormat").setValue("TEXT_FILE");
//
//        toJobConfig.getEnumInput("toJobConfig.compression").setValue("NONE");
//
//        toJobConfig.getBooleanInput("toJobConfig.overrideNullValue").setValue(true);
//
//        //获取驱动器并指定map数量
//
//        MDriverConfig driverConfig = job.getDriverConfig();
//
//        driverConfig.getIntegerInput("throttlingConfig.numExtractors").setValue(1);
//
//        //保存job
//
//        Status status = client.saveJob(job);
//
//        if (status.canProceed()) {
//
//            System.out.println("Created Job with Job Name: " + job.getName());
//
//            return job.getName();
//
//        } else {
//
//            System.out.println("Something went wrong creating the job");
//
//            return null;
//
//        }
//
//    }
//
//    /**
//     * job：hdfs to mysql
//     * @param fromLink
//     * @param toLink
//     * @return
//     */
//
//    public static String createHdfsToMysqlJob(MLink fromLink, MLink toLink) {
//
//        MJob job = client.createJob(fromLink.getName(), toLink.getName());
//
//        job.setName("wangwang" + UUID.randomUUID());
//
//        job.setCreationUser("wangwang");
//
//
//
//        MFromConfig fromJobConfig = job.getFromJobConfig();
//
//        fromJobConfig.getStringInput("fromJobConfig.inputDirectory").setValue("/sqoopDir");
//
//        MToConfig toJobConfig = job.getToJobConfig();
//
//        toJobConfig.getStringInput("toJobConfig.tableName").setValue("t_user");
//
//        //这里不需要指定表的字段，否则会出现语法错误
//
//        //GENERIC_JDBC_CONNECTOR_0002:Unable to execute the SQL
//
//        // toJobConfig.getListInput("toJobConfig.columnList")
//
//        // .setValue(Arrays.asList("id", "user_name", "passwd"));
//
//        MDriverConfig driverConfig = job.getDriverConfig();
//
//        //这里指定map数量，查看mapreduce运行情况发现就没有reduce任务
//
//        driverConfig.getIntegerInput("throttlingConfig.numExtractors").setValue(1);
//
//        //这里我们不能指定reduce的数量，否则会出现异常：No data available in table
//
//        //driverConfig.getIntegerInput("throttlingConfig.numLoaders").setValue(10);
//
//
//
//        Status status = client.saveJob(job);
//
//        if (status.canProceed()) {
//
//            System.out.println("Created Job with Job Name: " + job.getName());
//
//            return job.getName();
//
//        } else {
//
//            System.out.println("Something went wrong creating the job");
//
//            return null;
//
//        }
//
//    }
//
////启动job
//
//    static void startJob(String jobName) {
//
//        //Job start
//
//        MSubmission submission = client.startJob(jobName);
//
//        System.out.println("Job Submission Status : " + submission.getStatus());
//
//        if (submission.getStatus().isRunning() && submission.getProgress() != -1) {
//
//            System.out.println("Progress : " + String.format("%.2f %%",     submission.getProgress() * 100));
//
//        }
//
//
//
//        System.out.println("Hadoop job id :" + submission.getExternalJobId());
//
//        System.out.println("Job link : " + submission.getExternalLink());
//
//        Counters counters = submission.getCounters();
//
//        if (counters != null) {
//
//            System.out.println("Counters:");
//
//            for (CounterGroup group : counters) {
//
//                System.out.print("\t");
//
//                System.out.println(group.getName());
//
//                for (Counter counter : group) {
//
//                    System.out.print("\t\t");
//
//                    System.out.print(counter.getName());
//
//                    System.out.print(": ");
//
//                    System.out.println(counter.getValue());
//
//                }
//
//            }
//
//        }
//
//    }
//
//
//
//    public static void main(String[] args) {
//
//        String url = "http://localhost:12000/sqoop/";
//
//        client = new SqoopClient(url);
//
//        System.out.println(client);
//
//        MLink mysqlLink = createMysqlLink();
//
//        MLink hdfsLink = createHdfsLink();
//
//        // 将数据导入 hdfs
//
//        // startJob(createMysqlToHdfsJob(mysqlLink, hdfsLink));
//
//        // 将数据导回 mysql
//
//        startJob(createHdfsToMysqlJob(hdfsLink, mysqlLink));
//
//    }
//
//}
