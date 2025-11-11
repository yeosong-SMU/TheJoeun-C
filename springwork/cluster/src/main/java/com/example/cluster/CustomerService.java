package com.example.cluster;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CustomerService {
	@Autowired
	CustomerRepository repo;
	
	@Autowired
	ClusterClient flask;
	
	@Transactional
	public List<Customer> ingestAndLabel(MultipartFile file) throws Exception {
		List<Customer> rows = parseCsv(file);
		repo.saveAll(rows);
		var payload = rows.stream()
				.map(c -> Map.<String, Object>of(
						"age", c.getAge(), 
						"income", c.getIncome(),
						"spend", c.getSpend()))
				.toList();
		List<Integer> labels = flask.predictBatch(payload);
		
		int n = Math.min(rows.size(), labels.size());
		for(int i = 0; i < n; i++) {
			rows.get(i).setClusterLabel(labels.get(i));
		}
		repo.saveAll(rows);
		
		return rows;
	}
	
	private List<Customer> parseCsv(MultipartFile file) throws Exception {
		try (var reader = new InputStreamReader(file.getInputStream(), 
				StandardCharsets.UTF_8);
				var parser = CSVParser.parse(reader, 
						CSVFormat
						.DEFAULT
						.withFirstRecordAsHeader()
						.withIgnoreEmptyLines()
						.withTrim())) {
			List<Customer> list = new ArrayList<>();
			for (CSVRecord r : parser) {
				Customer c = new Customer();
				c.setCustomerId(Long.parseLong(r.get("customer_id")));
				c.setAge(Double.parseDouble(r.get("age")));
				c.setIncome(Double.parseDouble(r.get("income")));
				c.setSpend(Double.parseDouble(r.get("spend")));
				c.setClusterLabel(null);
				list.add(c);
			}
			return list;
		}
	}
}