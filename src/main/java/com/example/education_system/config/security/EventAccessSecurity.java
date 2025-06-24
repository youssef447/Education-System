package com.example.education_system.config.security;

import com.example.education_system.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("eventAccessSecurity")
@RequiredArgsConstructor
public class EventAccessSecurity {
    private final EventRepository eventRepository;
    public boolean isOwner(Long eventId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return eventRepository.findById(eventId)
                .map(event -> event.getUser().getUsername().equals(currentUsername))
                .orElse(false);
    }
}