package br.com.treinaweb.personalexpensesmanager.api.v1.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;
import br.com.treinaweb.personalexpensesmanager.core.models.Account;

class AccountMapperImplTest {

    private AccountMapper accountMapper;

    @BeforeEach
    void setUp() {
        this.accountMapper = new AccountMapperImpl();
    }

    @Test
    void whenGivenAnAccountRequestShouldReturnAnAccount() {
        var request = AccountRequest.builder()
            .name("Nubank")
            .description("Account of bank Nubank")
            .build();
        var expectedModel = Account.builder()
            .name("Nubank")
            .description("Account of bank Nubank")
            .build();

        var returnedModel = accountMapper.toModel(request);

        assertThat(returnedModel.getId(), is(equalTo(expectedModel.getId())));
        assertThat(returnedModel.getName(), is(equalTo(expectedModel.getName())));
        assertThat(returnedModel.getDescription(), is(equalTo(expectedModel.getDescription())));
    }

    @Test
    void whenGivenAnAccountShouldReturnAnAccountResponse() {
        var model = Account.builder()
            .id(1L)
            .name("Nubank")
            .description("Account of bank Nubank")
            .build();
        var expectedResponse = AccountResponse.builder()
            .id(1L)
            .name("Nubank")
            .description("Account of bank Nubank")
            .build();

        var returnedResponse = accountMapper.toResponse(model);

        assertThat(returnedResponse.getId(), is(equalTo(expectedResponse.getId())));
        assertThat(returnedResponse.getName(), is(equalTo(expectedResponse.getName())));
        assertThat(returnedResponse.getDescription(), is(equalTo(expectedResponse.getDescription())));
    }

}
