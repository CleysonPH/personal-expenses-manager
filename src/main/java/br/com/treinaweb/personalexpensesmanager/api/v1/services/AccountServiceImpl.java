package br.com.treinaweb.personalexpensesmanager.api.v1.services;

import org.springframework.stereotype.Service;

import br.com.treinaweb.personalexpensesmanager.core.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

}
