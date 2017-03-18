package com.courses.mvc.repository;

import com.courses.mvc.domain.BlackList;
import com.courses.mvc.domain.User;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.exception.DAOException;
import com.courses.mvc.service.util.UserConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Stepan
 */
@Component
public class BlackListRepositoryImpl implements BlackListRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlackListRepository blackListRepository;

    @Override
    @Transactional
    public void addUserToBlackListByCustomMethod(UserDTO user) {
        BlackList blackList = new BlackList();
        User u = userRepository.findUserByLogin(user.getLogin());
        if (u == null) {
            throw new DAOException();
        }
        blackList.setUser(u);
        try {
            entityManager.persist(blackList);
        } catch (Exception e) {
            throw new DAOException();
        }
    }

    @Override
    @Transactional
    public void removeFromBlackListByCustomMethod(UserDTO user) {
        User u = userRepository.findUserByLogin(user.getLogin());
        BlackList blackList = u.getBlackList();
        if (blackList == null) {
            throw new DAOException();
        }
        System.out.println(blackList.getId());
        try {
            entityManager.createQuery("delete from com.courses.mvc.domain.BlackList b where b.id =:id")
                    .setParameter("id", blackList.getId()).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
