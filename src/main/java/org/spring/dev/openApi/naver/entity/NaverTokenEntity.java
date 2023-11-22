package org.spring.dev.openApi.naver.entity;

import lombok.*;
import org.spring.dev.openApi.naver.dto.NaverTokenDto;

import javax.persistence.*;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "naver_token")
public class NaverTokenEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 500)
	private String access_token;

	private String refresh_token;

	private String scope;

	private String token_type;

	private String expires_in;

	public static NaverTokenEntity toEntity(NaverTokenDto naverTokenDto){
		return NaverTokenEntity.builder()
				.access_token(naverTokenDto.getAccess_token())
				.refresh_token(naverTokenDto.getRefresh_token())
				.scope(naverTokenDto.getScope())
				.token_type(naverTokenDto.getToken_type())
				.expires_in(naverTokenDto.getExpires_in())
				.build();

	}



}
