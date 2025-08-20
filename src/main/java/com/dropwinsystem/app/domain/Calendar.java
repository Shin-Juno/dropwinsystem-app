package com.dropwinsystem.app.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Calendar {
	private Long calendarNo;
    private String calendarTitle;
    private String calendarStart;
    private String calendarEnd;
    private boolean allDay;
    private String membersId;
}
