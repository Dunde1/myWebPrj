package org.me.springboot.service.tests;

import lombok.RequiredArgsConstructor;
import org.me.springboot.domain.test.api.TestCalculate;
import org.me.springboot.web.dto.testApi.TestApiRequestDto;
import org.me.springboot.web.dto.testApi.TestApiResponseDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class TestService {
    /** 테스트에 관한 서비스를 담당합니다.
     *
     * fourRuleCalculations() : 값을 2개 받아와 사칙연산을 수행 시키고 그 값들을 리턴합니다.
     * 현재 DB와 연동되어있지 않습니다.
     */
    private final TestCalculate testCalculate;

    @Transactional  //추후 DB와 연동시 사용됨.
    public TestApiResponseDto fourRuleCalculations(TestApiRequestDto requestDto) {
        return testCalculate.calcs(requestDto);
    }

}
