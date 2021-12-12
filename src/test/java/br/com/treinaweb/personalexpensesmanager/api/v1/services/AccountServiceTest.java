package br.com.treinaweb.personalexpensesmanager.api.v1.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;
import br.com.treinaweb.personalexpensesmanager.api.v1.mappers.AccountMapper;
import br.com.treinaweb.personalexpensesmanager.core.models.Account;
import br.com.treinaweb.personalexpensesmanager.core.repositories.AccountRepository;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

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

}
