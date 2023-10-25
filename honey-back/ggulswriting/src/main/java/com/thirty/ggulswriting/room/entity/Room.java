package com.thirty.ggulswriting.room.entity;

import com.thirty.ggulswriting.global.entity.BaseEntity;
import com.thirty.ggulswriting.member.entity.Member;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 100)
    private String roomTitle;

    @Column(nullable = false)
    private LocalDateTime showTime;

    @Column
    private String password;

    @Column(nullable = false)
    private Boolean isDeleted;

    @LastModifiedDate
    @UpdateTimestamp
    @Column
    private LocalDateTime updateTime;

    public Room create(Member member, String title, LocalDateTime showTime, String password){
        return Room.builder()
            .member(member)
            .roomTitle(title)
            .showTime(showTime)
            .password(password)
            .build();
    }

    public void changeMaster(Member member){
        this.member = member;
    }

    public void delete(){
        this.isDeleted = true;
    }
}
