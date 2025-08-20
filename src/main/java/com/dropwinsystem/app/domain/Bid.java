package com.dropwinsystem.app.domain;

import lombok.Data; 
import java.time.LocalDateTime;

@Data
public class Bid {
    private int id; 
    private int itemId;
    private String userId;
    private int bidPrice;
    private LocalDateTime bidTime;

    private String status; 

    private String itemName;
    private String userName;
    private LocalDateTime itemEndTime;
    private String itemImagePath;
}