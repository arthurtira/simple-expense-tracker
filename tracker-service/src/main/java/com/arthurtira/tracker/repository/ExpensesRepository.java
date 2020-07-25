package com.arthurtira.tracker.repository;

import com.arthurtira.tracker.model.Expense;
import com.arthurtira.tracker.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ExpensesRepository extends JpaRepository<Expense, Long> {
    Page<Expense> findAllByUserEntity(UserEntity userEntity, Pageable pageable);
    Page<Expense> findAllByUserEntityAndCategory(UserEntity userEntity, String category, Pageable pageable);
    Page<Expense> findAllByUserEntityAndCreatedOnAfterAndCreatedOnBefore(UserEntity userEntity, Date startDate, Date endDate, Pageable pageable);
    Page<Expense> findAllByUserEntityAndCategoryAndCreatedOnAfterAndCreatedOnBefore(UserEntity userEntity, String category, Date startDate, Date endDate, Pageable pageable);
    Optional<Expense> findByEntityId(String entityId);

}
