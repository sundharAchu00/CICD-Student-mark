package com.sb_academic_evo.repository;

import java.util.List;

import com.sb_academic_evo.entity.Admin;
import com.sb_academic_evo.entity.Marks;
import com.sb_academic_evo.entity.Staff;
import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.entity.Subject;

/*************************************************

* Author: Sundhar Raj S - 12106

* Project_Name: Stduent-mark-evaluation-system

* Class: StudentRepo

************************************************/

public interface AdminRepo {

	public boolean updateAdmin(Admin admin);
	
	public Admin findById(int id);
	
	public Staff findStaffById(int id);
	
	public List<Staff> getAllStaff();
	
	public Admin findByEmail(String email);
	
	public boolean addSubject(Staff staff);

	public List<Integer> getSubjectIdOfStaffs(int id);
	
	public List<Subject> getSubjectsOfStaff(int id);
	
	public List<Subject> getNotSubjectsOfStaff(int id);

	public Subject findSubjectById(int id);
	
	public List<Integer> getStaffIdList();

	List<Subject> findSubjectsByStudentId(int studentId);

	List<Subject> findUnassociatedSubjects(int studentId);

	List<Integer> getAllStudentIds();

	boolean addOnlySubject(Subject subject);
	
	public Student getStudentById(int id);

	public List<Subject> getAllSubjects();

	List<Marks> getMarksOfStduent(int studId);
	
	public Marks setMarks(Marks marks) ;
	
	public boolean addStudent(Student student);

	public List<Student> getAllStudent();

	boolean removeStudent(int studentId);

	boolean removeStaff(Staff staff);

	boolean removeSubjectOfStaff(Staff staff, Subject subject);

}
