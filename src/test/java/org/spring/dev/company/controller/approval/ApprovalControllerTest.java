package org.spring.dev.company.controller.approval;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.spring.dev.company.controller.approval.request.ApprovalCreate;
import org.spring.dev.company.entity.approval.ApprovalEntity;
import org.spring.dev.company.entity.util.ApproType;
import org.spring.dev.company.repository.approval.ApprovalRepository;
import org.spring.dev.company.repository.member.MemberRepository;
import org.spring.dev.config.TechForgeMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApprovalControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
        approvalRepository.deleteAllInBatch();
    }

    @TechForgeMockUser
    @Test
    @Disabled
    @DisplayName("회사 측에서 프리랜서들에게 프로젝트 투입 서류를 올린다.")
    public void test1() throws Exception {
        // given
        ApprovalCreate request = ApprovalCreate.builder()
                .title("프로젝트 투입 결재")
                .content("프로젝트 관련")
                .build();

        // expect
        mockMvc.perform(post("/api/v1/approval/create")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @TechForgeMockUser
    @Test
    @Disabled
    @DisplayName("프리랜서가 회사의 프로젝트 모집 글을 보고 승인 요청을 보낸다.")
    public void test2() throws Exception {
        // given
        ApprovalEntity approval = ApprovalEntity.builder()
                .title("프로젝트 투입 결재")
                .content("프로젝트 관련")
                .type(ApproType.MANAGER)
                .build();

        ApprovalEntity save = approvalRepository.save(approval);

        String request = "APPROVAL";


        // expect
        mockMvc.perform(post("/api/v1/approval/{approveId}", save.getId())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @TechForgeMockUser
    @Test
    @Disabled
    @DisplayName("프리랜서가 회사의 프로젝트 모집 글을 보고 반려를 보낸다.")
    public void test3() throws Exception {
        // given
        ApprovalEntity approval = ApprovalEntity.builder()
                .title("프로젝트 투입 결재")
                .content("프로젝트 관련")
                .type(ApproType.MANAGER)
                .build();

        ApprovalEntity save = approvalRepository.save(approval);

        String request = "UNAUTHORIZED";


        // expect
        mockMvc.perform(post("/api/v1/approval/{approveId}", save.getId())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

}