package br.com.treinaweb.personalexpensesmanager.api.v1.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;
import br.com.treinaweb.personalexpensesmanager.api.v1.mappers.AccountMapper;
import br.com.treinaweb.personalexpensesmanager.core.exceptions.AccountNotFoundException;
import br.com.treinaweb.personalexpensesmanager.core.models.Account;
import br.com.treinaweb.personalexpensesmanager.core.repositories.AccountRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        this.accountService = new AccountServiceImpl(accountRepository, accountMapper);
    }

    @Test
    void whenAccountRequestIsGivenThenItShouldBeCreated() {
        var request = AccountRequest.builder()
            .name("Nubank")
            .description("Account of bank Nubank")
            .build();
        var accountToCreate = Account.builder()
            .name("Nubank")
            .description("Account of bank Nubank")
            .build();
        var createdAccount = Account.builder()
            .id(1L)
            .name("Nubank")
            .description("Account of bank Nubank")
            .build();
        var expectedResponse = AccountResponse.builder()
            .id(1L)
            .name("Nubank")
            .description("Account of bank Nubank")
            .build();

        when(accountMapper.toModel(request)).thenReturn(accountToCreate);
        when(accountRepository.save(accountToCreate)).thenReturn(createdAccount);
        when(accountMapper.toResponse(createdAccount)).thenReturn(expectedResponse);

        var returnedResponse = accountService.create(request);

        assertThat(returnedResponse.getId(), is(equalTo(expectedResponse.getId())));
        assertThat(returnedResponse.getName(), is(equalTo(expectedResponse.getName())));
        assertThat(returnedResponse.getDescription(), is(equalTo(expectedResponse.getDescription())));
    }

    @Test
    void whenFindAllIsCalledThenShouldReturnAllAccounts() {
        var account = Account.builder()
            .id(1L)
            .name("Nubank")
            .description("Nubank account")
            .build();
        var accountResponse = AccountResponse.builder()
            .id(1L)
            .name("Nubank")
            .description("Nubank account")
            .build();
        var accounts = List.of(account);
        var expectedAccountsResponse = List.of(accountResponse);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(accountMapper.toResponse(account)).thenReturn(accountResponse);

        var returnedAccountsResponse = accountService.findAll();

        assertThat(returnedAccountsResponse.size(), is(equalTo(expectedAccountsResponse.size())));
        assertThat(returnedAccountsResponse.get(0), is(equalTo(expectedAccountsResponse.get(0))));
    }

    @Test
    void whenFindByIdIsCalledWithInvalidIdThenAccountNotFoundExceptionShouldBeThrow() {
        var accountId = 1L;
        var expectedMessage = "Account with id 1 not found";

        when(accountRepository.findById(accountId)).thenReturn(Optional.<Account>empty());

        var exception = assertThrows(AccountNotFoundException.class, () -> accountService.findById(accountId));
        assertThat(exception.getMessage(), is(equalTo(expectedMessage)));
    }

    @Test
    void whenFindByIdIsCalledWithValidIdThenAccountResponseShoulBeReturned() {
        var accountId = 1L;
        var account = Account.builder()
            .id(accountId)
            .name("Nubank")
            .description("Nubank account")
            .build();
        var expectedAccountsResponse = AccountResponse.builder()
            .id(accountId)
            .name("Nubank")
            .description("Nubank account")
            .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.toResponse(account)).thenReturn(expectedAccountsResponse);

        var returnedAccountResponse = accountService.findById(accountId);

        assertThat(returnedAccountResponse.getId(), is(equalTo(expectedAccountsResponse.getId())));
        assertThat(returnedAccountResponse.getName(), is(equalTo(expectedAccountsResponse.getName())));
        assertThat(returnedAccountResponse.getDescription(), is(equalTo(expectedAccountsResponse.getDescription())));
    }

    @Test
    void whenDeleteByIdIsCalledWithInvalidIdThenAccountNotFoundExceptionShouldBeThrow() {
        var accountId = 1L;
        var expectedMessage = "Account with id 1 not found";

        when(accountRepository.findById(accountId)).thenReturn(Optional.<Account>empty());

        var exception = assertThrows(AccountNotFoundException.class, () -> accountService.deleteById(accountId));
        assertThat(exception.getMessage(), is(equalTo(expectedMessage)));
    }

    @Test
    void whenDeleteByIdIsCalledWithValidIdThenAccountRepositoryDeleteMethodShouldBeCalled() {
        var accountId = 1L;
        var account = Account.builder()
            .id(accountId)
            .name("Nubank")
            .description("Nubank account")
            .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        accountService.deleteById(accountId);

        verify(accountRepository, times(1)).delete(account);
    }

}
