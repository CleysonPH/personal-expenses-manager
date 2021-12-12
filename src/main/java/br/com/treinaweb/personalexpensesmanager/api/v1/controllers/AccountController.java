package br.com.treinaweb.personalexpensesmanager.api.v1.controllers;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;

public interface AccountController {

    AccountResponse create(AccountRequest request);

}
