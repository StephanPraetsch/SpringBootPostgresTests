package com.mercateo.spring.boot.postgres.tests.persistence;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<UserDto, UUID> {

}
