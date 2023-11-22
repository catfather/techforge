package org.spring.dev.config;

import org.spring.dev.company.entity.util.ApproType;
import org.spring.dev.company.entity.util.GenderEntity;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = TechForgeMockSecurityContext.class)
public @interface TechForgeMockUser {

    String name() default "홍길동";

    String birth() default "2000.04.18";

    String email() default "userA@gmail.com";

    String nickName() default "홍길동";

    String phone() default "010-1111-1111";

    String password() default "";

    String postcode() default "11111";

    String address() default "서울시";

    String detailAddress() default "도봉구";

    String extraAddress() default "0000";

    ApproType grade() default ApproType.INTERN;

    GenderEntity gender() default GenderEntity.M;
}
