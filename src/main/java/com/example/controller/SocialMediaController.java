package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidUsernameOrPasswordException;
import com.example.exception.InvalidMessageTextException;
import com.example.exception.ResourceNotFoundException;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.List;

@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Handles a POST request to create a new Account on the endpoint POST localhost:8080/register.
     *
     * @param account an Account object without an accountId
     * @return a ResponseEntity object with response status 200 OK and response body containing the persisted Account
     * object, including its accountId
     * @throws InvalidUsernameOrPasswordException if username is blank or password is less than 4 characters long
     * @throws DuplicateUsernameException if an Account with the given username already exists
     */
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account registeredAccount = accountService.register(account);
        return ResponseEntity.ok()
                .body(registeredAccount);
    }

    /**
     * Handles a POST request to verify a login on the endpoint POST localhost:8080/login.
     *
     * @param account an Account object without an accountId
     * @return a ResponseEntity object with response status 200 OK and response body containing the persisted Account
     * object, including its accountId
     * @throws AuthenticationException if username and password do not match an existing Account
     */
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException {
        Account authenticatedAccount = accountService.login(account);
        return ResponseEntity.ok()
                .body(authenticatedAccount);
    }

    /**
     * Handles a POST request to process the creation of a new message on the endpoint POST localhost:8080/messages.
     *
     * @param message a Message object without a messageId
     * @return a ResponseEntity object with response status 200 OK and response body containing the persisted Message
     * object, including its messageId
     * @throws InvalidMessageTextException if messageText is blank or has more than 255 characters
     * @throws ResourceNotFoundException if postedBy does not refer to an existing Account
     */
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message postedMessage = messageService.createMessage(message);
        return ResponseEntity.ok()
                .body(postedMessage);
    }

    /**
     * Handles a GET request to retrieve all messages on the endpoint GET localhost:8080/messages.
     *
     * @return a ResponseEntity object with response status 200 OK and response body containing a List<Message> object
     * with all Messages in the database
     */
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok()
                .body(messages);
    }

    /**
     * Handles a GET request to retrieve a message by its ID on the endpoint GET localhost:8080/messages/{messageId}.
     *
     * @param messageId an Integer denoting a potential messageId of a persisted Message
     * @return a ResponseEntity object with response status 200 OK and response body containing the persisted Message
     * object identified by the input messageId, or an empty response body if there is no such Message
     */
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok()
                .body(message);
    }

    /**
     * Handles a DELETE request to delete a message identified by a message ID on the endpoint DELETE
     * localhost:8080/messages/{messageId}.
     *
     * @param messageId an Integer denoting a potential messageId of a persisted Message
     * @return a ResponseEntity object with response status 200 OK and response body containing the number of rows
     * updated (1), or an empty response body if no rows were updated
     */
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        Integer rowsUpdated = messageService.deleteMessageById(messageId);
        return ResponseEntity.ok()
                .body(rowsUpdated);
    }

    /**
     * Handles a PATCH request to update a message text identified by a message ID on the endpoint PATCH
     * localhost:8080/messages/{messageId}.
     *
     * @param messageId an Integer denoting a potential messageId of a persisted Message
     * @param message a Message object without a messageId
     * @return a ResponseEntity object with response status 200 OK and response body containing the number of rows
     * updated (1), or an empty response body if no rows were updated
     * @throws InvalidMessageTextException if messageText is blank or is over 255 characters
     * @throws ResourceNotFoundException if messageId does not refer to an existing Message
     */
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody Message message) {
        Integer rowsUpdated = messageService.updateMessageById(messageId, message.getMessageText());
        return ResponseEntity.ok()
                .body(rowsUpdated);
    }

    /**
     * Handles a GET request to retrieve all messages written by a particular user on the endpoint GET
     * localhost:8080/accounts/{accountId}/messages.
     *
     * @param accountId an Integer denoting a potential accountId of a persisted Account
     * @return a ResponseEntity object with response status 200 OK and response body containing a List<Message> object
     * with all Messages posted by the user identified by the accountId in the database
     */
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getAllMessagesByAccountId(accountId);

        return ResponseEntity.ok()
                .body(messages);
    }

}
