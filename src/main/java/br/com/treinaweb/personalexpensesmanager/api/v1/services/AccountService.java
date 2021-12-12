package br.com.treinaweb.personalexpensesmanager.api.v1.services;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;

public interface AccountService {

    AccountResponse create(AccountRequest request);

}
