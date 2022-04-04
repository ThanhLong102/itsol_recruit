package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Role;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.repoext.UserRepositoryExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryExt {

    Optional<User> findByUserName(String userName);

    Optional<User> findOneByEmail(String username);

    User findOneById(Long contactId);

    Optional<User> findByPhoneNumber(String phoneNumber);

}