package com.ngx.boot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 朱坤
 * @date :
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private String stuNo;

    private String bookId;

    private String bookName;

    private double recommend;

    public Comment(String stuNo, String bookId, double recommend) {
        this.stuNo = stuNo;
        this.bookId = bookId;
        this.recommend = recommend;
    }
}
