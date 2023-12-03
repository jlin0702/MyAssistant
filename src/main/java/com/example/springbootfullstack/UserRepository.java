package com.example.springbootfullstack;

import org.springframework.data.repository.CrudRepository;
import com.example.springbootfullstack.Entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
}
