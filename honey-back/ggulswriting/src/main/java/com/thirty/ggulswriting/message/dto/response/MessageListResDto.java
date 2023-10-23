package com.thirty.ggulswriting.message.dto.response;

import com.thirty.ggulswriting.message.dto.MessageListDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageListResDto {

    private List<MessageListDto> messageListDtoList;

    public static MessageListResDto from(List<MessageListDto> messageListDtoList){
        return MessageListResDto.builder()
                .messageListDtoList(messageListDtoList)
                .build();
    }
}
