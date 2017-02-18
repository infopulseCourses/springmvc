package com.courses.mvc.repository;

import com.courses.mvc.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stepan
 */
@Repository
@Qualifier("userRepository")
public interface UserRepository extends JpaRepository<User,Long> {

}