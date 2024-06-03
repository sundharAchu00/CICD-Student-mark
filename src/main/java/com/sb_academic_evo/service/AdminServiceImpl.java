package com.sb_academic_evo.service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sb_academic_evo.entity.Admin;
import com.sb_academic_evo.entity.Marks;
import com.sb_academic_evo.entity.Staff;
import com.sb_academic_evo.entity.StaffProfile;
import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.entity.StudentProfile;
import com.sb_academic_evo.entity.Subject;
import com.sb_academic_evo.repository.AdminRepo;

import jakarta.transaction.Transactional;


/*************************************************

* Author: Sundhar Raj S - 12106

* Project_Name: Stduent-mark-evaluation-system

* Class: AdminServiceImpl

************************************************/



@Transactional
@Service
public class AdminServiceImpl implements AdminService {

	private AdminRepo repo;
	private EmailService emailService;
	private SecureRandom random = new SecureRandom();

	public AdminServiceImpl() {
		super();
	}

	@Autowired
	public AdminServiceImpl(AdminRepo repo, EmailService emailService) {
		super();
		this.repo = repo;
		this.emailService = emailService;
	}

	public String getSaltString() {
		final String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ@abcdefghijklmnojqklmnopqrstuvwxyz$123456789";
		StringBuilder salt = new StringBuilder();
		while (salt.length() <= 9) { // length of the random string.
			int index = (int) (random.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		return salt.toString();

	}

	@Override
	public boolean addStaff(Admin admin) {

		Admin adminTemp = repo.findById(admin.getAdminId());

		Staff staff = admin.getStaffs().get(0);

		StaffProfile profile = admin.getStaffs().get(0).getStaffProfile();
		profile.setStaffPassword(getSaltString());

		staff.setStaffProfile(profile);

		adminTemp.getStaffs().add(staff);

		return emailService.staffMail(profile) && repo.updateAdmin(adminTemp);

	}

	@Override
	public Admin findById(int id) {

		return null;
	}

	@Override
	public ResponseEntity<?> findByEmail(String email, String password) {

		Admin admin = null;
		if (!email.isEmpty()) {
			admin = repo.findByEmail(email);

			if (admin.getAdminPassword().equals(password)) {
				return new ResponseEntity<>(admin, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>(new BadRequestException("Userdetails Not found"), HttpStatus.BAD_REQUEST);
	}

	@Override
	public boolean addSubject(Staff staff) {

		Staff staffTemp = repo.findStaffById(staff.getStaffId());

		staffTemp.getSubjects().add(staff.getSubjects().get(0));

		return Objects.nonNull(repo.addSubject(staffTemp));

	}

	@Override
	public List<Staff> getAllStaff() {

		return repo.getAllStaff();
	}

	@Override
	public boolean updatePasswordUsingOldPassword(Admin admin, String newPass) {

		Admin admintemp = null;

		if (!admin.getAdminPassword().isEmpty()) {
			admintemp = repo.findById(admin.getAdminId());

			if (admin.getAdminPassword().equals(admintemp.getAdminPassword())) {
				admintemp.setAdminPassword(newPass);

				return repo.updateAdmin(admintemp);
			}
		}

		return false;
	}

	@Override
	public List<Integer> getAllStaffIds() {
		List<Staff> staffList = repo.getAllStaff();

		// Use stream to map each Staff object to its staffId and collect them into a
		// List<Integer>
		return staffList.stream().map(Staff::getStaffId).collect(Collectors.toList());
	}

	@Override
	public boolean assignSubjectToStaff(int staffId, int subjectId) {

		// Get the staff member by ID
		Staff staff = repo.findStaffById(staffId);

		// Get the subject by ID
		Subject subject = repo.findSubjectById(subjectId);

		// Check if the subject is already associated with the staff
		if (staff.getSubjects().contains(subject)) {
			// Subject is already associated with the staff, so return false
			return false;
		} else {
			// Subject is not associated with the staff, so proceed with assignment
			staff.getSubjects().add(subject);

			// Update the staff member in the database
			repo.addSubject(staff); // Assuming you have a method to update staff

			// Return true to indicate successful assignment
			return true;
		}
	}

	@Override
	public List<Integer> getStaffIdList() {

		return repo.getStaffIdList();
	}

	@Override
	public List<Subject> getSubjectIdsNotInStaff(int staffId) {
		return repo.getNotSubjectsOfStaff(staffId);
	}

	@Override
	public List<Subject> findUnassociatedSubjects(int studId) {

		return repo.findUnassociatedSubjects(studId);
	}

	@Override
	public List<Integer> getAllStudentIds() {

		return repo.getAllStudentIds();
	}

	@Override
	public boolean addOnlySubject(Subject subject) {
		return repo.addOnlySubject(subject);

	}

	@Override
	public boolean assignSubjectToStudent(int studentId, int subjectId) {

		Student student = repo.getStudentById(studentId);

		Subject subject = repo.findSubjectById(subjectId);

		if (subject.getStudents().contains(student)) {
			return false;
		} else {

			Marks marks = new Marks();
			marks.setStudent(student);

			subject.getMarks().add(marks);

			subject.getStudents().add(student);

			return repo.addOnlySubject(subject);
		}

	}

	@Override
	public ResponseEntity<Object> getSubjectsAssignedToStudent(int studId) {

		List<Subject> subjects = repo.findSubjectsByStudentId(studId);


		if (!subjects.isEmpty()) {
			subjects.stream()
					.filter(sub -> sub.getMarks().stream().anyMatch(mar -> Objects.isNull(mar.getOverallResult())))
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(subjects, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("No Subject is associated or student is not present", HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public Marks setMarksOfStduent(int studId, int subId, Marks marksIn) {

		Student student = repo.getStudentById(studId);

		Subject subject = repo.findSubjectById(subId);

		Marks[] markStu = new Marks[1]; // Array with a single element

		subject.getMarks().forEach(mark -> {

			int exter = marksIn.getExternalPercentage();
			int inter = marksIn.getInternalPercentage();

			mark.setInternalPercentage(inter);
			mark.setExternalPercentage(exter);

			if (exter >= 40 && inter >= 40) {

				mark.setOverallResult("Pass");
			} else {
				mark.setOverallResult("Fail");
			}

			markStu[0] = mark;

		});

		markStu[0].setStudent(student);

		return repo.setMarks(markStu[0]);
	}

	@Override
	public boolean setAttendance(int studId, int attendance) {

		Student student = repo.getStudentById(studId);

		student.getStudentProfile().setStuAttPercentage(attendance);

		return repo.addStudent(student);

	}


	
	@Override
	public ResponseEntity<?> getResult(int stuId) {
	    Student student = repo.getStudentById(stuId);
	    List<Marks> marks = repo.getMarksOfStduent(stuId);
	    List<Subject> subjects = repo.findSubjectsByStudentId(stuId);

	    Set<String> reasons = new HashSet<>();
	    Set<String> results = new HashSet<>();

	    if (subjects.isEmpty()) {
	        reasons.add("Subject Not assigned yet");
	    } else {
	        assessStudentResult(student, marks, reasons, results);
	    }

	    if (!reasons.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.ACCEPTED).body(reasons);
	    } else {
	        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Student passed, keep growing up");
	    }
	}

	private void assessStudentResult(Student student, List<Marks> marks, Set<String> reasons, Set<String> results) {
	    marks.forEach(mark -> {
	        String overallResult = mark.getOverallResult();
	        boolean passCondition = overallResult != null && "Pass".equals(overallResult);
	        boolean failCondition = !passCondition;

	        if (passCondition && student.getStudentProfile().getStuAttPercentage() >= 75) {
	            results.add("Pass");
	        } else if (failCondition) {
	            results.add("Fail");
	            if (!"Pass".equals(overallResult)) {
	                reasons.add("marks");
	            }
	            if (student.getStudentProfile().getStuAttPercentage() < 75) {
	                reasons.add("attendance");
	            }
	        } else {
	            results.add("Fail");
	            reasons.add("marks");
	            if (student.getStudentProfile().getStuAttPercentage() < 75) {
	                reasons.add("attendance");
	            }
	        }
	    });
	}





	@Override
	public List<Student> getAllStudent() {

		return repo.getAllStudent();
	}

	@Override
	public boolean removeStudent(int studentId) {
		return repo.removeStudent(studentId);

	}

	@Override
	public boolean removeStaff(int staffId, int adminId) {

		Staff staff = repo.findStaffById(staffId);

		List<Subject> subjects = repo.getSubjectsOfStaff(staffId);

		Set<Boolean> result = new HashSet<>();
		Admin admin = repo.findById(adminId);

		if (!subjects.isEmpty()) {
			
			subjects.forEach(sub -> result.add(repo.removeSubjectOfStaff(staff, sub)));

			admin.getStaffs().remove(staff);
			repo.updateAdmin(admin);
			return result.stream().anyMatch(Boolean::booleanValue) && repo.removeStaff(staff);
		} else {
			admin.getStaffs().remove(staff);
			repo.updateAdmin(admin);
			return repo.removeStaff(staff);
		}

	}

	@Override
	public boolean addStudent(Student student) {

		StudentProfile profile = student.getStudentProfile();

		profile.setStuPassword(getSaltString());

		return emailService.studentMail(profile) && repo.addStudent(student);

	}

}
