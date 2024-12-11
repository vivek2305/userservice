package com.harikart.userservice.service;

import com.harikart.userservice.model.User;
import com.harikart.userservice.model.Token;
import com.harikart.userservice.repositiry.TokenRepository;
import com.harikart.userservice.repositiry.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final TokenRepository tokenRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    private TokenRepository tokenRepository;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.tokenRepository = tokenRepository;
        this.tokenRepository = tokenRepository;
    }
    public User signUp(String fullName,
                       String email,
                       String password) {
        User u = new User();
        u.setEmail(email);
        u.setName(fullName);
        u.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User user = userRepository.save(u);

        return user;
    }

    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            return null;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate futureLocalDate = currentDate.plusDays(30);
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            return null;
        }
        Token token= new Token();
        token.setUser(user);
        token.setExpiryAt(Date.from(futureLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        token.setValue(RandomStringUtils.random(128));

        Token savedToken = tokenRepository.save(token);

        return savedToken;
    }


    public void logout(String token){
        Optional<Token> tokenOptional = tokenRepository.findByValueAndIsDeleted(token,false);
        if(tokenOptional.isEmpty()){
            //throw token not exist or already expired.
            return;
        }
        Token tkn= tokenOptional.get();
        tkn.setDeleted(true);
        tokenRepository.save(tkn);
        return;
    }
}
