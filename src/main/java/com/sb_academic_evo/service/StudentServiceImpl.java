package com.sb_academic_evo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.entity.StudentProfile;
import com.sb_academic_evo.entity.Subject;
import com.sb_academic_evo.repository.StudentRepo;


@Transactional
@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepo repo;

	public StudentServiceImpl() {
		super();
	}

	@Autowired
	public StudentServiceImpl(StudentRepo repo) {
		super();
		this.repo = repo;
	}

	@Override
	public ResponseEntity<?> verifyStudent(String email, String password) {
		
		if(!email.isBlank()) {
			Student student = repo.findByEmail(email);
			
			if (password.equals(student.getStudentProfile().getStuPassword())) {
				return ResponseEntity.ok(student);
			} else {
				return ResponseEntity.badRequest().body("invalid credantials");
			}
			
		}
		return ResponseEntity.badRequest().body("User Not found");

	}

	@Override
	public ResponseEntity<?> updatePassword(Student student, String newPassword) {
		
		Student studentTemp = repo.findByEmail(student.getStuMapId());
		
		StudentProfile profile = studentTemp.getStudentProfile();
		
		if (student.getStudentProfile().getStuPassword().equals(profile.getStuPassword())) {
			profile.setStuPassword(newPassword);
			studentTemp.setStudentProfile(profile);
			repo.addStudent(studentTemp);
			return ResponseEntity.ok(studentTemp);
		} else {
			return ResponseEntity.badRequest().body("Bad Usercredentials");
		}

	}
	
	@Override
	public ResponseEntity<Object> getSubjectsAssignedToStudent(int studId) {


		List<Subject> subjects = repo.findSubjectsByStudentId(studId);
		
		System.out.println("all : "+subjects.size());

		if (!subjects.isEmpty()) {

			return new ResponseEntity<>(subjects, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("No Subject is associated", HttpStatus.BAD_REQUEST);
		}

	}

}
