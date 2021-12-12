package br.com.treinaweb.personalexpensesmanager.api.v1.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;
import br.com.treinaweb.personalexpensesmanager.api.v1.mappers.AccountMapper;
import br.com.treinaweb.personalexpensesmanager.core.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountResponse create(AccountRequest request) {
        var accountToCreate = accountMapper.toModel(request);
        var createdAccount = accountRepository.save(accountToCreate);
        return accountMapper.toResponse(createdAccount);
    }

    @Override
    public List<AccountResponse> findAll() {
        return accountRepository.findAll()
            .stream()
            .map(accountMapper::toResponse)
            .toList();
    }

}
