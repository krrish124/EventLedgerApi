package com.mphasis.events.service;

import com.mphasis.events.dao.EventsDao;
import com.mphasis.events.entity.EventEntity;
import com.mphasis.events.model.Event;
import com.mphasis.events.model.response.EventResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EventServiceImpl implements EventService {
	private final PriorityQueue<Event> eventQueue = new PriorityQueue<>((e1, e2) -> e1.getEventTimestamp().compareTo(e2.getEventTimestamp()));
	private final ConcurrentHashMap<String, Event> processedEvents = new ConcurrentHashMap<>();
	private final long TIME_WINDOW_MS = 5000; // 5 seconds

	@Autowired
	EventsDao eventsDao;

	public synchronized Event processEvent(Event event) {
		EventResponse eventResponse = new EventResponse();
		Event evt = null;
		if(isReqNotContainsErrors(eventResponse)) {
			evt = getEvent(event.getEventId());
			if(Objects.nonNull(evt)) {
				return evt;
			}else {
				if (processedEvents.containsKey(event.getEventId())) {
					evt = getEvent(event.getEventId());
				}
				if(Objects.isNull(evt))  {
					eventQueue.offer(event);
					processedEvents.put(event.getEventId(), event);

					// Process events within the time window
					Instant now = Instant.now();
					while (!eventQueue.isEmpty() && eventQueue.peek().getEventTimestamp().isBefore(now.minusMillis(TIME_WINDOW_MS))) {
						Event toProcess = eventQueue.poll();
						handleEvent(toProcess);
					}	
				}
				return evt;	
			}	
		}
		return evt;
	}

	public EventEntity handleEvent(Event event) {
		System.out.println("Processing event: " + event.getEventId());
		return eventsDao.handleEvent(event);
	}

	@Override
	public Event getEvent(String id) {
		EventEntity evt = eventsDao.getEventByEventId(id);
		Event event = new Event();
		if(Objects.nonNull(evt)) {
			BeanUtils.copyProperties(evt, event);
			return event;	
		}
		return null;
		
	}


	@Override
	public Optional<EventEntity> getEventsByAccount(String accountId) {
		return Optional.ofNullable(eventsDao.getEventsByAccount(accountId));
	}

	@Override
	public Optional<Event> getBalance(String id) {
		EventEntity evt = eventsDao.getBalanceByAccountId(id);
		Optional<Long> credBal=Arrays.asList(evt).stream().filter(e -> e.getType().equals("CREDIT")).findFirst().map(EventEntity::getAmount);
		long credBalVal = 0l,debBalVal=0l;
		if(credBal.isPresent()) {
			credBalVal = credBal.get();
		}
		Optional<Long> debBal=Arrays.asList(evt).stream().filter(evt1 -> evt1.getType().equals("DEBIT")).findFirst().map(EventEntity::getAmount);
		if(debBal.isPresent()) {
			debBalVal=debBal.get();
		}
		Event event = new Event();
		BeanUtils.copyProperties(evt, event);
		long totBal = credBalVal - debBalVal;
		event.setAmount(totBal);
		return Optional.ofNullable(event);

	}

	private boolean isReqNotContainsErrors(EventResponse uploadResponse) {
		return CollectionUtils.isEmpty(uploadResponse.getErrors());
	}
	public Event handleEventTests(Event event) {
		System.out.println("Processing event: " + event.getEventId());
		eventsDao.handleEvent(event);
		return event;
	}
}