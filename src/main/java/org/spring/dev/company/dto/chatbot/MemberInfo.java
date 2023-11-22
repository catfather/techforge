package org.spring.dev.company.dto.chatbot;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MemberInfo {

    String userName;
    String companyName;
    String career;
    String email;
    String phone;
    String businessNumber;

}
