package com.example.schedule;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherCsvExportScheduler {
	private final WeatherLogRepository weatherLogRepo;
	
	private final String exportDir = "d:/data/weather";
	private final String zoneId = "Asia/Seoul";
	
	@Scheduled(cron = "0 18 11 * * *", zone = "Asia/Seoul")
	public void exportAllToCsv() throws IOException {
		ZoneId zone = ZoneId.of(zoneId);
		ZonedDateTime now = ZonedDateTime.now(zone);
		
		String ymd = now.toLocalDate().format(DateTimeFormatter.BASIC_ISO_DATE);
		Path dir = Paths.get(exportDir);
		Files.createDirectories(dir);
		Path csvPath = dir.resolve("weather_ALL_" + ymd + ".csv");
		
		csvPath = uniquify(csvPath);
		
		List<WeatherLog> rows = weatherLogRepo.findAllByOrderByCapturedAtAsc();
		
		try (BufferedWriter w = Files.newBufferedWriter(csvPath, 
				StandardCharsets.UTF_8,
				StandardOpenOption.CREATE_NEW)) {
			w.write("id, captured_at, temperature, windspeed");
			w.newLine();
			
			DateTimeFormatter tsFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			for (WeatherLog r : rows) {
				String ts = (r.getCapturedAt() != null) ? r.getCapturedAt().format(tsFmt) : "";
				w.write(String.join(",", String.valueOf(r.getId()), ts, String.valueOf(r.getTemperature()),
						String.valueOf(r.getWindspeed())));
				w.newLine();
			}
		}
		
		System.out.println("[CSV] Exported ALL " + rows.size() + " rows -> " + csvPath.toAbsolutePath());
	}
	
	private Path uniquify(Path base) {
		if (!Files.exists(base))
			return base;
		String fn = base.getFileName().toString();
		String name = fn, ext = "";
		int dot = fn.lastIndexOf('.');
		if (dot > 0) {
			name = fn.substring(0, dot);
			ext = fn.substring(dot);
		}
		int i = 1;
		Path p;
		do {
			p = base.getParent().resolve(name + "(" + i + ")" + ext);
			i++;
		} while(Files.exists(p));
		return p;
	}
}