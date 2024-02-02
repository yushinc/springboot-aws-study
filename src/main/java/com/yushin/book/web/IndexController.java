package com.yushin.book.web;

import com.yushin.book.config.auth.LoginUser;
import com.yushin.book.config.auth.dto.SessionUser;
import com.yushin.book.service.PostService;
import com.yushin.book.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostService postService;
    private final HttpSession httpSession;

    // 글 목록 조회
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postService.findAllDesc());

        // 리팩토링하여 삭제된 코드
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");

        // 유저가 존재한다면 userName 모델에 저장
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

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
