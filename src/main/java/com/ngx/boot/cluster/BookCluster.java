package com.ngx.boot.cluster;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ngx.boot.algorithm.mapred.ReadHDFS;
import com.ngx.boot.algorithm.mapred.Recommend;
import com.ngx.boot.bean.BookInfo;
import com.ngx.boot.service.BookInfoService;
import com.ngx.boot.service.StuBorrowService;
import com.ngx.boot.vo.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 返回给前端一个comment列表
 */
@Slf4j
@Component
public class BookCluster {

    @Autowired
    private StuBorrowService stuBorrowService;

    @Autowired
    private BookInfoService bookInfoService;

//    @PostConstruct
    public void getBookComment() throws Exception {
//        List<StuBorrow> list = stuBorrowService.list();
//        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/bookComment.txt"));
//        list.forEach(item->{
//            String str = item.getStuNo()+"\t"+item.getBookNo()+"    "+(int) item.getBorTime();
//            try {
//                bw.write(str);
//                bw.newLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        });
//        bw.close();

        Recommend.mapred("src/main/resources/bookComment.txt");
        ArrayList<String> arrayList = ReadHDFS.getStringByTXT();
//        arrayList.forEach(System.out::println);

        List<Comment> comments = new ArrayList<Comment>();
        for (int i = 0; i < arrayList.size(); i++) {
            String[] split = arrayList.get(i).split(",");
            comments.add(new Comment(split[0], split[1], Double.parseDouble(split[2])));
        }

        Map<String, List<Comment>> collect = comments.stream().collect(Collectors.groupingBy(Comment::getBookId));
        Iterator<Map.Entry<String, List<Comment>>> mapIterator = collect.entrySet().iterator();

        List<Comment> bookComment = new ArrayList<>();

        Map<String, Double> result = new HashMap<>();
        while (mapIterator.hasNext()) {
            Map.Entry<String, List<Comment>> entry = mapIterator.next();
            double avg = entry.getValue().stream().mapToDouble(Comment::getRecommend).average().getAsDouble();
            String key = entry.getKey();
            Comment comment = new Comment();
            comment.setBookId(key);
            BookInfo bookInfo = bookInfoService.getById(key);
            comment.setBookName(bookInfo.getBookName());
            comment.setRecommend(avg);
            //key是书号，value是推荐指数
            bookComment.add(comment);

            result.put(entry.getKey(), avg);
        }
        //更新数据库中的
        bookComment.forEach(item->{
            UpdateWrapper<BookInfo> wrapper = new UpdateWrapper<>();
            wrapper.eq("book_no",item.getBookId());
            wrapper.set("recommend",item.getRecommend());
            bookInfoService.update(wrapper);
        });

//        return bookComment;

//        for(String key : result.keySet()){
//            double value = result.get(key);
//            System.out.println(key+":"+value);
//        }

    }

}
