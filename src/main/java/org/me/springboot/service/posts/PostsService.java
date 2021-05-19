package org.me.springboot.service.posts;

import lombok.RequiredArgsConstructor;
import org.me.springboot.domain.posts.Posts;
import org.me.springboot.domain.posts.PostsRepository;
import org.me.springboot.web.dto.posts.PostsListResponseDto;
import org.me.springboot.web.dto.posts.PostsResponseDto;
import org.me.springboot.web.dto.posts.PostsSaveRequestDto;
import org.me.springboot.web.dto.posts.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    /** 게시판의 게시글 DB테이블(Posts)을 조작하여 기능을 수행하는 서비스입니다.
     *
     * save() : 게시글을 저장합니다.
     * update() : 게시글을 수정합니다.
     * findById() : 게시글의 상세정보를 가져옵니다.
     * findPageDesc() : 게시글의 해당 페이지의 리스트를 가져옵니다.
     *      인자값으로 필터(filter)와 단어(word)를 줄 경우 리스트 중 해당 필터에 해당하는 단어만 검색하여 가져옵니다.
     * allRowCount() : 현재 게시글의 갯수를 가져옵니다.
     *      인자값으로 필터(filter)와 단어(word)를 줄 경우 갯수 중 해당 필터에 해당하는 단어만 검색하여 가져옵니다.
     * delete() : 게시글을 삭제합니다.
     */
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
                .map(PostsListResponseDto::new) // == .map(posts -> new PostsListResponseDto(posts))
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
