package br.com.fullcycle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInputDto {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Payload")
    private Payload payload;

}
