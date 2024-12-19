package org.example.relocantsbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "event_types")
public class EventType {

    @Id
    private int id;

    @Column(nullable = false)
    private String type_name;

    public EventType() {}

    public EventType(int id, String name) {
        this.id = id;
        this.type_name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return type_name;
    }

    public void setName(String name) {
        this.type_name = name;
    }

    @Override
    public String toString() {
        return "EventType{id=" + id + ", name='" + type_name + "'}";
    }
}
