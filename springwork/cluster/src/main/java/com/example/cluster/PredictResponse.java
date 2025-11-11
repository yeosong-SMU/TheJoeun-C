package com.example.cluster;

import java.util.List;

import lombok.Data;

@Data
public class PredictResponse {
	private List<Integer> clusters;
}