package com.namct.reddit.users.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class RedditUserDetails extends User {
	private static final long serialVersionUID = 1274616391375075489L;

	public RedditUserDetails(String username, String password,
         Collection<? extends GrantedAuthority> authorities) {            
        super(username, password, authorities);
    }
}