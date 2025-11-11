package com.example.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EventController {
	@Autowired
	EventService service;
	
	@GetMapping("/")
	public String calendarPage(Model model) {
		List<Event> events = service.getAllEvents();
		model.addAttribute("events", events);
		return "calendar";
	}
	
	@ResponseBody
	@GetMapping("/events")
	public List<Event> getAllEvents() {
		return service.getAllEvents();
	}
	
	@ResponseBody
	@PostMapping("/events")
	public Event addEvent(@RequestBody Event event) {
		return service.saveEvent(event);
	}
	
	@ResponseBody
	@PutMapping("/events/{id}")
	public Event updateEvent(@PathVariable("id") Long id,
			@RequestBody Event event) {
		Event existing = service.getEventById(id);
		existing.setTitle(event.getTitle());
		existing.setStartDate(event.getStartDate());
		existing.setEndDate(event.getEndDate());
		return service.saveEvent(existing);
	}
	
	@ResponseBody
	@DeleteMapping("/events/{id}")
	public void deleteEvent(@PathVariable("id") Long id) {
		service.deleteEvent(id);
	}
}