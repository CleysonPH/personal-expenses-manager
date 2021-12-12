package br.com.treinaweb.personalexpensesmanager.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.personalexpensesmanager.core.models.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
