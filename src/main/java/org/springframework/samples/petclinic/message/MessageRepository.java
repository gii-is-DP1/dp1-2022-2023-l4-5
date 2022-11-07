package org.springframework.samples.petclinic.message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE m.sender.id = :id OR m.receiver.id = :id")
    List<Message> findBySenderOrReceiver(Integer id);

    @Query("SELECT m FROM Message m WHERE (m.sender.username = :sender AND m.receiver.username = :receiver) OR (m.sender.username = :receiver AND m.receiver.username = :sender)")
    List<Message> getBySenderWithReceiver(String sender, String receiver);
}
