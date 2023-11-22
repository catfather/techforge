package org.spring.dev.company.service.chatbot;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;
import org.spring.dev.company.dto.chatbot.AnswerDto;
import org.spring.dev.company.dto.chatbot.ChatMessageDto;
import org.spring.dev.company.dto.chatbot.MemberInfo;
import org.spring.dev.company.entity.chatbot.AnswerEntity;
import org.spring.dev.company.entity.chatbot.IntentionEntity;
import org.spring.dev.company.entity.member.MemberEntity;
import org.spring.dev.company.repository.chatbot.IntentionRepository;
import org.spring.dev.company.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KomoranService {

    private final Komoran komoran;
    private final IntentionRepository intentionRepository;
    private final MemberRepository memberRepository;

    public ChatMessageDto analyze(String message) {

        KomoranResult result = komoran.analyze(message);

        // 메세지(문자열)에서 명사(nouns)추출 -> set에 저장
        Set<String> nouns = result.getNouns().stream().collect(Collectors.toSet());

        return analyzeToken(nouns);
    }

    private Optional<IntentionEntity> decisionTree(String token, IntentionEntity upper) {
        return intentionRepository.findByNameAndUpper(token, upper);
    }

    private ChatMessageDto analyzeToken(Set<String> nouns) {

        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a h:m");
        ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                .time(today.format(timeFormatter))
                .build();


        for(String token: nouns){

            // 1차 의도 존재 파악
            Optional<IntentionEntity> result = decisionTree(token, null);

            if (result.isEmpty()) continue;

            // 1차 목록 복사
            Set<String> next = nouns.stream().collect(Collectors.toSet());
            next.remove(token);

            // 2차 분석
            AnswerDto answer = analyzeToken(next, result).toAnswerDto();

            // 탐색
            if (token.contains("프리랜서")) {
                MemberInfo freelancer = analyzeTokenIsUser(next);
                answer.info(freelancer);
            } else if (token.contains("안녕")) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
                chatMessageDto.today(today.format(dateFormatter));
            } else if (token.contains("회사")) {
                MemberInfo company = analyzeTokenIsCompany(next);
                answer.info(company);
            } else if (token.contains("도움")){
                AnswerDto help = decisionTree("도움", null).get().getAnswerEntity().toAnswerDto();
                chatMessageDto.answer(help);
            }

            chatMessageDto.answer(answer);
            return chatMessageDto;

        }

        AnswerDto answer = decisionTree("기타", null).get().getAnswerEntity().toAnswerDto();
        chatMessageDto.answer(answer);
        return chatMessageDto;

    }

    private AnswerEntity analyzeToken(Set<String> next, Optional<IntentionEntity> upper) {

        for (String token : next) {
            Optional<IntentionEntity> result = decisionTree(token, upper.get());
            if (result.isEmpty()) continue;
            return result.get().getAnswerEntity();
        }
        return upper.get().getAnswerEntity();
    }

    // 프리랜서면 프리랜서 권한을 가진 유저만 찾아 MemberInfo 객체로 반환
    private MemberInfo analyzeTokenIsUser(Set<String> next) {

        for (String name : next) {
            Optional<MemberEntity> member = memberRepository.findByName(name);

            if (!member.isPresent() || !member.get().getGrade().toString().equals("FREELANCER")) {

                continue;
            }

            String career = member.get().getCareer();
            String phone = member.get().getPhone();
            String userName = member.get().getName();
            String email = member.get().getEmail();


            return MemberInfo.builder()
                    .career(career)
                    .phone(phone)
                    .userName(userName)
                    .email(email)
                    .build();

        }
        return MemberInfo.builder().build();
    }

    private MemberInfo analyzeTokenIsCompany(Set<String> next) {

        for (String name : next) {

            Optional<MemberEntity> member = memberRepository.findByCompanyName2(name);
            if (!member.isPresent() || !member.get().getGrade().toString().equals("COMPANY")) continue;

            String companyName = member.get().getCompanyName();
            String phone = member.get().getPhone();
            String email = member.get().getEmail();
            String businessNumber = member.get().getBusinessNumber();

            return MemberInfo.builder()
                    .companyName(companyName)
                    .phone(phone)
                    .email(email)
                    .businessNumber(businessNumber)
                    .build();

        }
        return MemberInfo.builder().build();
    }

}
