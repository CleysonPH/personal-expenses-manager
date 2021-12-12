package br.com.treinaweb.personalexpensesmanager.api.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;
import br.com.treinaweb.personalexpensesmanager.api.v1.routes.AccountRoutes;
import br.com.treinaweb.personalexpensesmanager.api.v1.services.AccountService;

@RestController
public class AccountControllerImpl implements AccountController {

    private final AccountService accountService;

    public AccountControllerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @PostMapping(AccountRoutes.CREATE_URI)
    @ResponseStatus(code = HttpStatus.CREATED)
    public AccountResponse create(@RequestBody AccountRequest request) {
        return accountService.create(request);
    }

}
