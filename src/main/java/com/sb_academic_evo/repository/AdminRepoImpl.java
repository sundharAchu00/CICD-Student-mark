package com.sb_academic_evo.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sb_academic_evo.entity.Admin;
import com.sb_academic_evo.entity.Marks;
import com.sb_academic_evo.entity.Staff;
import com.sb_academic_evo.entity.StaffProfile;
import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.entity.Subject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*************************************************
 * 
 * Author: Sundhar Raj S - 12106
 * 
 * Project_Name: Stduent-mark-evaluation-system
 * 
 * Class: StudentRepoImpl
 * 
 ************************************************/

@Repository
public class AdminRepoImpl implements AdminRepo {

	private EntityManager em;

	private static final Logger logger = LoggerFactory.getLogger(AdminRepoImpl.class);

	@Autowired
	public AdminRepoImpl(EntityManager em) {
		super();
		this.em = em;
	}

	public AdminRepoImpl() {
		super();

	}

	@Override
	public boolean updateAdmin(Admin admin) {

		try {
			em.merge(admin);
			return true;
		} catch (Exception e) {
			logger.debug("update admin", e);
			return false;
		}
	}

	@Override
	public Admin findById(int id) {

		Admin admin = null;

		try {
			admin = em.find(Admin.class, id);
		} catch (Exception e) {
			logger.debug("find by id", e);
		} finally {
			em.close();
		}
		return admin;
	}

	@Override
	public Admin findByEmail(String email) {

		Admin admin = null;
		try {
			Query query = em.createQuery("select a from Admin a where a.adminEmail = :email");

			return (Admin) query.setParameter("email", email).getSingleResult();
		} catch (Exception e) {
			logger.debug("find by email", e);
		} finally {
			em.close();
		}
		return admin;
	}

	@Override
	public Staff findStaffById(int id) {

		try {
			return em.find(Staff.class, id);
		} catch (Exception e) {
			logger.debug("find staff by id", e);
			return null;
		} finally {
			em.close();
		}

	}

	@Override
	public boolean addSubject(Staff staff) {

		try {
			em.merge(staff);
			return true;
		} catch (Exception e) {
			logger.debug("Add subject", e);
			return false;
		} finally {
			em.close();
		}

	}

	@Override
	public List<Staff> getAllStaff() {

		try {
			String jpqlQuery = "SELECT staff.staffId, staff.staffProfile FROM Staff staff";
			List<Object[]> resultList = em.createQuery(jpqlQuery, Object[].class).getResultList();

			List<Staff> staffList = new ArrayList<>();
			for (Object[] result : resultList) {
				int staffId = (int) result[0]; // Casting staffId to int
				StaffProfile staffProfile = (StaffProfile) result[1]; // Casting staffProfile to StaffProfile

				// Create a new Staff instance with the retrieved values
				Staff staff = new Staff();
				staff.setStaffId(staffId);
				staff.setStaffProfile(staffProfile);

				// Add the Staff instance to the list
				staffList.add(staff);
			}
			return staffList;
		} catch (Exception e) {
			logger.debug("Get all the staff", e);
		} finally {
			em.close();
		}
		return Collections.emptyList();
	}

	@Override
	public List<Integer> getSubjectIdOfStaffs(int id) {

		String jpqlQuery = "SELECT DISTINCT s.subjectId FROM Staff st JOIN st.subjects s WHERE st.facultyId = :staffId";

		List<Integer> subjectIds = null;
		try {
			return em.createQuery(jpqlQuery, Integer.class).setParameter("facultyId", id).getResultList();
		} catch (Exception e) {
			logger.debug("find all subjects of student", e);
			return subjectIds;
		} finally {
			em.close();
		}

	}

	@Override
	public List<Subject> getSubjectsOfStaff(int id) {
		String jpqlQuery = "SELECT DISTINCT s FROM Staff st JOIN st.subjects s WHERE st.staffId = :staffId";

		List<Subject> subjects = null;
		try {
			return em.createQuery(jpqlQuery, Subject.class).setParameter("staffId", id).getResultList();
		} catch (Exception e) {
			logger.debug("find subject foe staff", e);
			return subjects;
		} finally {
			em.close();
		}

	}

	@Override
	public Subject findSubjectById(int id) {

		try {
			return em.find(Subject.class, id);
		} catch (Exception e) {
			logger.debug("error message{} :" , e.getMessage());
			return null;
		}

	}

	@Override
	public List<Subject> getNotSubjectsOfStaff(int id) {

		String jpqlQuery = "SELECT s FROM Subject s WHERE s NOT IN (SELECT sub FROM Staff st JOIN st.subjects sub WHERE st.staffId = :staffId)";

		try {
			return em.createQuery(jpqlQuery, Subject.class).setParameter("staffId", id).getResultList();
		} catch (Exception e) {
			logger.debug("error message 1", e);
			return Collections.emptyList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Integer> getStaffIdList() {

		try {
			return em.createQuery("select s.staffId from Staff s", Integer.class).getResultList();
		} catch (Exception e) {
			logger.debug("error message 2", e);
			return Collections.emptyList();
		}

	}

	@Override
	public List<Subject> findSubjectsByStudentId(int studentId) {
		try {
			return em.createQuery("SELECT s FROM Subject s JOIN s.students stu WHERE stu.stuMapId = :studentId",
					Subject.class).setParameter("studentId", studentId).getResultList();
		} catch (Exception e) {
			logger.debug("error message 3", e);
			return Collections.emptyList();
		} finally {
			em.close();
		}
	}

	@Override
	public List<Subject> findUnassociatedSubjects(int studentId) {

		return em.createQuery(
				"SELECT s FROM Subject s WHERE s.subjectId NOT IN (SELECT ss.subjectId FROM Subject ss JOIN ss.students stu WHERE stu.stuMapId = :studentId)",
				Subject.class).setParameter("studentId", studentId).getResultList();
	}

	@Override
	public List<Integer> getAllStudentIds() {

		try {
			return em.createQuery("select s.stuMapId from Student s", Integer.class).getResultList();
		} catch (Exception e) {
			logger.debug("error message 4", e);
			return Collections.emptyList();
		} finally {
			em.close();
		}
	}

	@Override
	public boolean addOnlySubject(Subject subject) {

		try {

			em.merge(subject);
			return true;
		} catch (Exception e) {
			logger.debug("error message 5", e);
			return false;
		} finally {
			em.close();
		}

	}

	@Override
	public Student getStudentById(int id) {

		try {
			return em.find(Student.class, id);
		} catch (Exception e) {
			logger.debug("error message 6", e);
			return null;
		} finally {
			em.close();
		}
	}

	@Override
	public List<Subject> getAllSubjects() {

		return em.createQuery("select s from Subject s", Subject.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Marks> getMarksOfStduent(int studId) {

		List<Marks> marks = null;
		try {

			String query = "SELECT * FROM `shaji-evo-db`.marks m WHERE m.student_stu_map_id = :stuMapId";

			marks = em.createNativeQuery(query, Marks.class).setParameter("stuMapId", studId).getResultList();

			return marks;
		} catch (Exception e) {

			logger.debug("error message 7", e);
			return marks;
		}

	}

	@Override
	public Marks setMarks(Marks marks) {

		try {
			return em.merge(marks);
		} catch (Exception e) {

			logger.debug("error message8", e);
			return null;
		}

	}

	@Override
	public boolean addStudent(Student student) {

		try {
			em.merge(student);
			return true;
		} catch (Exception e) {
			logger.debug("error add student 10", e); 
			return false;
		}

	}

	@Override
	public List<Student> getAllStudent() {

		return em.createQuery("select s from Student s", Student.class).getResultList();
	}

	@Override
	public boolean removeStudent(int studentId) {

		// Step 1: Retrieve the student by ID
		Student student = em.find(Student.class, studentId);
		if (student != null) {
			// Step 2: Retrieve the subject associated with the student
			List<Subject> subjects = em
					.createQuery("SELECT s FROM Subject s JOIN FETCH s.students WHERE :student MEMBER OF s.students",
							Subject.class)
					.setParameter("student", student).getResultList();

			// Step 4: Retrieve and delete marks associated with the student
			List<Marks> marks = em.createQuery("SELECT m FROM Marks m WHERE m.student = :student", Marks.class)
					.setParameter("student", student).getResultList();

			// removing the marks which is associated to student
			for (Subject subject : subjects) {
				// Step 3: Remove the student from the subject
				subject.getStudents().remove(student);
				subject.getMarks().removeAll(marks);
				em.merge(subject); // Update the subject
			}

			for (Marks mark : marks) {
				em.remove(mark);
			}

			// Step 5: Delete the student
			em.remove(student);
			return true;
		} else {
			return false;
			// Handle case where student with given ID doesn't exist
			// Maybe throw an exception or handle it according to your business logic
		}

	}

	@Override
	public boolean removeStaff(Staff staff) {

		try {
			em.remove(staff);
			return true;
		} catch (Exception e) {
			logger.debug("error add student remove staff", e);
			return false;
		} finally {
			em.close();
		}

	}

	@Override
	public boolean removeSubjectOfStaff(Staff staff, Subject subject) {

		try {

			// Step 1: Remove the subject from the staff's list of subjects
			staff.getSubjects().remove(subject);
			em.merge(staff); // Update the staff entity

			// Step 2: Delete the subject
			em.remove(subject);
			// step 3:
			em.persist(subject);
			return true;
		} catch (Exception e) {
			logger.debug("error remove subject", e);
			return false;
		}

	}

}
