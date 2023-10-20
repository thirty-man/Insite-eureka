package com.thirty.ggulswriting.member.entity;

import com.thirty.ggulswriting.global.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(nullable = false)
    private String kakaoToken;

    @Column
    private LocalDateTime goodbyeTime;

    public Member create(String kakaoToken){
        return Member.builder()
            .kakaoToken(kakaoToken)
            .build();
    }

    public Member delete(){
        return Member.builder()
            .goodbyeTime(LocalDateTime.now())
            .build();
    }
}
