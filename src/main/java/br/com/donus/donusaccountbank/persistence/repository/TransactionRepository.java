package br.com.donus.donusaccountbank.persistence.repository;

import br.com.donus.donusaccountbank.domain.tiny.BalanceProjection;
import br.com.donus.donusaccountbank.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    @Query("select " +
            "new br.com.donus.donusaccountbank.domain.tiny.BalanceProjection(coalesce(sum(t.amount), 0)) " +
            " from TransactionEntity t" +
            " inner join t.owner o" +
            " where o.cpf = :cpfOwner")
    BalanceProjection calculateCurrentBalance(@Param("cpfOwner") final String cpfOwner);
}
