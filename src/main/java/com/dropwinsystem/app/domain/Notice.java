package com.dropwinsystem.app.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

	private int noticeId;
    private String title;
    private String writer;
    private String content;
    private int viewCount;
    private Timestamp regDate;
    private String attachFile;
    private int likeCount;
    private int dislikeCount;
    private boolean isNotice;
}
