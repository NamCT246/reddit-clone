package com.namct.reddit.subreddit;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.namct.reddit.users.UserModel;
import com.namct.reddit.post.PostModel;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SubRedditModel {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Nullable
    private String url;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PostModel> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserModel user;

    private Instant createdAt;
}