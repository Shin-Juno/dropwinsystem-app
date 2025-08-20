package com.dropwinsystem.app.service;

import java.util.List;

import com.dropwinsystem.app.domain.Calendar;

public interface CalendarService {

    List<Calendar> calendarList() throws Exception;

    void calendarSave(Calendar calendar) throws Exception;

    void calendarDelete(Long calendarNo) throws Exception;

    void eventUpdate(Calendar calendar) throws Exception;
    
    List<Calendar> selectCalendarListByMember(String membersId) throws Exception;
}