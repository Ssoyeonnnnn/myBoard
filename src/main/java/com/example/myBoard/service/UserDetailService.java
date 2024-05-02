package com.example.myBoard.service;

import com.example.myBoard.config.PrincipalDetails;
import com.example.myBoard.entity.UserAccount;
import com.example.myBoard.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    public UserDetailService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //로그인을 누르면 여기로 온다 security가 알아서 처리해줌
        Optional<UserAccount> account = userAccountRepository.findById(username); //name을 Optional로 받음
        if (account.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        UserAccount userAccount = account.get();
        return new PrincipalDetails(userAccount);
    }
}
