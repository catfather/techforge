package org.spring.dev.company.entity.chatbot;


import lombok.*;
import org.spring.dev.company.dto.chatbot.AnswerDto;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "c_answer")
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    private String content;

    private String keyword;

    public AnswerEntity keyword(String keyword){
        this.keyword = keyword;
        return this;
    }

    public AnswerDto toAnswerDto() {
        return AnswerDto.builder()
                .id(id)
                .content(content)
                .keyword(keyword)
                .build();
    }
}
