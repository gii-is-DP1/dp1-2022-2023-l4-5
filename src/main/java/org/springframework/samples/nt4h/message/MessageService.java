package org.springframework.samples.nt4h.message;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.game.Game;
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
    public List<Message> getMessageBySenderWithReceiver(String usernameSender, String usernameReceiver) {
        return messageRepository.findBySenderWithReceiver(usernameSender, usernameReceiver);
    }

    @Transactional
    public void deleteMessage(Message message) {
        message.onDeleteSetNull();
        messageRepository.save(message);
        messageRepository.delete(message);
    }

    @Transactional
    public void deleteMessageById(int id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new NotFoundException("Message not found"));
        deleteMessage(message);
    }

    @Transactional(readOnly = true)
    public boolean messageExists(int id) {
        return messageRepository.existsById(id);
    }

    public Object getGameMessages(Game game) {
        return messageRepository.findByGame(game);
    }
}
