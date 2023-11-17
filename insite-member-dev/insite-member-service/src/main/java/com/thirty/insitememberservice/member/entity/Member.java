package com.thirty.insitememberservice.member.entity;

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