package org.spring.dev.company.dto.chatbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatMessageDto {

    private String today;

    private String time;

    private AnswerDto answer;

    public ChatMessageDto today(String today) {
        this.today=today;
        return this;
    }
    public ChatMessageDto answer(AnswerDto answer) {
        this.answer=answer;
        return this;
    }


}
