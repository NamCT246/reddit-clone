package com.namct.reddit.auth.service;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

import com.namct.reddit.users.UserModel;
import com.namct.reddit.users.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<UserModel> userOptional = userRepository.findByUsername(username);
        UserModel user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " + "Found with username : " + username));


        return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, getAuthorities("USER"));
    }

      private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}