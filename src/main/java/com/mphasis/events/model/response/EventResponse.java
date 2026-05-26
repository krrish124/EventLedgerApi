package com.mphasis.events.model.response;

import java.util.List;

import lombok.Data;

@Data
public class EventResponse {

	private List<FieldErrorResponse> errors;
}
