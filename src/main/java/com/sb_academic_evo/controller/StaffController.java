package com.sb_academic_evo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb_academic_evo.entity.Staff;
import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.entity.StudentProfile;
import com.sb_academic_evo.service.StaffService;

/*************************************************

* Author: Sundhar Raj S - 12106

* Project_Name: Stduent-mark-evaluation-system

* Class: StaffController

************************************************/
@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/staff")
public class StaffController {

	private StaffService service;

	public StaffController() {
		super();
	}

	@Autowired
	public StaffController(StaffService service) {
		super();
		this.service = service;
	}
	
	@PutMapping(path = "/addStudent")
	public ResponseEntity<?> saveStudentTemp(@RequestParam int id, @RequestParam("studentName") String studentName, 
	                                         @RequestParam("studentEmail") String studentEmail, 
	                                         @RequestParam("stuContact") String stuContact, 
	                                         @RequestParam("stuAttPercentage") String stuAttPercentage, 
	                                         @RequestParam("stuProfilePicture") MultipartFile formData) throws IOException {
		
		Student student = new Student();
		StudentProfile profile = new StudentProfile();
		
		profile.setStudentName(studentName);
		profile.setStudentEmail(studentEmail);
		profile.setStuContact(Long.parseLong(stuContact));
		profile.setStuProfilePicture(formData.getBytes());
		
		student.setStudentProfile(profile);
		
	    return new ResponseEntity<>(service.addStudent(id, student), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> saveStudent(@RequestParam int id, @RequestBody Student student) {
		return new ResponseEntity<>(service.addStudent(id, student), HttpStatus.CREATED);

	}

	@PutMapping("/updatePass")
	public ResponseEntity<?> updatePassword(@RequestBody Staff staff, @RequestParam String newPass) {
		return new ResponseEntity<>(service.updatePasswordUsingOldPassword(staff, newPass), HttpStatus.OK);

	}

	@PutMapping("/forget-pass")
	public ResponseEntity<?> forgetPassword(@RequestBody Staff staff, @RequestParam String newPass) {
		return new ResponseEntity<>(service.updatePasswordUsingPhoneNumber(staff, newPass), HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<?> getAllStudent() {
		return new ResponseEntity<>(service.getAllStudents(), HttpStatus.ACCEPTED);

	}
	
	@GetMapping("/login")
	public ResponseEntity<?> loginRequest(@RequestParam String email,@RequestParam String password){
		return service.findByEmail(email, password);
	}
		

}
