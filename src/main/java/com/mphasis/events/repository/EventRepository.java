package com.mphasis.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mphasis.events.entity.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, String> {

	@Query(" from EventEntity where eventId in :id")
	EventEntity findByEventId(@Param("id") String id);

	@Query(" from EventEntity where accountId in :id order by eventTimestamp asc")
	EventEntity findByAccountId(@Param("id") String id);
	
}
