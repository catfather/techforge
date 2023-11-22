package org.spring.dev.company.entity.department;

import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "c_department")
public class DepartmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name")
    private String name;

    // n:1 하나의 부서에 여러명이 들어갈 수 있움
    // 지연로딩 생각하기
    @JoinColumn(name = "memberEntity")
    @ManyToOne
    private MemberEntity memberEntity;
}
