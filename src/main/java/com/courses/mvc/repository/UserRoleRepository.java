package com.courses.mvc.repository;

import com.courses.mvc.domain.UserRole;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stepan
 */
@Repository
@Qualifier(value = "userRoleRepository")
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {


}
