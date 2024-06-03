package com.sb_academic_evo.repository;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.entity.Subject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/*************************************************

* Author: Sundhar Raj S - 12106

* Project_Name: Stduent-mark-evaluation-system

* Class: StudentRepoImpl

************************************************/

@Repository
public class StudentRepoImpl implements StudentRepo {

	private EntityManager em;
	
	private static final Logger logger = LoggerFactory.getLogger(StudentRepoImpl.class);

	public StudentRepoImpl(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Student findByEmail(String email) {

		Student student = null;

		try {
			Query query = em.createQuery("select s from Student s where s.studentProfile.studentEmail = :email",
					Student.class);
			return (Student) query.setParameter("email", email).getSingleResult();
		} catch (Exception e) {
			logger.debug("error message 11", e);
			return student;
		}

	}

	@Override
	public Student findByEmail(int stuMapId) {
		
		return em.find(Student.class, stuMapId);
		
	}

	@Override
	public Student addStudent(Student student) {
		
		Student studentTemp = null;
		try {
			studentTemp = em.merge(student);
			return studentTemp;
		} catch (Exception e) {
			logger.debug("error message 12", e);
		}finally {
			em.close();
		}
		return studentTemp;
	}
	
	
	@Override
	public List<Subject> findSubjectsByStudentId(int studentId) {
		try {
			return em.createQuery("SELECT s FROM Subject s JOIN s.students stu WHERE stu.stuMapId = :studentId",
					Subject.class).setParameter("studentId", studentId).getResultList();
		} catch (Exception e) {
			logger.debug("error message 13", e);
			return Collections.emptyList();
		}finally {
			em.close();
		}
	}
	

}
