package com.thirty.insite.application.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Entity
@AllArgsConstructor
@Builder
public class Application {

	@Id
	@GeneratedValue
	private int applicationId;

	@Column(nullable = false)
	private String serviceUrl;

	@Column(nullable = false, length = 60)
	private String serviceToken;

	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime createdTime;

}