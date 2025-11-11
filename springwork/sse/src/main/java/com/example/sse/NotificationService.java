package com.example.sse;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final NotificationRepository repo;
	
	// 알림 구독 리스트
	// 멀티스레드에서 안전한 List, 데이터를 추가, 수정, 삭제할 때마다
	// 기존 배열을 복사해서 새로운 배열을 만든 뒤 변경 적용
	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
	
	public SseEmitter subscribe() {
		SseEmitter emitter = new SseEmitter(Duration.ofMinutes(30).toMillis());  //타임 아웃 시간 설정
		emitters.add(emitter);
		
		emitter.onCompletion(() -> emitters.remove(emitter));
		emitter.onTimeout(() -> emitters.remove(emitter));
		emitter.onError(e -> emitters.remove(emitter));
		
		// 최근 NEW 상태 알림 20개 전송
		try {
			List<Notification> recent = repo.findTop20ByStatusOrderByCreatedAtDesc("NEW");
			emitter.send(SseEmitter.event().name("init").data(recent));
		} catch (IOException ignore) {
			
		}
		
		return emitter;
	}
	
	// 알림 저장 후 SSE 브로드캐스트
	public Notification saveAndBroadcast(Notification n) {
		Notification saved = repo.save(n);
		broadcast("notify", saved);
		return saved;
	}
	
	// 모든 구독자에게 이벤트 전송
	private void broadcast(String eventName, Object payload) {
		for (SseEmitter emitter : emitters) {
			try {
				emitter.send(SseEmitter.event().name(eventName).data(payload));
			} catch (IOException e) {
				emitter.complete();
				emitters.remove(emitter);
			}
		}
	}
}