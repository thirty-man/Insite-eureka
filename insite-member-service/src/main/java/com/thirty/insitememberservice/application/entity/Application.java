package com.thirty.insitememberservice.application.entity;

import com.thirty.insitememberservice.member.entity.Member;
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
public class Application {

	@Id
	@GeneratedValue
	private int applicationId;

	@Column(nullable = false)
	private String serviceUrl;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;


	@Column(nullable = false, length = 60)
	private String serviceToken;

	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime createdTime;

}