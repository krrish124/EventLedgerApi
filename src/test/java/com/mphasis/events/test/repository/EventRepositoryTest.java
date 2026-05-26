package com.mphasis.events.test.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.mphasis.events.entity.EventEntity;
import com.mphasis.events.entity.MetadataEntity;
import com.mphasis.events.model.Event;
import com.mphasis.events.model.Metadata;
import com.mphasis.events.repository.EventRepository;
import com.mphasis.events.repository.MetaDataRepository;

@DataJpaTest
@Transactional
public class EventRepositoryTest {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	MetaDataRepository metaDataRepository;

	@Test
	public void testEventId() throws Exception {
		Event entity = createEvtObj();
		assertNotNull(entity);
		assertEquals("evt-002", entity.getEventId());
	}

	@Test
	public void testFindEventById() {
		createEvtObj();
		Optional<EventEntity> foundEvent = eventRepository.findById("evt-002");
		assertTrue(foundEvent.isPresent());
		assertEquals("evt-002", foundEvent.get().getEventId());
	}
	@Test
	public void testDeleteEvent() {
		createEvtObj();
		eventRepository.deleteById("evt-002");
		Optional<EventEntity> foundEvent = eventRepository.findById("evt-002");
		assertFalse(foundEvent.isPresent());
	}
	public Event createEvtObj() {
		Event event = new Event();
		event.setEventId("evt-002");
		event.setEventTimestamp(Instant.now());
		event.setAmount(100.50);
		event.setAccountId("acct-123");
		event.setCurrency("USD");
		event.setType("CREDIT");

		Metadata metadata = new Metadata();
		metadata.setBatchId("B-9042");
		metadata.setSource("mainframe-batch");
		event.setMetadata(metadata);

		MetadataEntity metadataEntity = new MetadataEntity();
		BeanUtils.copyProperties(metadata, metadataEntity);
		metaDataRepository.save(metadataEntity);
		
		EventEntity entity = new EventEntity();
		BeanUtils.copyProperties(event, entity);
		entity.setMetadataId(metadataEntity.getId());
		eventRepository.save(entity);
		
		return event;
		
	}
}
