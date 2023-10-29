package com.thirty.ggulswriting.room.entity;

import com.thirty.ggulswriting.global.entity.BaseEntity;
import com.thirty.ggulswriting.member.entity.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

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

    public static Room create(Member member, String title, LocalDateTime showTime, String password, boolean isOpen){
        if(!isOpen){
            return Room.builder()
                .member(member)
                .roomTitle(title)
                .showTime(showTime)
                .password(password)
                .isDeleted(false)
                .build();
        }else{
            return Room.builder()
                .member(member)
                .roomTitle(title)
                .showTime(showTime)
                .isDeleted(false)
                .build();
        }
    }

    public void changeMaster(Member member){
        this.member = member;
    }

    public void delete(){
        this.isDeleted = true;
    }

    public void modify(String roomTitle, String password, boolean isOpen){
        this.roomTitle = roomTitle;

        if(!isOpen){
            this.password = password;
        }
    }
}
