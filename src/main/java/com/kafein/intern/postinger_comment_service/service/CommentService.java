package com.kafein.intern.postinger_comment_service.service;

import com.kafein.intern.postinger_comment_service.model.Comment;
import com.kafein.intern.postinger_comment_service.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    public String getToken(HttpServletRequest request){
        String jwt = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT_TOKEN")) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }return jwt;
    }
    public Comment createOrUpdateComment(Comment comment) { //userID
        return commentRepository.save(comment);
    }
    public void deleteComment(Long commentId) { //kendi yazdığınsa yani userId önemli
        commentRepository.deleteById(commentId);
    }
    public List<Comment> getAllCommentsOnPost(String postId){ //herhangi bi post'unkileri görebilir
        return commentRepository.findAllByPostId(postId);
    }
    public Comment getPostById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }
    public Long getUserIdByCommentId(Long commentId){
        Comment comment = getPostById(commentId);
        return comment.getUserId();
    }



}
