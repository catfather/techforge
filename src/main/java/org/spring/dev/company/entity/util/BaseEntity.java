package org.spring.dev.company.entity.util;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(value = AbstractMethodError.class)
public class BaseEntity {

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime CreateTime;

    @UpdateTimestamp
    @Column(updatable = false)
    private LocalDateTime UpdateTime;

    // 만약에 회원이 탈퇴하면 더 이상 가져올 필요가 없으니까 select문을 사용할 경우 is_display가 1인 경우에만 가져오도록(null값은 나오지 않도록)
    @Column(name = "is_display")
    @ColumnDefault(value = "1")
    private int is_display;

    public BaseEntity() {
        this.is_display = 1;
    }

}
