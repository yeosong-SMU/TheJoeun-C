package com.example.wsuser;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class PresenceService {
	private final SimpMessagingTemplate messagingTemplate;
	private final PresenceLogRepository repo;
	
	private final Map<String, String> sessionToName = new ConcurrentHashMap<>();
	
	public PresenceService(SimpMessagingTemplate template,
			PresenceLogRepository repo) {
		this.messagingTemplate = template;
		this.repo = repo;
	}
	
	public void join(String sessionId, String nickname) {
		if (nickname == null || nickname.isBlank())
			nickname = "손님";
		nickname = nickname.trim();
		
		sessionToName.put(sessionId, nickname);
		
		var log = new PresenceLog();
		log.setSessionId(sessionId);
		log.setNickname(nickname);
		log.setEventType(PresenceLog.EventType.JOIN);
		log.setAtTs(OffsetDateTime.now());
		repo.save(log);
		
		broadcastWithEvent("JOIN", nickname, log.getAtTs());
	}
	
	@Transactional
	public void leave(String sessionId) {
		if (sessionId == null || sessionId.isBlank())
			return;
		
		String nickname = sessionToName.remove(sessionId);
		if (nickname == null) {
			return;
		}
		
		OffsetDateTime at = OffsetDateTime.now();
		
		PresenceLog log = new PresenceLog();
		log.setSessionId(sessionId);
		log.setNickname(nickname);
		log.setEventType(PresenceLog.EventType.LEAVE);
		log.setAtTs(at);
		repo.save(log);
		
		broadcastWithEvent("LEAVE", nickname, at);
	}
	
	public OnlineStatus currentStatus() {
		var list = new ArrayList<>(sessionToName.values());
		list.sort(String::compareToIgnoreCase);
		return new OnlineStatus(list.size(), list);
	}
	
	private void broadcastWithEvent(String type, 
			String nickname, OffsetDateTime at) {
		var status = currentStatus();
		var event = new PresenceEventDTO(type, nickname, at.toString());
		var payload = Map.of("count", status.getCount(), 
				"users", status.getUsers(), 
				"event", event);
		messagingTemplate.convertAndSend("/topic/online", payload);
	}
	
	public List<PresenceLog> recentLogs(int limit) {
		return repo.findRecent(PageRequest.of(0, limit));
	}
	
	public List<Map<String, Object>> aggregatedRows(int limit) {
		var recent = recentLogs(limit);
		var order = new LinkedHashSet<String>();
		for(var r : recent)
			order.add(r.getNickname());
		var byNick = new HashMap<String, Map<String, Object>>();
		
		for (var r : recent) {
			var row = byNick.computeIfAbsent(r.getNickname(), k -> {
				var m = new HashMap<String, Object>();
				m.put("nickname", k);
				m.put("joinedAt", null);
				m.put("leftAt", null);
				return m;
			});
			if (r.getEventType() == PresenceLog.EventType.JOIN && row.get("joinedAt") == null)
				row.put("joinedAt", r.getAtTs().toString());
			if(r.getEventType() == PresenceLog.EventType.LEAVE && row.get("leftAt") == null)
				row.put("leftAt", r.getAtTs().toString());
		}
		var online = new TreeSet<>(String::compareToIgnoreCase);
		online.addAll(sessionToName.values());
		
		var onlineRows = new ArrayList<Map<String, Object>>();
		var others = new ArrayList<Map<String, Object>>();
		for (var nick : order) {
			var row = byNick.get(nick);
			if (row == null)
				continue;
			if (online.contains(nick))
				onlineRows.add(row);
			else
				others.add(row);
		}
		onlineRows.sort(Comparator.comparing(m -> ((String) m.get("nickname")), String::compareToIgnoreCase));
		onlineRows.addAll(others);
		return onlineRows;
	}
}