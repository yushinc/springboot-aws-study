package com.yushin.book.web.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        // junit5
        assertEquals(dto.getName(), name);
        assertEquals(dto.getAmount(), amount);

        // junit4
        // assertThat(dto.getName()).isEqualTo(name);
        // assertThat(dto.getAmount()).isEqualTo(amount);

    }
}