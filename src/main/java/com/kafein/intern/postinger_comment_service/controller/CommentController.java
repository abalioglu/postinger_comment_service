package com.kafein.intern.postinger_comment_service.controller;


import com.kafein.intern.postinger_comment_service.jwt.JwtUtil;
import com.kafein.intern.postinger_comment_service.model.Comment;
import com.kafein.intern.postinger_comment_service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    @PostMapping("/create-comment")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, HttpServletRequest request) {
        String jwt = commentService.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        if(comment.getId() == null && comment.getUserId() == null){
            comment.setUserId(userId);
            Comment savedComment = commentService.createOrUpdateComment(comment);
            return ResponseEntity.ok(savedComment);
        }
        else throw new RuntimeException("ID and UserID must be null");
    }
    @PostMapping("/update-comment")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment, HttpServletRequest request){
        String jwt = commentService.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        if(comment.getId() != null && comment.getUserId() == null) {
            if (Objects.equals(commentService.getUserIdByCommentId(comment.getId()), userId)) {
                comment.setUserId(userId);
                Comment updatedComment = commentService.createOrUpdateComment(comment);
                return ResponseEntity.ok(updatedComment);
            } else throw new RuntimeException("User doesn't have a post with this ID");
        }else throw new RuntimeException("ID must not be null and UserID must be null");
    }
    @DeleteMapping("/delete-comment")
    public ResponseEntity<String> deleteComment(@RequestParam(name = "commentId") Long commentId, HttpServletRequest request){
        String jwt = commentService.getToken(request);
        Long userId = jwtUtil.extractIdClaim(jwt);
        if (Objects.equals(commentService.getUserIdByCommentId(commentId), userId)) {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Comment deleted successfully");
        } else throw new RuntimeException("User doesn't have a comment with this ID");
    }
    @GetMapping("/all-comments-on-post")
    public List<Comment> getAllCommentsOnPost(@RequestParam(name = "postId") String postId){
        return commentService.getAllCommentsOnPost(postId);
    }


}
