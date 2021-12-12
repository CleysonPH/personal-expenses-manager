package br.com.treinaweb.personalexpensesmanager.core.exceptions;

import javax.persistence.EntityNotFoundException;

public class AccountNotFoundException extends EntityNotFoundException {

    public AccountNotFoundException() {
        super("Account not found");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

}
