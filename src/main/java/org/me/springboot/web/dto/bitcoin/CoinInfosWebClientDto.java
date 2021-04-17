package org.me.springboot.web.dto.bitcoin;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class CoinInfosWebClientDto {
    private String status;
    private List<Map<String, String>> data;

    @Builder
    public CoinInfosWebClientDto(String status, List<Map<String, String>> data) {
        this.status = status;
        this.data = data;
    }

    public String getPrice(){
        int lastIdx = data.size()-1;
        return data.get(lastIdx).get("price");
    }
}
