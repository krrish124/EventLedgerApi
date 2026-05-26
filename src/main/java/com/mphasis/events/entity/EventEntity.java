package com.mphasis.events.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EventEntity {
	
    @Id
    private String eventId;
    
    private String accountId;
    private String type; 
    private long amount; 
    private String currency;
    private Instant eventTimestamp;
    
    private int metadataId;

}