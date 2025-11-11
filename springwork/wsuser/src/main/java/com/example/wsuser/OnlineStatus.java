package com.example.wsuser;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OnlineStatus {
	private int count;
	private List<String> users;
}