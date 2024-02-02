package com.yushin.book.web;

import com.yushin.book.service.PostService;
import com.yushin.book.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostService postService;

    // 글 목록 조회
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAllDesc());
        return "index"; // View Resolver가 index.mustache 리턴 처리
    }

    // 글 등록
    @GetMapping("/posts/save")
    public String postSave() {
        return "post-save";
    }

    // 글 수정
    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostResponseDto dto = postService.findById(id);
        model.addAttribute("post", dto);

        return "post-update";
    }

}
