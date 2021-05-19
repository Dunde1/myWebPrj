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

    private final TestCalculate testCalculate;

    @Transactional
    public TestApiResponseDto FourRuleCalculations(TestApiRequestDto requestDto) {
        return testCalculate.Calcs(requestDto);
    }

}
