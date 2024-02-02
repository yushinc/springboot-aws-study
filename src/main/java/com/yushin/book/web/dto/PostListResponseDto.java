package com.yushin.book.web.dto;

import com.yushin.book.web.domain.posts.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {

    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;


    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.modifiedDate = post.getModifiedDate();
    }
}
