package br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest {

    private String name;

    private String description;

}
