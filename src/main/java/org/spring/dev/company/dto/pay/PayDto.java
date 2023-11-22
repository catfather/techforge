package org.spring.dev.company.dto.pay;

import lombok.*;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.entity.pay.PayEntity;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayDto {

    private Long id;

    private String monthly;

    private Integer price;

    private LocalDate payDay; //2023-10-25

    private Integer isPay;

    private MemberEntity memberEntity;

    private LocalDateTime CreateTime;

    private LocalDateTime UpdateTime;

    private Integer is_display;

    public static PayDto toDto(PayEntity payEntity) {
        return PayDto.builder()
                .id(payEntity.getId())
                .price(payEntity.getPrice())
                .monthly(payEntity.getMonthly())
                .payDay(payEntity.getPayDay())
                .isPay(payEntity.getIsPay())
                .memberEntity(payEntity.getMemberEntity())
                .CreateTime(payEntity.getCreateTime())
                .UpdateTime(payEntity.getUpdateTime())
                .is_display(payEntity.getIs_display())
                .build();
    }
}
