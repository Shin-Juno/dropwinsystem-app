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
public class Member {	
	private String id, name, pass, email, mobile;
	private boolean essOption;
	private boolean unOption;
	private Timestamp regDate;	
	private String role;
	private String provider;
	private String providerId;
}

