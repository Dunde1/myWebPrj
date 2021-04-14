package org.me.springboot.web;

import org.me.springboot.config.auth.LoginUser;
import org.me.springboot.config.auth.dto.SessionUser;
import org.me.springboot.web.dto.lotto.LottoListRequestDto;
import org.me.springboot.web.dto.lotto.LottoTestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/function/lottoTest")
    public @ResponseBody List<LottoListRequestDto> lottoListPop(@RequestBody LottoTestDto testDto){
        List<LottoListRequestDto> requestDtos = new ArrayList<>();
        requestDtos.add(LottoListRequestDto.builder().lnum1("1").lnum2("1").lnum3("1").lnum4("1").lnum5("1").lnum6("1").build());
        requestDtos.add(LottoListRequestDto.builder().lnum1("2").lnum2("32").lnum3("12").lnum4("22").lnum5("40").lnum6("4").build());
        return requestDtos;
    }
}
