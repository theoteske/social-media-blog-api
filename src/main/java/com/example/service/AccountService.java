package com.example.service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidUsernameOrPasswordException;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    
    public Account register(Account account) {

        if (account.getUsername().isEmpty() || account.getPassword().length() < 4)
            throw new InvalidUsernameOrPasswordException("Username or password is invalid. Username must not be empty and password must be at least 4 characters.");

        if (accountRepository.findByUsername(account.getUsername()).isPresent())
            throw new DuplicateUsernameException(account.getUsername() + " is already taken. Please choose a different username.");

        return accountRepository.save(account);
    }

    
    public Account login(Account account) throws AuthenticationException {
        Optional<Account> authenticatedAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (authenticatedAccount.isEmpty())
            throw new AuthenticationException("Credentials could not be authenticated. Check username and password and try again.");

        return authenticatedAccount.get();
    }
}
