package com.example.schedule;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectScheduler {
	private final WeatherLogRepository weatherRepo;
	private final RestClient rest = RestClient.create();
	
	// 날씨: Open-Meteo 현재 날씨
	//@Scheduled(cron = "0 * * * * *")  // 매 분 0초
	public void collectWeather() {
		//String url = "https://api.open-meteo.com/v1/forecast?latitude=37.5665&longitude=126.9780&t_weather=true";
		
		String url = "https://api.open-meteo.com/v1/forecast?latitude=37.5665&longitude=126.9780&current_weather=true";
		
		OpenMeteoResp resp = rest.get().uri(url).retrieve().body(OpenMeteoResp.class);
		System.out.println(resp);
		if (resp != null && resp.current_weather != null) {
			WeatherLog log = new WeatherLog();
			log.setCapturedAt(LocalDateTime.now());
			log.setTemperature(resp.current_weather.temperature);
			log.setWindspeed(resp.current_weather.windspeed);
			weatherRepo.save(log);
			System.out.println("[WEATHER] " + log.getCapturedAt() 
			+ " temp=" + log.getTemperature() + ", wind=" + log.getWindspeed());
		}
	}
	
	public record OpenMeteoResp(CurrentWeather current_weather) {
		public record CurrentWeather(double temperature, double windspeed) {
			
		}
	}
}