package com.sb_academic_evo.service;

import org.springframework.http.ResponseEntity;

import com.sb_academic_evo.entity.Student;

public interface StudentService {

	ResponseEntity<?> verifyStudent(String email, String password);

	ResponseEntity<?> updatePassword(Student student, String newPassword);

	ResponseEntity<Object> getSubjectsAssignedToStudent(int studId);

}
