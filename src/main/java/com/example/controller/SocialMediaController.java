package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Create a new Account on the endpoint POST localhost:8080/register.
     * The body will contain a representation of a JSON Account, but will not contain an accountId.
     *
     */
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account registeredAccount = accountService.register(account);
        return ResponseEntity.ok()
                .body(registeredAccount);
    }

    /**
     * As a user, I should be able to verify my login on the endpoint POST localhost:8080/login.
     * The request body will contain a JSON representation of an Account.
     *
     * The login will be successful if and only if:
     * - the username and password provided in the request body JSON match a real account existing on the database.
     *
     * If successful, the response body should contain a JSON of the account in the response body, including its accountId.
     * The response status should be 200 OK, which is the default.
     * If the login is not successful, the response status should be 401. (Unauthorized)
     */
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException {
        Account authenticatedAccount = accountService.login(account);
        return ResponseEntity.ok()
                .body(authenticatedAccount);
    }

}
