package com.namct.reddit.auth.token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.namct.reddit.users.UserModel;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id 
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = LAZY)
    private UserModel user;
    private Instant expiryDate;
}