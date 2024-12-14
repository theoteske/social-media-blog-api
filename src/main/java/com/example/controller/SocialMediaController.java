package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
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

    
    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account registeredAccount = accountService.register(account);
        return ResponseEntity.ok()
                .body(registeredAccount);
    }

    
    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException {
        Account authenticatedAccount = accountService.login(account);
        return ResponseEntity.ok()
                .body(authenticatedAccount);
    }

    
    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message postedMessage = messageService.createMessage(message);
        return ResponseEntity.ok()
                .body(postedMessage);
    }

    
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok()
                .body(messages);
    }

    
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok()
                .body(message);
    }

    
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        Integer rowsUpdated = messageService.deleteMessageById(messageId);
        return ResponseEntity.ok()
                .body(rowsUpdated);
    }

    
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody Message message) {
        Integer rowsUpdated = messageService.updateMessageById(messageId, message.getMessageText());
        return ResponseEntity.ok()
                .body(rowsUpdated);
    }

    
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getAllMessagesByAccountId(accountId);

        return ResponseEntity.ok()
                .body(messages);
    }

}
