package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.service.tests.TestService;
import org.me.springboot.web.dto.testApi.TestApiRequestDto;
import org.me.springboot.web.dto.testApi.TestApiResponseDto;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TestApiController {

    private final TestService testService;

    @CrossOrigin("*")
    @RequestMapping("/test/api/main/number")
    public TestApiResponseDto apiTakeAndGive(@RequestBody TestApiRequestDto requestDto) {
        return testService.FourRuleCalculations(requestDto);
    }
}
