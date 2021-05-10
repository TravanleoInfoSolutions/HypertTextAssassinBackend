package com.example.demo.repo;

import com.example.demo.domain.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepo extends CrudRepository<Form, Long> {


}
