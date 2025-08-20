package com.dropwinsystem.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dropwinsystem.app.domain.Calendar;

@Mapper
public interface CalendarMapper {
	
    List<Calendar> calendarList() throws Exception;

    void calendarSave(Calendar calendar) throws Exception;

    int calendarDelete(@Param("calendarNo") Long calendarNo) throws Exception;

    void eventUpdate(Calendar calendar) throws Exception;
    
    List<Calendar> selectCalendarListByMember(String membersId) throws Exception;
}
