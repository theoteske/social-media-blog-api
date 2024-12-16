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

    /**
     * Registers an Account if and only if the username is not blank, the password is at least 4 character long, and an
     * Account with that username does not already exist. If all these conditions are met, the Account is persisted to
     * the database.
     *
     * @param account an Account object without an accountId
     * @return the persisted Account object, including its accountId
     * @throws InvalidUsernameOrPasswordException if username is blank or password is less than 4 characters long
     * @throws DuplicateUsernameException if an Account with the given username already exists
     */
    public Account register(Account account) {

        if (account.getUsername().isEmpty() || account.getPassword().length() < 4)
            throw new InvalidUsernameOrPasswordException("Username or password is invalid. Username must not be empty and password must be at least 4 characters.");

        if (accountRepository.findByUsername(account.getUsername()).isPresent())
            throw new DuplicateUsernameException(account.getUsername() + " is already taken. Please choose a different username.");

        return accountRepository.save(account);
    }

    /**
     * Verifies that the username and password provided match a real Account existing on the database.
     *
     * @param account an Account object without an accountId
     * @return the persisted Account object, including its accountId
     * @throws AuthenticationException if username and password do not match an existing Account
     */
    public Account login(Account account) throws AuthenticationException {
        Optional<Account> authenticatedAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (authenticatedAccount.isEmpty())
            throw new AuthenticationException("Credentials could not be authenticated. Check username and password and try again.");

        return authenticatedAccount.get();
    }

}
