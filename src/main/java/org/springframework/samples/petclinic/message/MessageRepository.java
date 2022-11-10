package org.springframework.samples.petclinic.message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE m.sender.id = :id OR m.receiver.id = :id")
    List<Message> findBySenderOrReceiver(Integer id);

    @Query("SELECT m FROM Message m WHERE (m.sender.username = :sender AND m.receiver.username = :receiver) OR (m.sender.username = :receiver AND m.receiver.username = :sender)")
    List<Message> findBySenderWithReceiver(String sender, String receiver);

    Optional<Message> findById(Integer id);

    List<Message> findAll();
}
