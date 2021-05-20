package org.me.springboot.web;

import org.me.springboot.config.auth.LoginUser;
import org.me.springboot.config.auth.dto.SessionUser;
import org.me.springboot.web.dto.lotto.LottoListRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FunctionController {
    /** 추가기능이 있는 페이지로 이동시키는 컨트롤러 입니다.
     *
     * lottoListPop() : 해당 주소로 요청 시 로또추첨 페이지로 이동시킵니다.
     */
    @PostMapping("/function/lottoList")
    public String lottoListPop(@RequestBody List<LottoListRequestDto> requestDtos, Model model, @LoginUser SessionUser user){
        model.addAttribute("lottoList", requestDtos);
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "lotto/lottoMain";
    }
}
