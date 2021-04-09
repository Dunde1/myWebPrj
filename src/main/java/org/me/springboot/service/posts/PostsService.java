package org.me.springboot.service.posts;

import lombok.RequiredArgsConstructor;
import org.me.springboot.domain.posts.Posts;
import org.me.springboot.domain.posts.PostsRepository;
import org.me.springboot.web.dto.PostsListResponseDto;
import org.me.springboot.web.dto.PostsResponseDto;
import org.me.springboot.web.dto.PostsSaveRequestDto;
import org.me.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findPageDesc(int page) {
        return postsRepository.findPageDesc(page).stream()
                .map(PostsListResponseDto::new) // == .map(posts -> new PostsListResponseDto(Posts))
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findPageDesc(int page, String filter, String word) {
        if(filter.equals("title")) {
            return postsRepository.findTitlePageDesc(page, "%"+word+"%").stream()
                    .map(PostsListResponseDto::new) // == .map(posts -> new PostsListResponseDto(Posts))
                    .collect(Collectors.toList());
        }else{
            return postsRepository.findAuthorPageDesc(page, "%"+word+"%").stream()
                    .map(PostsListResponseDto::new) // == .map(posts -> new PostsListResponseDto(Posts))
                    .collect(Collectors.toList());
        }
    }

    @Transactional(readOnly = true)
    public int allRowCount(){
        return postsRepository.allRowCount();
    }

    @Transactional(readOnly = true)
    public int allRowCount(String filter, String word){
        if(filter.equals("title")) {
            return postsRepository.allTitleRowCount("%"+word+"%");
        }else{
            return postsRepository.allAuthorRowCount("%"+word+"%");
        }
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(posts);
    }
}
