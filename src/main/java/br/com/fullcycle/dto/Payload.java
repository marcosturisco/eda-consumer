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
public class Payload {

    @JsonProperty("id")
    private String id;
    @JsonProperty("account_id_from")
    private String accountIdFrom;
    @JsonProperty("account_id_to")
    private String accountIdTo;
    @JsonProperty("amount")
    private Integer amount;

}
