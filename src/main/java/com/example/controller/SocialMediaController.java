package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import javax.security.sasl.AuthenticationException;
import java.util.List;


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

    /**
     * As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages.
     * The request body will contain a JSON representation of a message, which should be persisted to the database, but will not contain a messageId.
     *
     * The creation of the message will be successful if and only if:
     *  - the messageText is not blank, is not over 255 characters
     *  - and postedBy refers to a real, existing user.
     *
     * If successful, the response body should contain a JSON of the message, including its messageId.
     * The response status should be 200, which is the default. The new message should be persisted to the database.
     *
     * If the creation of the message is not successful, the response status should be 400. (Client error)
     */
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message postedMessage = messageService.createMessage(message);
        return ResponseEntity.ok()
                .body(postedMessage);
    }

    /**
     * As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages.
     *
     * The response body should contain a JSON representation of a list containing all messages retrieved from the database.
     * It is expected for the list to simply be empty if there are no messages.
     * The response status should always be 200, which is the default.
     */
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok()
                .body(messages);
    }

}
