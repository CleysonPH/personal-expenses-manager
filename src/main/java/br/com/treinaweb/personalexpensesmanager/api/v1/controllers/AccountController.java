package br.com.treinaweb.personalexpensesmanager.api.v1.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;

public interface AccountController {

    AccountResponse create(AccountRequest request);

    List<AccountResponse> findAll();

    AccountResponse findById(Long accountId);

    ResponseEntity<Void> deleteById(Long accountId);

}
