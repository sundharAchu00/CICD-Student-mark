package com.sb_academic_evo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.service.StudentService;

/*************************************************

* Author: Sundhar Raj S - 12106

* Project_Name: Stduent-mark-evaluation-system

* Class: StudentController

************************************************/

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/student")
public class StudentController {

	private StudentService service;

	
	public StudentController(StudentService service) {
		super();
		this.service = service;
	}

	@GetMapping("/login")
	public ResponseEntity<?> verifyStudent(@RequestParam String email, @RequestParam String password) {
		return service.verifyStudent(email, password);

	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<?> updatePassword(@RequestBody Student student, @RequestParam String newPassword) {
		return service.updatePassword(student,newPassword);
	
	}
	
	@GetMapping("/get/student/subjects")
	public ResponseEntity<Object> getSubjectsOfStudent(@RequestParam int stuId) {
		return service.getSubjectsAssignedToStudent(stuId);
		
	}

}
