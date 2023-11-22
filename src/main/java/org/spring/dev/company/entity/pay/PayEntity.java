package org.spring.dev.company.entity.pay;

import lombok.Getter;
import lombok.Setter;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.util.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "c_pay")
public class PayEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_monthly") // 월급 구분 ex) 몇 월달 월급인지
    private String monthly;

    @Column(name = "pay_price") // 월급
    private Integer price;

    @Column(name = "pay_day") // 월급 기록 날
    private LocalDate payDay; //2023-10-25

    @Column(name = "is_pay", nullable = false) // 지금 여부
    private Integer isPay;

    @JoinColumn(name = "memberId", nullable = false)
    @ManyToOne
    private MemberEntity memberEntity;

}
