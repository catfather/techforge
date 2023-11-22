package org.spring.dev.company.entity.board;


import lombok.*;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.BaseEntity;
import org.spring.dev.company.entity.util.BoardType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "c_board")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_title")
    private String title;

    @Column(name = "board_content", length = 1000)
    private String content;

    @Column(name = "board_writer")
    private String writer;

    @Column(nullable = false)
    private int isFile;

    private int hit;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE)
    private List<ReplyEntity> replyEntities;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE)
    private List<FileEntity> fileEntities;

}
