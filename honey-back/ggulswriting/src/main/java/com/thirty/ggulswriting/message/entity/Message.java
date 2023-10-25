package com.thirty.ggulswriting.message.entity;

import com.thirty.ggulswriting.global.entity.BaseEntity;
import com.thirty.ggulswriting.participation.entity.Participation;
import lombok.*;

import javax.persistence.*;

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

    public static Message create(Participation to, Participation from, String content, Boolean isCheck, String honeyCaseType, String nickName){
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
