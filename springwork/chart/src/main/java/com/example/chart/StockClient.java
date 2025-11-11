package com.example.chart;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StockClient {
	//WebClient : 외부 api와의 통신을 지원하는 도구
	@Autowired
	WebClient.Builder web;
	
	String base = "http://localhost:5000";
	
	public Map<String, Object> sim(int days, double mu, double sigma, double s0, int seed) {
		// queryPram() 쿼리 파라미터 추가
		// reetrieve() 요청을 보내고 응답 받는 함수
		// bodyToMono() 응답 내용을 단일값으로 매핑
		// ParameterizedTypeReference : json 응답을 해시맵으로 역직렬화
		// block() 완료될때까지 대기
		return web.baseUrl(base).build().get()
				.uri(uri -> uri.path("/sim/stock")
						.queryParam("days", days)
						.queryParam("mu", mu)
						.queryParam("sigma", sigma)
						.queryParam("s0", s0)
						.queryParam("seed", seed).build())
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
				}).block();
	}
	
	public Map<String, Object> fetch(int limit) {
		return web.baseUrl(base).build().get()
				.uri(uri -> uri.path("/api/series")
						.queryParam("limit", limit).build())
				.retrieve().bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
				}).block();
	}
}