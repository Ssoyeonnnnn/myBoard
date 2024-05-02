package com.example.myBoard.service;

import com.example.myBoard.constant.UserRole;
import com.example.myBoard.dto.UserCreateForm;
import com.example.myBoard.entity.UserAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
//    @PersistenceContext
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;

    @Transactional
    public void createUser(UserCreateForm userCreateForm) { //회원가입을 할 때
        UserAccount account = new UserAccount();
        account.setUserId(userCreateForm.getUsername());
        account.setUserPassword(passwordEncoder.encode(
                userCreateForm.getPassword1()
        ));
        account.setEmail(userCreateForm.getEmail());
        account.setNickname(userCreateForm.getNickname());
        if ("ADMIN".equals(userCreateForm.getUsername().toUpperCase())) { //admin 이면 관리자 권한을 준다
            account.setUserRole(UserRole.ADMIN);
        } else {
            account.setUserRole(UserRole.USER); //그 외는 사용자 권한을 준다
        }
        em.persist(account);
    }
}
