package com.example.ask;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FastApiService {
	private final RestTemplate restTemplate = new RestTemplate();
											//api 데이터 처리 쉽게
	
	public QueryResponse classifyText(String text) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//json 형식으로 전달
		
		Map<String, String> body = new HashMap<>();
		body.put("text", text);
		//("text": "배송이 언제 되나요?")
		//   key : value
		
		//api.py에서 넘어오는 값
		//return {"text": query.text, "label": label, "urgency_class": urgency_class}
		//그걸 저장할 변수
		HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
		
		ResponseEntity<QueryResponse> response = restTemplate.postForEntity("http://127.0.0.1:8000/predict", request, QueryResponse.class);
												//api서버에 전달
		
		if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
								  //200 success
			return response.getBody();
		} else {
			QueryResponse fallback = new QueryResponse();
			fallback.setText(text);
			fallback.setLabel("기타");
			fallback.setUrgencyClass("보통");
			return fallback;
		}
	}
}