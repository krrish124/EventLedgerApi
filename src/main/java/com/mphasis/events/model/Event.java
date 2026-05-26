package com.mphasis.events.model;

import java.time.Instant;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class Event {
	
	@NotNull(message = "eventId is required")
	@NotBlank(message = "eventId is required")
	private String eventId; // Unique event ID
	
	@NotNull(message = "accountId is required")
	@NotBlank(message = "accountId is required")
	private String accountId;
	
	@Pattern(regexp = "^(CREDIT|DEBIT)$", message = "Value must be CREDIT or DEBIT")
	private String type;
	
	@NegativeOrZero(message = "amount should be greater than zero")
	private long amount; // Transaction amount
	
	@NotNull(message = "currency is required")
	@NotBlank(message = "currency is required")
	private String currency; // Currency type
	
	@NotNull(message = "eventTimestamp is required")
	@NotNull(message = "eventTimestamp is required")
    private Instant eventTimestamp; // Event timestamp
	
    private Metadata metadata;
}