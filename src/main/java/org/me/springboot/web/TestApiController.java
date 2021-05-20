package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.service.tests.TestService;
import org.me.springboot.web.dto.testApi.TestApiRequestDto;
import org.me.springboot.web.dto.testApi.TestApiResponseDto;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TestApiController {
    /** Api의 요청과 응답을 테스트 할 수 있는 컨트롤러입니다.
     *
     * apiTakeAndGive() : 해당 주소로 값을 담아 요청을 하게 되면 값을 연산 후 되돌려 줍니다.
     * 크로스 오리진을 통해 모든 주소에서 요청을 허용합니다.
     */
    private final TestService testService;

    @CrossOrigin("*")
    @RequestMapping("/test/api/main/number")
    public TestApiResponseDto apiTakeAndGive(@RequestBody TestApiRequestDto requestDto) {
        return testService.fourRuleCalculations(requestDto);
    }
}
