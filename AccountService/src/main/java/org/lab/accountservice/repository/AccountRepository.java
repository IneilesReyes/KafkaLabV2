package org.lab.accountservice.repository;

import org.lab.accountservice.entities.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
