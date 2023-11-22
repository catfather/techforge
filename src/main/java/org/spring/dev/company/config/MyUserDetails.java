package org.spring.dev.company.config;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.dev.company.entity.member.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor
public class MyUserDetails implements UserDetails, OAuth2User {

    @Getter
    @Autowired
    private MemberEntity memberEntity;

    private Map<String, Object> attributes;
    private int is_display;

    // 일반
    public MyUserDetails(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
        this.is_display = memberEntity.getIs_display();
    }

    // oAuth2
    public MyUserDetails(MemberEntity memberEntity, Map<String, Object> attributes) {
        this.memberEntity = memberEntity;
        this.attributes = attributes;
        this.is_display = memberEntity.getIs_display();
    }


    @Override
    public String getName() {
        return memberEntity.getName();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_" + memberEntity.getGrade().toString(); // ROLE_
            }
        });
        return collection;
    }

    public void setIs_display(int is_display) {
        this.is_display = is_display;
    }

    @Override
    public String getPassword() {
        return memberEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return memberEntity.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return is_display == 1;
    }

}
