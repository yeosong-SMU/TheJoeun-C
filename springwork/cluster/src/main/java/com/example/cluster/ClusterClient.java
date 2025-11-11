package com.example.cluster;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ClusterClient {
	@Autowired
	WebClient flaskClient;
	
	public List<Integer> predictBatch(List<Map<String, Object>> rows) {
		PredictResponse res = flaskClient.post()
				.uri("/predict")
				.bodyValue(rows).retrieve()
				.bodyToMono(PredictResponse.class)
				.block();
		return res.getClusters();
	}
}