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

    /**
     * Submits a Message if and only if the messageText is not blank, is not over 255 characters, and postedBy refers
     * to a real, existing Account. If these conditions are met, the Message is persisted to the database.
     *
     * @param message a Message object without a messageId
     * @return the persisted Message object, including its messageId
     * @throws InvalidMessageTextException if messageText is blank or has more than 255 characters
     * @throws ResourceNotFoundException if postedBy does not refer to an existing Account
     */
    public Message createMessage(Message message) {
        if (message.getMessageText().isEmpty() || message.getMessageText().length() > 255)
            throw new InvalidMessageTextException("Message text must not be blank and cannot have more than 255 characters.");

        if (accountRepository.findById(message.getPostedBy()).isEmpty())
            throw new ResourceNotFoundException("Account ID " + message.getPostedBy() + " was not found. Please check account ID and try again.");

        return messageRepository.save(message);
    }

    /**
     * Returns a List containing all Messages from the database. The list is simply empty if there are no Messages.
     *
     * @return a List<Message> object containing all Messages in the database
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Returns the Message object identified by the input messageId. If there is no such Message, simply returns null.
     *
     * @param messageId an Integer denoting a potential messageId of a persisted Message
     * @return the Message identified by the messageId or null
     */
    public Message getMessageById(Integer messageId) {
        Optional<Message> message = messageRepository.findById(messageId);
        return message.orElse(null);
    }

    /**
     * Removes an existing Message object identified by the input messageId from the database and returns the number of
     * rows updated (1). If there is no such Message, simply returns null.
     *
     * @param messageId an Integer denoting a potential messageId of a persisted Message
     * @return an Integer denoting the number of rows updated (1) or null
     */
    public Integer deleteMessageById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return null;
    }

    /**
     * Updates a Message existing on the database so it has the updated messageText. The update should be successful
     * if and only if the messageId already exists and the new messageText is not blank and is not over 255 characters.
     * If the update is successful, returns the number of rows updated (1).
     *
     * @param messageId an Integer denoting a potential messageId of a persisted Message
     * @param messageText a String that is not blank and is not over 255 characters
     * @return an Integer denoting the number of rows updated (1)
     * @throws InvalidMessageTextException if messageText is blank or is over 255 characters
     * @throws ResourceNotFoundException if messageId does not refer to an existing Message
     */
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

    /**
     * Returns a List containing all Messages posted by a particular user retrieved from the database. The list is
     * simply empty if there are no such Messages.
     *
     * @param accountId an Integer denoting a potential accountId of a persisted Account
     * @return a List<Message> object containing all messages posted by the user identified by the accountId in the
     * database
     */
    public List<Message> getAllMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

}
