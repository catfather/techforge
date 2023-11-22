package org.spring.dev.company.entity.board;

import lombok.*;
import org.spring.dev.company.entity.util.BaseEntity;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "c_file")
public class FileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false, name = "file_old_name")
    private String oldName;

    @Column(nullable = false, name = "file_new_name")
    private String newName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

}
