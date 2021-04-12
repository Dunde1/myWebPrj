package org.me.springboot.web.dto.testApi;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestApiResponseDto {
    private String add;
    private String sub;
    private String mul;
    private String div;

    @Builder
    public TestApiResponseDto(String add, String sub, String mul, String div){
        this.add = add;
        this.sub = sub;
        this.mul = mul;
        this.div = div;
    }
}
