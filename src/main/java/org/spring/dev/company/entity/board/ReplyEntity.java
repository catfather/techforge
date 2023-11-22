package org.spring.dev.company.entity.board;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.BaseEntity;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "c_reply")
public class ReplyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Column(name = "reply_content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id")

    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;
}
