package com.example.animal_search_engine.security_utils;

import com.example.animal_search_engine.model.Consumer;
import org.springframework.security.core.userdetails.UserDetails;

public interface ConsumerAdapter extends UserDetails {
    Consumer consumer();
}

