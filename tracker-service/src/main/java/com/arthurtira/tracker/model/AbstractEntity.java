package com.arthurtira.tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Data
public class AbstractEntity {

    private static final long serialVersionUID = 8469507098449006251L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "entity_id")
    private String entityId;

    private Date createdOn;

    private Date updatedOn;

    public AbstractEntity() {
        UUID uuid = UUID.randomUUID();
        this.entityId = uuid.toString();
    }

    @PrePersist
    public void prePresist() {
        this.createdOn = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedOn = new Date();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) obj;

        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    public String toString() {
        try {
            return Jackson2ObjectMapperBuilder.json().build().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
