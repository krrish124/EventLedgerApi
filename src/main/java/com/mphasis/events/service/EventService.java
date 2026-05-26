package com.mphasis.events.service;

import java.util.Optional;

import com.mphasis.events.entity.EventEntity;
import com.mphasis.events.model.Event;

public interface EventService {

	Event processEvent(Event event);

	Event getEvent(String id);

	Optional<EventEntity> getEventsByAccount(String accountId);

	Optional<Event> getBalance(String id);
	
	Event handleEventTests(Event event);

}
