package org.me.springboot.web.dto.testApi;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestApiRequestDto {
    private String number1;
    private String number2;

    @Builder
    public TestApiRequestDto(String number1, String number2) {
        this.number1 = number1;
        this.number2 = number2;
    }
}
