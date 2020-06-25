package com.prospringboot.messagingRabbitMQ.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ToDo {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @NotBlank
    private String description;

    @Column(insertable = true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;

    public ToDo(String description) {
        this.description = description;
    }

    @PrePersist
    void onCreate(){
        setCreated(LocalDateTime.now());
        setModified(LocalDateTime.now());
    }

    @PreUpdate
    void onModified(){
        setModified(LocalDateTime.now());
    }
}
