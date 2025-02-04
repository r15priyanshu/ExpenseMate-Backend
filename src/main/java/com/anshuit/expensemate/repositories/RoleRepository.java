package com.anshuit.expensemate.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.anshuit.expensemate.entities.Role;

public interface RoleRepository extends MongoRepository<Role, ObjectId> {

}
