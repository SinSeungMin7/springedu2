package com.example.springedu2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springedu2.entity.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

}
