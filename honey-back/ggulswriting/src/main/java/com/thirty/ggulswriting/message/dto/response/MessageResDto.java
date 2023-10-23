package com.thirty.ggulswriting.message.dto.response;

import com.thirty.ggulswriting.message.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageResDto {
    private String content;
    private String honeyCaseType;
    private String nickName;

    public static MessageResDto from(Message message) {
        return new MessageResDto(
                message.getContent(),
                message.getHoneyCaseType(),
                message.getNickName()
        );
    }
}
