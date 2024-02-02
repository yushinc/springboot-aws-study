package com.yushin.book.web.domain.posts;

import com.yushin.book.domain.posts.Post;
import com.yushin.book.domain.posts.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    // 데이터 모두 삭제 (@Test 어노테이션 코드 실행 후 자동 실행 - 테스트 실패해도 실행됨)
    @AfterEach
    public void cleanUp() {
        postRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .author("yushin")
                .build());

        // when
        List<Post> postList = postRepository.findAll();

        // then
        Post post = postList.get(0);
        assertEquals(post.getTitle(), title);
        assertEquals(post.getContent(), content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 2, 2, 0, 0, 0);
        postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Post> postList = postRepository.findAll();

        // then
        Post post = postList.get(0);

        System.out.println(">>>>>>>>> createdDate= " + post.getCreatedDate()
                + ", modifiedDate= " + post.getModifiedDate());

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);
    }
}