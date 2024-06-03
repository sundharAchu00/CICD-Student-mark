package com.sb_academic_evo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sb_academic_evo.entity.Staff;
import com.sb_academic_evo.entity.Student;

public interface StaffService {

	public boolean addStudent(int id, Student staff);
	
	public Staff findStaffById(int id);
	
	public boolean updatePasswordUsingOldPassword(Staff staff,String newPass);
	
	public boolean updatePasswordUsingPhoneNumber(Staff staff,String newPass);
	
	public List<Student> getAllStudents();

	public ResponseEntity<?> findByEmail(String email, String password);

}
