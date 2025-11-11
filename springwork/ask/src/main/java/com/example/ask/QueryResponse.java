package com.example.ask;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryResponse {
	private String text;
	private String label;
	
	@JsonProperty("urgency_class")
	private String urgencyClass;
}