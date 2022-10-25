package com.example.api;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EventController {
    private final EventRepository repository;

    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    public Long nextId(){
        Long maxId = 1L;
        for(Event event: repository.findAll()){
            if(event.getId()>maxId) {
                maxId = event.getId();
            }
        }
        return maxId+1;
    }

    @CrossOrigin(origins = "http://localhost:8100")
        @GetMapping("/events")
    List<Event> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

//    @CrossOrigin(origins = "http://localhost:8100")
//    @GetMapping("/events")
//    CollectionModel<EntityModel<Event>> all() {
//        List<EntityModel<Event>> events = repository.findAll().stream()
//                .map(event -> EntityModel.of(event,
//                        linkTo(methodOn(EventController.class).one(event.getId())).withSelfRel(),
//                        linkTo(methodOn(EventController.class).all()).withRel("events"))).toList();
//        return CollectionModel.of(events,
//                linkTo(methodOn(EventController.class).all()).withSelfRel());
//    }

    @CrossOrigin(origins = "http://localhost:8100")
    @PostMapping("/events")
    Event newEvent(@RequestBody Event newEvent) {
        System.out.println(newEvent);
        return repository.findById(newEvent.getId())
                .map(event -> {
                    event.setName(newEvent.getName());
                    event.setTime(newEvent.getTime());
                    event.setLocation(newEvent.getLocation());
                    return repository.save(event);
                })
                .orElseGet(() -> {
                    newEvent.setId(nextId());
                    return repository.save(newEvent);
                });
    }

    // Single item
//    @GetMapping("/events/{id}")
//    Event one(@PathVariable Long id) {
//        return repository.findById(id)
//                .orElseThrow(() -> new EventNotFoundException(id));
//    }

    @GetMapping("/events/{id}")
    EntityModel<Event> one(@PathVariable Long id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        return EntityModel.of(event,
                linkTo(methodOn(EventController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EventController.class).all()).withRel("events"));
    }

    @PutMapping("/events/{id}")
    Event replaceEvent(@RequestBody Event newEvent, @PathVariable Long id) {

        return repository.findById(id)
                .map(event -> {
                    event.setName(newEvent.getName());
                    event.setTime(newEvent.getTime());
                    event.setLocation(newEvent.getLocation());
                    return repository.save(event);
                })
                .orElseGet(() -> {
                    newEvent.setId(id);
                    return repository.save(newEvent);
                });
    }

    @DeleteMapping("/events/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);

    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public SocketMessage send(Event message) throws Exception {
        return new SocketMessage(message);
    }
}
