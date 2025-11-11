package com.example.sse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	private final ProductRepository repo;
	private final NotificationService service;
	
	@Transactional
	public Product setStockQty(Long productId, int newQty) {
		Product p = repo.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("상품이 없습니다: " + productId));
		
		p.setStockQty(newQty);
		//재고 수정
		Product saved = repo.save(p);
		
		//재고가 임계치 이하이면
		if (saved.getStockQty() <= saved.getSafetyStock()) {
			String msg = String.format("%s 재고 %d개 (임계치 %d개 이하)", 
					saved.getName(), saved.getStockQty(),
					saved.getSafetyStock());
			//알림 생성 및 전송
			Notification n = Notification.builder()
					.productId(saved.getId())
					.title("재고 부족 경고").message(msg)
					.alertLevel(saved.getStockQty() == 0 ? "CRITICAL" : "WARN")
					.status("NEW").build();
			
			service.saveAndBroadcast(n);
		}
		
		return saved;
	}
}