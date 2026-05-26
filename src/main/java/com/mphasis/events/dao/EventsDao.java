package com.mphasis.events.dao;

import com.mphasis.events.entity.EventEntity;
import com.mphasis.events.model.Event;

public interface EventsDao {

	EventEntity handleEvent(Event event);

	EventEntity getEventByEventId(String id);

	EventEntity getEventsByAccount(String accountId);

	EventEntity getBalanceByAccountId(String id);

}
