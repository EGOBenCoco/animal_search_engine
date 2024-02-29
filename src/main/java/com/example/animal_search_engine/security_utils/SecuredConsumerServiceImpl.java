package com.example.animal_search_engine.security_utils;

import com.example.animal_search_engine.exception.CustomException;
import com.example.animal_search_engine.model.Consumer;
import com.example.animal_search_engine.repository.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SecuredConsumerServiceImpl implements UserDetailsService {
    private final ConsumerRepository consumerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Consumer consumer = consumerRepository.findByEmail(email).orElseThrow();
        if (!consumer.isEnabled()) {
            throw new CustomException("Consumer account is disabled", HttpStatus.UNAUTHORIZED);
        }
        return new ConsumerDetailsAdapter(consumer);
    }
}
