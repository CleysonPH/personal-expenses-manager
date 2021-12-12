package br.com.treinaweb.personalexpensesmanager.api.v1.services;

import java.util.List;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;

public interface AccountService {

    AccountResponse create(AccountRequest request);

    List<AccountResponse> findAll();

    AccountResponse findById(Long accountId);

    void deleteById(Long accountId);

    AccountResponse updateById(AccountRequest request, Long accountId);

}
