package org.spring.dev.openApi.naver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.spring.dev.openApi.naver.dto.NaverTokenDto;
import org.spring.dev.openApi.naver.dto.OrgResponse;
import org.spring.dev.openApi.naver.entity.NaverTokenEntity;
import org.spring.dev.openApi.naver.repository.NaverRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class NaverService {

    private final NaverRepository naverRepository;

    @Value("${naver.api.client-id}")
    String CLIENT_ID;

    @Value("${naver.api.client-secret}")
    String CLIENT_SECRET;

    @Value("${naver.auth-url}")
    String NAVER_URL;

    @Value("${naver.work-url}")
    String NAVER_WORK_URL;

    @Value("${naver.auth.uri.authorize}")
    String NAVER_URI_AUTH;

    @Value("${naver.auth.uri.token}")
    String NAVER_URI_TOKEN;

    @Value("${naver.scope}")
    String NAVER_SCOPE;

    @Value("${naver.org.units}")
    String NAVER_ORG_UNITS;

    @Value("${naver.redirect-url}")
    String NAVER_REDIRECT_URL;;


    public String getNaverCode() {
        String naverAuthHtml = null;

        RestTemplate restTemplate = new RestTemplate();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = null;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        URI uri = UriComponentsBuilder
                .fromUriString(NAVER_URL)
                .path(NAVER_URI_AUTH)
                .queryParam("redirect_uri", NAVER_REDIRECT_URL) //code를 받아올 return url
                .queryParam("client_id", CLIENT_ID)
                .queryParam("response_type", "code") // code로 지정 값
                .queryParam("scope", NAVER_SCOPE) //사용할 권환들
                .queryParam("state", "CSRF") // CSRF 고정
                .encode()
                .build()
                .toUri();

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        if (result.getStatusCode() == HttpStatus.OK) {
            try {
                naverAuthHtml = result.getBody();
                jsonStr = objectMapper.writeValueAsString(result.getBody());
                System.out.println(jsonStr);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("json JsonProcessingException : " + e);
            }
        } else {
            System.out.println(result.getStatusCode());
        }


        return naverAuthHtml;
    }

    public String getNaverToken(String code, String errorCode, String state) {

        RestTemplate restTemplate = new RestTemplate();
        String accessToken = "토큰 발급이 안되었습니다";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        URI uri = UriComponentsBuilder
                .fromUriString(NAVER_URL)
                .path(NAVER_URI_TOKEN)
                .queryParam("code", code)
                .queryParam("client_id", CLIENT_ID)
                .queryParam("grant_type", "authorization_code") // 고정
                .queryParam("client_secret", CLIENT_SECRET) //사용할 권환들
                .encode()
                .build()
                .toUri();

        ResponseEntity<NaverTokenDto> result = restTemplate.exchange(uri, HttpMethod.POST, entity, NaverTokenDto.class);

        if (result.getStatusCode() == HttpStatus.OK) {

            accessToken = naverRepository.save(NaverTokenEntity.toEntity(result.getBody())).getAccess_token();

        } else {
            System.out.println(result.getStatusCode());
        }

        return accessToken;
    }

    public OrgResponse getOrgUnitList() {

        //db에 저장한 가장 최신 토큰을 가져온다.
        NaverTokenEntity naverTokenEntity =naverRepository.findFirstByOrderByIdDesc();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+naverTokenEntity.getAccess_token());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        URI uri = UriComponentsBuilder
                .fromUriString(NAVER_WORK_URL)
                .path(NAVER_ORG_UNITS)
                .encode()
                .build()
                .toUri();

        ResponseEntity<OrgResponse> result = restTemplate.exchange(uri, HttpMethod.GET, entity, OrgResponse.class);

        if (result.getStatusCode() == HttpStatus.OK) {

            System.out.println("정상적으로 실행");

        } else {
            System.out.println(result.getStatusCode());
        }

        return result.getBody();
    }
}
