package org.spring.dev.company.entity.chatbot;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "c_intention")
public class IntentionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intention_id")
    private Long id;

    private String name;

    @JoinColumn
    @ManyToOne
    private AnswerEntity answerEntity;

    @JoinColumn
    @ManyToOne
    private IntentionEntity upper; // 상위 의도

}
