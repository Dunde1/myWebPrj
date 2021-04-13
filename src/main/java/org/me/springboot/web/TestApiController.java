package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.service.posts.TestService;
import org.me.springboot.web.dto.testApi.TestApiRequestDto;
import org.me.springboot.web.dto.testApi.TestApiResponseDto;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
