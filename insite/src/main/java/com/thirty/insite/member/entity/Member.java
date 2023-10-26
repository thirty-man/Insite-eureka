package com.thirty.insite.member.entity;

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
public class Member {
	@Id
	@GeneratedValue
	private int memberId;

	@Column(nullable = false)
	private String kakaoId;

	@Column(nullable = false, length = 60)
	private String password;

	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime createdTime;

	@Column
	private LocalDateTime goodByeTime;

}