package com.kafein.intern.postinger_comment_service.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "COMMENT_TABLE")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "POST_ID")
    private String postId;

    @Column(name = "CONTENT")
    private String content;
}
