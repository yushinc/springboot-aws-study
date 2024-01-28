package com.yushin.book.web.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postsRepository;

    // 데이터 모두 삭제 (@Test 어노테이션 코드 실행 후 자동 실행 - 테스트 실패해도 실행됨)
    @AfterEach
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author("yushin")
                .build());

        // when
        List<Post> postList = postsRepository.findAll();

        // then
        Post post = postList.get(0);
        assertEquals(post.getTitle(), title);
        assertEquals(post.getContent(), content);
    }
}