package org.me.springboot.domain.test.api;

import org.me.springboot.web.dto.testApi.TestApiRequestDto;
import org.me.springboot.web.dto.testApi.TestApiResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TestCalculate {
    /** 테스트 연산을 수행하는 클래스입니다.
     *
     * calcs() : TestApiRequestDto에 있는 값 number1, number2의 사칙연산을 수행 후 값들을 TestApiResponseDto에 담아 리턴합니다.
     */
    public TestApiResponseDto calcs(TestApiRequestDto requestDto) {

        int number1 = 0, number2 = 0;
        String add, sub, mul, div;

        try {
            number1 = Integer.parseInt(requestDto.getNumber1());
            number2 = Integer.parseInt(requestDto.getNumber2());
            add = ""+(number1 + number2);
            sub = ""+(number1 - number2);
            mul = ""+(number1 * number2);
            div = ""+(number1 / number2);
        }catch (Exception e){
            e.printStackTrace();
            add = "유효하지 않는 요청";
            sub = "유효하지 않는 요청";
            mul = "유효하지 않는 요청";
            div = "유효하지 않는 요청";
        }

        return TestApiResponseDto.builder()
                .add(add)
                .sub(sub)
                .mul(mul)
                .div(div)
                .build();
    }

}
