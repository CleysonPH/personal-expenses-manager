package br.com.treinaweb.personalexpensesmanager.api.v1.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.requests.AccountRequest;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.AccountResponse;
import br.com.treinaweb.personalexpensesmanager.api.v1.dtos.responses.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Accounts", description = "Account Controller")
public interface AccountController {

    @ApiOperation("Cadastrar nova conta")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Conta foi criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Houveram erros de validação", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    AccountResponse create(AccountRequest request);

    @ApiOperation("Lista todas as contas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso")
    })
    List<AccountResponse> findAll();

    @ApiOperation("Busca conta por id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Conta encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    AccountResponse findById(Long accountId);

    @ApiOperation("Excluir conta por id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Conta encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> deleteById(Long accountId);

    AccountResponse updateById(AccountRequest request, Long accountId);

}
