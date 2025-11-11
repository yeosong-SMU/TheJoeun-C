package com.example.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
	@Autowired
	EventRepository repo;
	
	public List<Event> getAllEvents() {
		return repo.findAll();
	}
	
	public Event saveEvent(Event event) {
		return repo.save(event);
	}
	
	public Event getEventById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> 
				new RuntimeException("Event not found"));
	}
	
	public void deleteEvent(Long id) {
		repo.deleteById(id);
	}
}