package com.namct.reddit.comment;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.namct.reddit.post.PostModel;
import com.namct.reddit.users.UserModel;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CommentModel {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long commentId;

    @NotBlank
    private String text;

    private Instant createdDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private PostModel post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserModel user;
}