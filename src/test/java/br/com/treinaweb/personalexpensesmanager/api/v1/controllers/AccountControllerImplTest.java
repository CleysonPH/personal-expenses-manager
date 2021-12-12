package br.com.treinaweb.personalexpensesmanager.api.v1.controllers;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.web.util.UriComponentsBuilder;

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

        when(accountService.findById(accountId)).thenThrow(new AccountNotFoundException());

        mockMvc.perform(get(AccountRoutes.FIND_BY_ID_URI, accountId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status", is(404)))
            .andExpect(jsonPath("$.error", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Account not found")))
            .andExpect(jsonPath("$.path", is(UriComponentsBuilder.fromUriString(AccountRoutes.FIND_BY_ID_URI).build(accountId).toString())));
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

    @Test
    void whenDELETEDeleteByIdWithInvalidIdIsCalledThenStatusCode404ShouldBeReturned() throws Exception {
        var accountId = 1L;

        doThrow(new AccountNotFoundException()).when(accountService).deleteById(accountId);

        mockMvc.perform(delete(AccountRoutes.DELETE_BY_ID_URI, accountId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status", is(404)))
            .andExpect(jsonPath("$.error", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Account not found")))
            .andExpect(jsonPath("$.path", is(UriComponentsBuilder.fromUriString(AccountRoutes.DELETE_BY_ID_URI).build(accountId).toString())));
    }

    @Test
    void whenDELETEDeleteByIdWithValidIdIsCalledThenStatusCode204ShouldBeReturned() throws Exception {
        var accountId = 1L;

        doNothing().when(accountService).deleteById(accountId);

        mockMvc.perform(delete(AccountRoutes.DELETE_BY_ID_URI, accountId))
            .andExpect(status().isNoContent());
    }

    @Test
    void whenPUTUpdateByIdWithInvalidIdIsCalledThenStatusCode404ShouldBeReturned() throws Exception {
        var accountId = 1L;
        var accountRequest = AccountRequest.builder()
            .name("Nubank edited")
            .description("Nubank account edited")
            .build();
        var requestJson = objectMapper.writeValueAsString(accountRequest);

        when(accountService.updateById(accountRequest, accountId)).thenThrow(new AccountNotFoundException());

        mockMvc.perform(put(AccountRoutes.UPDATE_BY_ID_URI, accountId).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status", is(404)))
            .andExpect(jsonPath("$.error", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Account not found")))
            .andExpect(jsonPath("$.path", is(UriComponentsBuilder.fromUriString(AccountRoutes.UPDATE_BY_ID_URI).build(accountId).toString())));
    }

    @Test
    void whenPUTUpdateByIdWithValidIdAndValidDataThenAccountResponseShouldBeReturnedWithStatusCode200() throws Exception {
        var accountId = 1L;
        var accountRequest = AccountRequest.builder()
            .name("Nubank edited")
            .description("Nubank account edited")
            .build();
        var expectedAccountResponse = AccountResponse.builder()
            .id(accountId)
            .name("Nubank edited")
            .description("Nubank account edited")
            .build();
        var requestJson = objectMapper.writeValueAsString(accountRequest);

        when(accountService.updateById(accountRequest, accountId)).thenReturn(expectedAccountResponse);

        mockMvc.perform(put(AccountRoutes.UPDATE_BY_ID_URI, accountId).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(expectedAccountResponse.getId().intValue())))
            .andExpect(jsonPath("$.name", is(expectedAccountResponse.getName())))
            .andExpect(jsonPath("$.description", is(expectedAccountResponse.getDescription())));
    }

    @Test
    void whenPOSTCreateWithNameNullThendStatusCode400ShouldBeReturned() throws Exception {
        var accountRequest = AccountRequest.builder()
            .description("Nubank Account")
            .build();
        var requestJson = objectMapper.writeValueAsString(accountRequest);

        mockMvc.perform(post(AccountRoutes.CREATE_URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].field", is("name")))
            .andExpect(jsonPath("$.validationErrors[0].error", is("must not be null")));

    }

    @Test
    void whenPOSTCreateWithNameLengthLowerThan3ThendStatusCode400ShouldBeReturned() throws Exception {
        var accountRequest = AccountRequest.builder()
            .name("Nu")
            .description("Nubank account")
            .build();
        var requestJson = objectMapper.writeValueAsString(accountRequest);

        mockMvc.perform(post(AccountRoutes.CREATE_URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].field", is("name")))
            .andExpect(jsonPath("$.validationErrors[0].error", is("size must be between 3 and 100")));

    }

    @Test
    void whenPOSTCreateWithNameLengthGreaterThan100ThendStatusCode400ShouldBeReturned() throws Exception {
        var accountRequest = AccountRequest.builder()
            .name("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam imperdiet ac nisl a fermentum viverra.")
            .description("Nubank account")
            .build();
        var requestJson = objectMapper.writeValueAsString(accountRequest);

        mockMvc.perform(post(AccountRoutes.CREATE_URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].field", is("name")))
            .andExpect(jsonPath("$.validationErrors[0].error", is("size must be between 3 and 100")));

    }

    @Test
    void whenPOSTCreateWithDescriptionNullThendStatusCode400ShouldBeReturned() throws Exception {
        var accountRequest = AccountRequest.builder()
            .name("Nubank")
            .build();
        var requestJson = objectMapper.writeValueAsString(accountRequest);

        mockMvc.perform(post(AccountRoutes.CREATE_URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].field", is("description")))
            .andExpect(jsonPath("$.validationErrors[0].error", is("must not be null")));
    }

    @Test
    void whenPOSTCreateWithDescriptionLengthLowerThan3ThendStatusCode400ShouldBeReturned() throws Exception {
        var accountRequest = AccountRequest.builder()
            .name("Nubank")
            .description("Nu")
            .build();
        var requestJson = objectMapper.writeValueAsString(accountRequest);

        mockMvc.perform(post(AccountRoutes.CREATE_URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].field", is("description")))
            .andExpect(jsonPath("$.validationErrors[0].error", is("size must be between 3 and 255")));
    }

    @Test
    void whenPOSTCreateWithDescriptionLengthGreaterThan255ThendStatusCode400ShouldBeReturned() throws Exception {
        var accountRequest = AccountRequest.builder()
            .name("Nubank")
            .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam ut lobortis leo, a fringilla felis. Cras id nulla sed felis gravida maximus. Morbi mattis est arcu. Cras aliquam sapien at tristique imperdiet. Nulla eu egestas nunc, eget porta metus integer.")
            .build();
        var requestJson = objectMapper.writeValueAsString(accountRequest);

        mockMvc.perform(post(AccountRoutes.CREATE_URI).contentType(MediaType.APPLICATION_JSON).content(requestJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.validationErrors[0].field", is("description")))
            .andExpect(jsonPath("$.validationErrors[0].error", is("size must be between 3 and 255")));
    }

}
