package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.service.posts.TestService;
import org.me.springboot.web.dto.testApi.TestApiRequestDto;
import org.me.springboot.web.dto.testApi.TestApiResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestApiController {

    private final TestService testService;

    @PostMapping("/test/api/main/number")
    public TestApiResponseDto apiTakeAndGive(@RequestBody TestApiRequestDto requestDto) {
        return testService.FourRuleCalculations(requestDto);
    }
}
