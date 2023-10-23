package com.thirty.ggulswriting.message.entity;

import com.thirty.ggulswriting.global.entity.BaseEntity;
import com.thirty.ggulswriting.participation.entity.Participation;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "Message")
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "participation_to", nullable = false)
    private Participation participationTo;

    @ManyToOne
    @JoinColumn(name = "participation_from", nullable = false)
    private Participation participationFrom;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isCheck;

    @Column(nullable = false)
    private String honeyCaseType;

    @Column(nullable = false)
    private String nickName;

    public void markAsChecked() {
        this.isCheck = true;
    }

    public Message create(Participation to, Participation from, String content, Boolean isCheck, String honeyCaseType, String nickName){
        return Message.builder()
            .participationTo(to)
            .participationFrom(from)
            .content(content)
            .isCheck(isCheck)
            .honeyCaseType(honeyCaseType)
            .nickName(nickName)
            .build();
    }
}
