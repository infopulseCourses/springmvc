package com.courses.mvc.repository;

import com.courses.mvc.domain.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stepan
 */
@Repository
public interface BlackListRepository extends JpaRepository<BlackList,Long>, BlackListRepositoryCustom {

}
