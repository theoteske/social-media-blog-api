package com.example.service;

import com.example.entity.Message;
import com.example.exception.InvalidMessageTextException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * The creation of the message will be successful if and only if:
     *      *  - the messageText is not blank, is not over 255 characters
     *      *  - and postedBy refers to a real, existing user.
     * @return the persisted Message object
     */
    public Message createMessage(Message message) {
        if (message.getMessageText().isEmpty() || message.getMessageText().length() > 255)
            throw new InvalidMessageTextException("Message text must not be blank and cannot have more than 255 characters.");

        if (accountRepository.findById(message.getPostedBy()).isEmpty())
            throw new ResourceNotFoundException("Account ID " + message.getPostedBy() + " was not found. Please check account ID and try again.");

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

}
