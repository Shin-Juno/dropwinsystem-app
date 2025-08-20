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
public class Board {
	private int no;
	private String title;
	private String name;
	private String content;
	private int readCount;
	private Timestamp regDate;
	private String file1;
	private String pass;
	private boolean isBoard;
	private int recommend;
	private int displayNo;
}
