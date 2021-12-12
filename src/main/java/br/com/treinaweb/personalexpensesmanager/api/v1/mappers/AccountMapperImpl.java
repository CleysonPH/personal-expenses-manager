package br.com.treinaweb.personalexpensesmanager.api.v1.mappers;

import org.springframework.stereotype.Component;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;
import br.com.treinaweb.personalexpensesmanager.core.models.Account;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toModel(AccountRequest request) {
        return Account.builder()
            .name(request.getName())
            .description(request.getDescription())
            .build();
    }

    @Override
    public AccountResponse toResponse(Account model) {
        return AccountResponse.builder()
            .id(model.getId())
            .name(model.getName())
            .description(model.getDescription())
            .build();
    }

}
