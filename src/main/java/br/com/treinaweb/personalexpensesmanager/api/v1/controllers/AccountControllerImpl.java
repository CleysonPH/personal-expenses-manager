package br.com.treinaweb.personalexpensesmanager.api.v1.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Override
    @GetMapping(AccountRoutes.FIND_ALL_URI)
    public List<AccountResponse> findAll() {
        return accountService.findAll();
    }

    @Override
    @GetMapping(AccountRoutes.FIND_BY_ID_URI)
    public AccountResponse findById(@PathVariable Long accountId) {
        return accountService.findById(accountId);
    }

    @Override
    @DeleteMapping(AccountRoutes.DELETE_BY_ID_URI)
    public ResponseEntity<Void> deleteById(@PathVariable Long accountId) {
        accountService.deleteById(accountId);
        return ResponseEntity.noContent().build();
    }

}
