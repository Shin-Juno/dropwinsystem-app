package com.dropwinsystem.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dropwinsystem.app.domain.Calendar;
import com.dropwinsystem.app.mapper.CalendarMapper;

@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired
    private CalendarMapper calendarMapper;

    @Override
    public List<Calendar> calendarList() throws Exception {
        return calendarMapper.calendarList();
    }

    @Override
    public void calendarSave(Calendar clalendar) throws Exception {
        calendarMapper.calendarSave(clalendar);
    }

    @Override
    @Transactional
    public void calendarDelete(Long calendarNo) throws Exception {
        calendarMapper.calendarDelete(calendarNo);
    }

    @Override
    public void eventUpdate(Calendar clalendar) throws Exception {
        calendarMapper.eventUpdate(clalendar);
    }
    
    @Override
    public List<Calendar> selectCalendarListByMember(String membersId) throws Exception {
        return calendarMapper.selectCalendarListByMember(membersId);
    }
}
