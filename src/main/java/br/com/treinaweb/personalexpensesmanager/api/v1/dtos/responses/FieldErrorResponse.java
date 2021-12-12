package br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldErrorResponse {

    private String field;
    private String error;

}
