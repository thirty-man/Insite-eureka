package com.thirty.insitememberservice.button.repository;

import com.thirty.insitememberservice.application.entity.Application;
import com.thirty.insitememberservice.button.entity.Button;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ButtonRepository extends JpaRepository<Button, Integer> {

    Optional<Button> findByApplicationAndNameAndIsDeletedIsFalse(Application application, String name);
    Optional<Button> findByButtonIdAndApplicationAndIsDeletedIsFalse(int buttonId, Application application);
    Page<Button> findAllByApplicationAndIsDeletedIsFalse(Application application, Pageable pageable);
}
