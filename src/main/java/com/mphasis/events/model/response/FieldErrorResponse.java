package com.mphasis.events.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldErrorResponse {
	private String fieldId;
	private String error;
}
