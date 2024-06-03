package com.sb_academic_evo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sb_academic_evo.entity.Admin;
import com.sb_academic_evo.entity.Marks;
import com.sb_academic_evo.entity.Staff;
import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.entity.Subject;

/*************************************************

* Author: Sundhar Raj S - 12106

* Project_Name: Stduent-mark-evaluation-system

* Class: AdminService

************************************************/


public interface AdminService {
	
	public boolean addStaff(Admin admin);
	
	public Admin findById(int id);
	
	public ResponseEntity<?> findByEmail(String email,String password);
	
	public boolean addSubject(Staff staff);
	
	public List<Staff> getAllStaff();

	public boolean updatePasswordUsingOldPassword(Admin admin, String newPass);

	public List<Integer> getAllStaffIds();

	public boolean assignSubjectToStaff(int staffId, int subjectId);
	
	public List<Integer> getStaffIdList();

	List<Subject> getSubjectIdsNotInStaff(int staffId);

	public List<Subject> findUnassociatedSubjects(int studId);

	public List<Integer> getAllStudentIds();

	boolean addOnlySubject(Subject subject);

	boolean assignSubjectToStudent(int studentId, int subjectId);

	ResponseEntity<Object> getSubjectsAssignedToStudent(int studId);
	
	Marks setMarksOfStduent(int studId, int subId,Marks marks);

	boolean setAttendance(int studId, int attendance);

	public List<Student> getAllStudent();

	ResponseEntity<?> getResult(int stuId);

	boolean removeStudent(int studentId);

	boolean removeStaff(int staffId,int adminId);

	public boolean addStudent(Student student);

	

}
