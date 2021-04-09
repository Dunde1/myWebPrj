package org.me.springboot.service.posts.comments;

import lombok.RequiredArgsConstructor;
import org.me.springboot.domain.posts.Posts;
import org.me.springboot.domain.posts.comments.Comments;
import org.me.springboot.domain.posts.comments.CommentsId;
import org.me.springboot.domain.posts.comments.CommentsRepository;
import org.me.springboot.web.dto.posts.PostsListResponseDto;
import org.me.springboot.web.dto.posts.PostsResponseDto;
import org.me.springboot.web.dto.posts.PostsSaveRequestDto;
import org.me.springboot.web.dto.posts.PostsUpdateRequestDto;
import org.me.springboot.web.dto.posts.comments.CommentsListResponseDto;
import org.me.springboot.web.dto.posts.comments.CommentsSaveRequestDto;
import org.me.springboot.web.dto.posts.comments.CommentsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;

    @Transactional(readOnly = true)
    public List<CommentsListResponseDto> findComments(Long postId) {
        return commentsRepository.findComments(postId).stream()
                .map(CommentsListResponseDto::new) // == .map(comments -> new CommentsListResponseDto(comments))
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentsId save(CommentsSaveRequestDto requestDto) {
        return commentsRepository.save(requestDto.toEntity()).getCommentsId();
    }

    @Transactional
    public CommentsId update(CommentsId commentsId, CommentsUpdateRequestDto requestDto) {
        Comments comments = commentsRepository.findById(commentsId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + commentsId));
        comments.update(requestDto.getComment());
        return commentsId;
    }

    @Transactional
    public void delete(CommentsId commentsId) {
        Comments comments = commentsRepository.findById(commentsId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+commentsId));
        commentsRepository.delete(comments);
    }
}
