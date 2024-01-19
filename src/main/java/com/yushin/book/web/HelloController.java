package com.yushin.book.web;

import com.yushin.book.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam(name = "name") String name,
                                     @RequestParam(name = "amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
