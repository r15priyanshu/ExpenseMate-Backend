package com.anshuit.expensemate.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.anshuit.expensemate.entities.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

	@Query("{ '$or': [ { 'categoryOwner': ?0 } , { 'categoryOwner': 'SYSTEM' } ] }")
	List<Category> findCategoryByCategoryOwnerOrSystem(String userId);

}
