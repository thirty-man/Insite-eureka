package com.thirty.ggulswriting.message.dto;

import com.thirty.ggulswriting.message.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageListDto {

    private int id;

    private Boolean isCheck;

    private String honeyCaseType;

    private String nickName;

    public static MessageListDto from(Message message){
        return MessageListDto.builder()
                .id(message.getMessageId())
                .isCheck(message.getIsCheck())
                .honeyCaseType(message.getHoneyCaseType())
                .nickName(message.getNickName())
                .build();
    }
}
