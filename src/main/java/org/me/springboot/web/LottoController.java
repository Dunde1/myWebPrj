package org.me.springboot.web;

import org.me.springboot.config.auth.LoginUser;
import org.me.springboot.config.auth.dto.SessionUser;
import org.me.springboot.web.dto.lotto.LottoListRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LottoController {

    @PostMapping("/function/lottoList")
    public String lottoListPop(@RequestBody List<LottoListRequestDto> requestDtos, Model model, @LoginUser SessionUser user){

        model.addAttribute("lottoList", requestDtos);
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "lotto/lottoMain";
    }
}
