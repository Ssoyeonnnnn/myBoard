package com.example.myBoard.config;

import com.example.myBoard.entity.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails { //security 가 관리함
    private UserAccount user;

    public PrincipalDetails(UserAccount user) {
        this.user = user;
    }

    public UserAccount getUser() { //security 가 객체를 받아옴
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add( () -> { //()는 익명함수임
            return user.getUserRole().getValue();});
        return collect;
    }

    @Override
    public String getPassword() { //비밀번호 가져옴
        return user.getUserPassword();
    }

    @Override
    public String getUsername() { //이름을 가져옴
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() { //이 계정이 만료가 된 계정인지 아닌지 확인해줌
        return true; //만료가 안된 것
    }

    @Override
    public boolean isAccountNonLocked() { //계정 잠금이 되어있는지
        return true; //계정 잠김
    }

    @Override
    public boolean isCredentialsNonExpired() { //계정이 만료기간이 지났는지
        return true; //사용 못 가능
    }

    @Override
    public boolean isEnabled() { //계정 사용이 가능한지
        return true;
    }
}
