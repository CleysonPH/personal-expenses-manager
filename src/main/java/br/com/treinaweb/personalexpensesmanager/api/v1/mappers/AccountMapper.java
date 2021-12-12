package br.com.treinaweb.personalexpensesmanager.api.v1.mappers;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;
import br.com.treinaweb.personalexpensesmanager.core.models.Account;

public interface AccountMapper {

    Account toModel(AccountRequest request);

    AccountResponse toResponse(Account model);

}
