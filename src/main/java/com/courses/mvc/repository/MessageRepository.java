package com.courses.mvc.repository;

import com.courses.mvc.domain.Message;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stepan
 */
@Repository
@Qualifier("messageRepository")
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("select m from com.courses.mvc.domain.Message m join m.receiver r where r.login = :login")
    List<Message> getAllMessagesByReceiverLogin(@Param("login")String login);

    @Modifying
    @Query("delete from com.courses.mvc.domain.Message m where m.receiver.login = :receiver")
    void deleteByReceiverLogin(@Param("receiver")String receiver);
}
