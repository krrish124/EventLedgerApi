package com.mphasis.events.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mphasis.events.entity.EventEntity;
import com.mphasis.events.model.Event;
import com.mphasis.events.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class EventController {
    
	@Autowired
    private EventService eventService;

    /*****
     * 
     * @param event
     * @return
     * Post event
     */
    @PostMapping("/events")
    public ResponseEntity<?> postEvent(@Valid @RequestBody Event event) {
    	try {
    		Event evt = eventService.processEvent(event);
    		return  Optional.ofNullable(evt)
            		.map(e -> new ResponseEntity<>(e, HttpStatus.ALREADY_REPORTED))
            		.orElse(new ResponseEntity<>(HttpStatus.OK));	
    	}catch(Exception e) {
    		return null;
    	}
        
    }
    
    /****
     * 
     * @param id
     * @return
     * get Event based on event id
     */
    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getEvent(
    		 @Parameter(description = "ID of the event to fetch events for", required = true, example = "12345")
    		@PathVariable String id) {
    	Event evt = eventService.getEvent(id);
    	return Optional.ofNullable(evt)
    			.map(event -> new ResponseEntity<>(event, HttpStatus.OK))
    			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    /****
     * 
     * @param accountId
     * @return
     * get Events based on Account id
     */
    @Operation(summary = "Get events by account ID", description = "Returns a list of events associated with a specific account")
    @GetMapping("/events")
    public ResponseEntity<EventEntity> getEventsByAccount(
            @Parameter(description = "ID of the account to fetch events for", required = true, example = "12345")
            @RequestParam("account") String accountId) {
        return eventService.getEventsByAccount(accountId)
        		.map(event -> new ResponseEntity<>(event, HttpStatus.OK))
    			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    /****
     * 
     * @param id
     * @return
     * get balance based on account id
     */
    @Operation(summary = "Get account balance", description = "Returns the current balance for a specific account ID")
    @GetMapping("/accounts/{id}/balance")
    public ResponseEntity<Event> getBalance(@PathVariable("id") String id) {
       return eventService.getBalance(id)
    		   .map(event -> new ResponseEntity<>(event, HttpStatus.OK))
   			.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}