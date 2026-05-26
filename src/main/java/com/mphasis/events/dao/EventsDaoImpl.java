package com.mphasis.events.dao;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;

import com.mphasis.events.entity.EventEntity;
import com.mphasis.events.entity.MetadataEntity;
import com.mphasis.events.model.Event;
import com.mphasis.events.repository.EventRepository;
import com.mphasis.events.repository.MetaDataRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class EventsDaoImpl implements EventsDao {

	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	MetaDataRepository metaDataRepository;
	
	
	@Override
	public EventEntity handleEvent(Event event) {
		log.info("EventsDaoImpl => handleEvent #start");
		EventEntity entity = null;
		try {
			MetadataEntity metadataEntity = new MetadataEntity();
			BeanUtils.copyProperties(event.getMetadata(), metadataEntity);
			metaDataRepository.save(metadataEntity);
			
			entity = new EventEntity();
			BeanUtils.copyProperties(event, entity);
			entity.setMetadataId(metadataEntity.getId());
			eventRepository.save(entity);
			BeanUtils.copyProperties(entity, event);
		    System.out.println("Processed and saved event: " + event.getEventId());
	
		}catch(Exception e) {
			log.error("EventsDaoImpl => handleEvent ",e.getMessage());
		}
		log.info("EventsDaoImpl => handleEvent #end");
		return entity;
	}

	@Override
	public EventEntity getEventByEventId(String id) {
		log.info("EventsDaoImpl => getEventByEventId #start");
		return eventRepository.findByEventId(id.trim());
	}

	@Override
	public EventEntity getEventsByAccount(String accountId) {
		log.info("EventsDaoImpl => getEventsByAccount #start");
		return eventRepository.findByAccountId(accountId.trim());
	}

	@Override
	public EventEntity getBalanceByAccountId(String id) {
		return getEventsByAccount(id);
	}

}
