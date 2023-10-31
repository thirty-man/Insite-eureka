package com.thirty.insitememberservice.button.entity;

import com.thirty.insitememberservice.application.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
@AllArgsConstructor
@Builder
public class Button {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int buttonId;

    @ManyToOne
    @JoinColumn(name= "application_id", nullable = false)
    private Application application;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Column
    private boolean isDeleted;

    public static Button create(Application application, String name){
        return Button.builder()
            .application(application)
            .name(name)
            .isDeleted(false)
            .build();
    }

    public void modify(String name){this.name = name;}

    public void delete(){this.isDeleted= true;}




}
