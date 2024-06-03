package com.sb_academic_evo.repository;

import java.util.List;

import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.entity.Subject;

/*************************************************

* Author: Sundhar Raj S - 12106

* Project_Name: Stduent-mark-evaluation-system

* Class: StudentRepo

************************************************/


public interface StudentRepo {
	
	public Student findByEmail(String email);

	public Student findByEmail(int stuMapId);
	
	public Student addStudent(Student student);

	public List<Subject> findSubjectsByStudentId(int studId);

}
