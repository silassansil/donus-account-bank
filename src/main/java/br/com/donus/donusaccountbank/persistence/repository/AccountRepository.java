package br.com.donus.donusaccountbank.persistence.repository;

import br.com.donus.donusaccountbank.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, String> {

    Optional<AccountEntity> findAccountEntityByCustomerCpf(final String cpf);
}
