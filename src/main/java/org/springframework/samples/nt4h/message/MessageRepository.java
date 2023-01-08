package org.springframework.samples.nt4h.message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE (m.sender.username = :user1 AND m.receiver.username = :user2) OR (m.sender.username = :user2 AND m.receiver.username = :user1)")
    List<Message> findByPair(String user1, String user2);

    @Query("SELECT m FROM Message m WHERE (m.sender.username = :sender AND m.receiver.username = :receiver)")
    List<Message> findBySenderWithReceiver(String sender, String receiver);

    @Query("SELECT m FROM Message m WHERE m.game = :game")
    List<Message> findByGame(Game game);

    Optional<Message> findById(Integer id);

    List<Message> findAll();
}
