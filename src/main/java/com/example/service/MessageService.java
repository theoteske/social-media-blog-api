package com.example.service;

import com.example.entity.Message;
import com.example.exception.InvalidMessageTextException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }


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

    public Message getMessageById(Integer messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        return message.orElse(null);
    }

    public Integer deleteMessageById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return null;
    }

    
    public Integer updateMessageById(Integer messageId, String messageText) {
        if (messageText.isEmpty() || messageText.length() > 255)
            throw new InvalidMessageTextException("Message text must not be blank and cannot have more than 255 characters.");

        if (!messageRepository.existsById(messageId))
            throw new ResourceNotFoundException("Message ID " + messageId + " was not found. Please check message ID and try again.");

        Message updatedMessage = messageRepository.findById(messageId).get();
        updatedMessage.setMessageText(messageText);
        messageRepository.save(updatedMessage);

        return 1;
    }

    public List<Message> getAllMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

}
