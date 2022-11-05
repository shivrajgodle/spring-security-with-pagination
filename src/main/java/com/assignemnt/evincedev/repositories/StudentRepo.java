package com.assignemnt.evincedev.repositories;

import com.assignemnt.evincedev.entities.Student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Integer> {

	Optional<Student> findByEmail(String Email);
}
