package com.sb_academic_evo.repository;

import java.util.List;

import com.sb_academic_evo.entity.Staff;
import com.sb_academic_evo.entity.StaffProfile;
import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.entity.Subject;

/*************************************************

* Author: Sundhar Raj S - 12106

* Project_Name: Stduent-mark-evaluation-system

* Class: StaffRepo

************************************************/

public interface StaffRepo {
	
	public boolean updateStaff(Staff staff);
	
	public boolean addStudent(Student student);
	
	public Staff findStaffById(int id);
	
	public Staff findByEmail(String email);
	
	public Student findStudentById(int id);
	
	public boolean updatePasswordUsingOldPassword(StaffProfile profile);
	
	public List<Student> getAllStudents();
	
	public List<Subject> findSubjectsByStudentId(int studentId);

}
