package org.springframework.samples.nt4h.message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE (m.sender.username = :sender AND m.receiver.username = :receiver) OR (m.sender.username = :receiver AND m.receiver.username = :sender)")
    List<Message> findBySenderWithReceiver(String sender, String receiver);

    @Query("SELECT m FROM Message m WHERE m.game = :game")
    List<Message> findByGame(Game game);

    Optional<Message> findById(Integer id);

    List<Message> findAll();
}
