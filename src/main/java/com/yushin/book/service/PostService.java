package com.yushin.book.service;

import com.yushin.book.web.domain.posts.Post;
import com.yushin.book.web.domain.posts.PostRepository;
import com.yushin.book.web.dto.PostResponseDto;
import com.yushin.book.web.dto.PostSaveRequestDto;
import com.yushin.book.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 등록
    @Transactional
    public Long save(PostSaveRequestDto requestDto) {
        Post post = postRepository.save(requestDto.toEntity());
        return post.getId();
    }

    // 수정
    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto) {
        Post posts = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id= " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }


    // 조회
    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id= " + id));

        return new PostResponseDto(post);
    }
}
