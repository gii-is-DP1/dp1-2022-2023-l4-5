package org.springframework.samples.nt4h.message;

import lombok.AllArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public void saveMessage(Message message) {
        message.setTime(LocalDateTime.now());
        messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
    }

    @Transactional(readOnly = true)
    public List<Message> getMessageBySenderWithReceiver(String usernameSender, String usernameReceiver) {
        return messageRepository.findBySenderWithReceiver(usernameSender, usernameReceiver);
    }

    @Transactional(readOnly = true)
    public List<Message> getAllMessages(Integer id) {
        return messageRepository.findAll();
    }

    @Transactional
    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }

    @Transactional
    public void deleteMessageById(int id) {
        messageRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean messageExists(int id) {
        return messageRepository.existsById(id);
    }
}
