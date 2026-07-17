package com.subhash.ims.repository;

import com.subhash.ims.entity.Role;
import com.subhash.ims.entity.enums.RoleType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository {

    Optional<Role> findByName(RoleType name);

    boolean existsByName(RoleType name);
}
