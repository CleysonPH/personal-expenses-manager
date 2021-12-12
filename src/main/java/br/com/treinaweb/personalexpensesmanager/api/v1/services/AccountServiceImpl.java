package br.com.treinaweb.personalexpensesmanager.api.v1.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;
import br.com.treinaweb.personalexpensesmanager.api.v1.mappers.AccountMapper;
import br.com.treinaweb.personalexpensesmanager.core.exceptions.AccountNotFoundException;
import br.com.treinaweb.personalexpensesmanager.core.models.Account;
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

    @Override
    public AccountResponse findById(Long accountId) {
        var foundAccount = findAccountById(accountId);
        return accountMapper.toResponse(foundAccount);
    }

    @Override
    public void deleteById(Long accountId) {
        var foundAccount = findAccountById(accountId);
        accountRepository.delete(foundAccount);
    }

    @Override
    public AccountResponse updateById(AccountRequest request, Long accountId) {
        var accountToUpdate = findAccountById(accountId);
        BeanUtils.copyProperties(request, accountToUpdate);
        var updatedAccount = accountRepository.save(accountToUpdate);
        return accountMapper.toResponse(updatedAccount);
    }

    private Account findAccountById(Long accountId) {
        var message = String.format("Account with id %d not found", accountId);
        return accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(message));
    }

}
