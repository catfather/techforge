package org.spring.dev.company.dto.chatbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerDto {

    private Long id;

    private String content;

    private String keyword;

    private MemberInfo info;

    private List<MemberInfo> memberInfoList;

    public AnswerDto info(MemberInfo info){
        this.info=info;
        return this;
    }


}
