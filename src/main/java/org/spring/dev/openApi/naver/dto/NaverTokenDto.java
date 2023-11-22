package org.spring.dev.openApi.naver.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NaverTokenDto {

	private String access_token;
	private String refresh_token;
	private String scope;
	private String token_type;
	private String expires_in;

}
