package com.yushin.book.web.dto;

import com.yushin.book.domain.posts.Post;
import lombok.Getter;

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
