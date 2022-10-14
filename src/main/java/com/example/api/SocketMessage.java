package com.example.api;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SocketMessage implements Serializable {
    private Long id;
    private String name;
    private LocalDateTime time;
    private String location;

    public SocketMessage() {
    }

    public SocketMessage(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.time = event.getTime();
        this.location = event.getLocation();
    }


    public SocketMessage(Long id, String name, LocalDateTime time, String location) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
