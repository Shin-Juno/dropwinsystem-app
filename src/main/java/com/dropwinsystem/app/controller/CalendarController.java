package com.dropwinsystem.app.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dropwinsystem.app.domain.Calendar;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.service.CalendarService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
	
	@Autowired
    private CalendarService calendarService;

	@GetMapping("")
    public String calendar() {
        return "views/calendar";
    }

	@GetMapping("/calendarList")
	public @ResponseBody List<Calendar> calendarList(HttpSession session) throws Exception {
	    String membersId = ((Member) session.getAttribute("loginUser")).getId();
	    return calendarService.selectCalendarListByMember(membersId);
	}


	@PostMapping("/calendarSave")
	public @ResponseBody Calendar calendarSave(@RequestBody Map<String, Object> map, HttpSession session) throws Exception {
	    Calendar calendar = new Calendar();
	    calendar.setCalendarTitle((String) map.get("calendarTitle"));

	    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
	    ZonedDateTime startUTC = ZonedDateTime.parse((String) map.get("calendarStart"), formatter)
	                                         .withZoneSameInstant(ZoneId.of("Asia/Seoul"));

	    ZonedDateTime endUTC = map.get("calendarEnd") != null
	        ? ZonedDateTime.parse((String) map.get("calendarEnd"), formatter).withZoneSameInstant(ZoneId.of("Asia/Seoul"))
	        : null;

	    calendar.setCalendarStart(startUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	    calendar.setCalendarEnd(endUTC != null ? endUTC.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
	    calendar.setAllDay((Boolean) map.get("allDay"));

	    Member loginUser = (Member) session.getAttribute("loginUser");
	    if (loginUser == null) {
	        throw new RuntimeException("로그인이 필요합니다.");
	    }
	    calendar.setMembersId(loginUser.getId());

	    calendarService.calendarSave(calendar);

	    return calendar;
	}


	@DeleteMapping("/calendarDelete")
	public @ResponseBody String calendarDelete(@RequestParam("calendarNo") String calendarNo, HttpSession session) {
	    try {
	        Member loginUser = (Member) session.getAttribute("loginUser");
	        if (loginUser == null) {
	            return "notLogin";
	        }
	        calendarService.calendarDelete(Long.valueOf(calendarNo));
	        return "success";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "fail";
	    }
	}


    @PutMapping("/eventUpdate/{calendarNo}")
    public @ResponseBody String eventUpdate(@PathVariable("calendarNo") String calendarNo, 
                                            @RequestBody Map<String, Object> map,
                                            HttpSession session) {
        try {
            Calendar calendar = new Calendar();
            calendar.setCalendarNo(Long.valueOf(calendarNo));
            calendar.setCalendarTitle((String) map.get("calendarTitle"));

            DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            DateTimeFormatter mysqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String startStr = map.get("calendarStart").toString().substring(0, 19);
            LocalDateTime startDateTime = LocalDateTime.parse(startStr, isoFormatter);
            calendar.setCalendarStart(startDateTime.format(mysqlFormatter));

            if (map.get("calendarEnd") != null) {
                String endStr = map.get("calendarEnd").toString().substring(0, 19);
                LocalDateTime endDateTime = LocalDateTime.parse(endStr, isoFormatter);
                calendar.setCalendarEnd(endDateTime.format(mysqlFormatter));
            } else {
                calendar.setCalendarEnd(null);
            }

            calendar.setAllDay((Boolean) map.get("allDay"));

            Member loginUser = (Member) session.getAttribute("loginUser");
            if (loginUser == null) {
                throw new RuntimeException("로그인이 필요합니다.");
            }
            calendar.setMembersId(loginUser.getId());

            calendarService.eventUpdate(calendar);
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}