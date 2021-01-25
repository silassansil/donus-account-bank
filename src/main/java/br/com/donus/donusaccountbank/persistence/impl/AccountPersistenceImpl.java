package br.com.donus.donusaccountbank.persistence.impl;

import br.com.donus.donusaccountbank.domain.Account;
import br.com.donus.donusaccountbank.domain.data.LRUCache;
import br.com.donus.donusaccountbank.exception.UserAlreadyExistsException;
import br.com.donus.donusaccountbank.exception.UserNotFoundException;
import br.com.donus.donusaccountbank.persistence.AccountPersistence;
import br.com.donus.donusaccountbank.persistence.entity.AccountEntity;
import br.com.donus.donusaccountbank.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountPersistenceImpl implements AccountPersistence {

    private final AccountRepository accountRepository;
    private final LRUCache<String, AccountEntity> cache = new LRUCache<>(10);

    @Override
    public Account persistNewAccount(Account account) {
        try {
            final AccountEntity toSave = AccountEntity.fromDomain(account);
            this.cache.put(toSave.getCustomer().getCpf(), toSave);
            return this.accountRepository.save(toSave)
                    .toDomain();
        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public Account findAccountEntityByCustomerCpf(final String cpfOwner) {
        final AccountEntity fromCache = this.cache.get(cpfOwner);
        if (fromCache != null) return fromCache.toDomain();
        return this.accountRepository.findAccountEntityByCustomerCpf(cpfOwner)
                .map(AccountEntity::toDomain)
                .orElseThrow(UserNotFoundException::new);
    }
}
