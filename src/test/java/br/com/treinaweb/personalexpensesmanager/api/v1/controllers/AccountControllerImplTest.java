package br.com.treinaweb.personalexpensesmanager.api.v1.controllers;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;
import br.com.treinaweb.personalexpensesmanager.api.v1.routes.AccountRoutes;
import br.com.treinaweb.personalexpensesmanager.api.v1.services.AccountService;
import br.com.treinaweb.personalexpensesmanager.core.exceptions.AccountNotFoundException;

@WebMvcTest(AccountControllerImpl.class)
class AccountControllerImplTest {

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenPOSTCreateWithValidDataThenShouldItReturnedWithStatusCode201() throws Exception {
        var request = AccountRequest.builder()
            .name("Nubank")
            .description("Account of bank Nubank")
            .build();
        var expectedResponse = AccountResponse.builder()
            .id(1L)
            .name("Nubank")
            .description("Account of bank Nubank")
            .build();
        var requestJson = objectMapper.writeValueAsString(request);

        when(accountService.create(request)).thenReturn(expectedResponse);

        mockMvc.perform(post(AccountRoutes.CREATE_URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(expectedResponse.getId().intValue())))
            .andExpect(jsonPath("$.name", is(expectedResponse.getName())))
            .andExpect(jsonPath("$.description", is(expectedResponse.getDescription())));
    }

    @Test
    void whenGETFindAllThenShouldReturnAllAccountsWithStatusCode200() throws Exception {
        var accountResponse = AccountResponse.builder()
            .id(1L)
            .name("Nubank")
            .description("Nubank account")
            .build();
        var accountsResponseToReturn = List.of(accountResponse);

        when(accountService.findAll()).thenReturn(accountsResponseToReturn);

        mockMvc.perform(get(AccountRoutes.FIND_ALL_URI))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(accountsResponseToReturn.size())))
            .andExpect(jsonPath("$.[0].id", is(accountsResponseToReturn.get(0).getId().intValue())))
            .andExpect(jsonPath("$.[0].name", is(accountsResponseToReturn.get(0).getName())))
            .andExpect(jsonPath("$.[0].description", is(accountsResponseToReturn.get(0).getDescription())));
    }

    @Test
    void whenGETFindByIdWithInvalidIdThenStatusCode404ShouldBeReturned() throws Exception {
        var accountId = 1L;
        var expectedMessage = "Account not found";

        when(accountService.findById(accountId)).thenThrow(AccountNotFoundException.class);

        mockMvc.perform(get(AccountRoutes.FIND_BY_ID_URI, accountId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is(expectedMessage)));
    }

    @Test
    void whenGETFindByIdWithValidIdThenReturnAccountResponseWithStatusCode200() throws Exception {
        var accountId = 1L;
        var expectedAccountResponse = AccountResponse.builder()
            .id(accountId)
            .name("Nubank")
            .description("Nubank account")
            .build();

        when(accountService.findById(accountId)).thenReturn(expectedAccountResponse);

        mockMvc.perform(get(AccountRoutes.FIND_BY_ID_URI, accountId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(expectedAccountResponse.getId().intValue())))
            .andExpect(jsonPath("$.name", is(expectedAccountResponse.getName())))
            .andExpect(jsonPath("$.description", is(expectedAccountResponse.getDescription())));
    }

}
