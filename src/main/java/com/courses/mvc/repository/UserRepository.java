package com.courses.mvc.repository;

import com.courses.mvc.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Stepan
 */
@Repository
@Qualifier("userRepository")
public interface UserRepository extends JpaRepository<User,Long> {

   @Query(value = "select u from com.courses.mvc.domain.User u where u.login =:login")
   User findUserByLogin(@Param("login") String login);

   @Query(value = "select u from com.courses.mvc.domain.User u where u.role.role = com.courses.mvc.domain.Role.USER")
   List<User> getAllUsersExceptAdmins();
}