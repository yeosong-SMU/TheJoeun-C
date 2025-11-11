package com.example.chart;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StockService {
	@Autowired
	StockRepository repo;
	
	public List<StockPoint> latest(int limit) {
		var list = repo.findAllByOrderByTsDesc(PageRequest.of(0, limit));
		// StockPoint의 ts 값을 비교하여 오름차순으로 정렬
		list.sort(Comparator.comparing(StockPoint::getTs));
		return list;
	}
}