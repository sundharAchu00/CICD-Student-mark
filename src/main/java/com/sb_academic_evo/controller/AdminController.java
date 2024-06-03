package com.sb_academic_evo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sb_academic_evo.entity.Admin;
import com.sb_academic_evo.entity.Marks;
import com.sb_academic_evo.entity.Staff;
import com.sb_academic_evo.entity.StaffProfile;
import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.entity.StudentProfile;
import com.sb_academic_evo.entity.Subject;
import com.sb_academic_evo.service.AdminService;

/*************************************************

* Author: Sundhar Raj S - 12106

* Project_Name: Stduent-mark-evaluation-system

* Class: AdminController

************************************************/

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/admin")
public class AdminController {

	private AdminService service;

	public AdminController() {
		super();
	}

	@Autowired
	public AdminController(AdminService service) {
		super();
		this.service = service;
	}

	@PutMapping
	public ResponseEntity<?> addStaff(@RequestParam int id, @RequestParam("staffName") String staffName,
			@RequestParam("staffEmail") String staffEmail,
			@RequestParam("staffContact") String staffContact,
			@RequestParam("staffProfilePicture") MultipartFile formData) throws IOException {

		Admin admin = new Admin();
		admin.setAdminId(id);
		StaffProfile profile = new StaffProfile();
		profile.setStaffName(staffName);
		profile.setStaffEmail(staffEmail);
		profile.setStaffContact(Long.parseLong(staffContact));
		profile.setStaffProfilePicture(formData.getBytes());

		Staff staff = new Staff(0, profile, null);

		staff.setStaffProfile(profile);
		List<Staff> list = new ArrayList<>();
		list.add(staff);
		admin.setStaffs(list);
		
		
		if (service.addStaff(admin)) {
			new ResponseEntity<>(true, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}
		return null;

	}
	
	@PutMapping(path = "/addStudent")
	public ResponseEntity<?> saveStudentTemp(@RequestParam("studentName") String studentName, 
	                                         @RequestParam("studentEmail") String studentEmail, 
	                                         @RequestParam("stuContact") String stuContact, 
	                                         @RequestParam("stuAttPercentage") String stuAttPercentage, 
	                                         @RequestParam("stuProfilePicture") MultipartFile formData) throws IOException {
		
		Student student = new Student();
		StudentProfile profile = new StudentProfile();
		
		profile.setStudentName(studentName);
		profile.setStudentEmail(studentEmail);
		profile.setStuContact(Long.parseLong(stuContact));
		profile.setStuAttPercentage(Long.parseLong(stuAttPercentage));
		profile.setStuProfilePicture(formData.getBytes());
		
		student.setStudentProfile(profile);
		
	    // Process the formData and other fields
	    return new ResponseEntity<>(service.addStudent(student), HttpStatus.CREATED);
	}
	
	

	@PostMapping("/login")
	public ResponseEntity<?> loginRequest(@RequestParam String email, @RequestParam String password) {
		return service.findByEmail(email, password);
	}

	@PutMapping("/addSub")
	public ResponseEntity<?> addSubject(@RequestBody Staff staff) {
		return new ResponseEntity<>(service.addSubject(staff), HttpStatus.CREATED);

	}

	@PostMapping("/updatePass")
	public ResponseEntity<?> updatePassword(@RequestBody Admin admin, @RequestParam String newPass) {
		return new ResponseEntity<>(service.updatePasswordUsingOldPassword(admin, newPass), HttpStatus.OK);

	}

	@GetMapping("/staffIds")
	public List<Integer> getAllStaffIds() {
		return service.getAllStaffIds();

	}

	@GetMapping("/studIds")
	public List<Integer> getAllStudentIds() {
		return service.getAllStudentIds();

	}

	@PutMapping("/updateSub")
	public boolean assignSubjectToStaff(@RequestParam int staffId, @RequestParam int subjectId) {
		return service.assignSubjectToStaff(staffId, subjectId);
	}

	@GetMapping("/getStaff")
	public List<Staff> getAllStaff() {
		return service.getAllStaff();

	}

	@GetMapping("/getSubNotStaff")
	public List<Subject> getSubjectNotInStaff(@RequestParam int staffId) {
		System.out.println(staffId);
		return service.getSubjectIdsNotInStaff(staffId);

	}

	@GetMapping("/getSubNotInStu")
	public List<Subject> name(@RequestParam int studId) {
		return service.findUnassociatedSubjects(studId);
	}

	@PostMapping("/addSubject")
	public boolean addSubject(@RequestBody Subject subject) {
		return service.addOnlySubject(subject);
	}

	@PostMapping("/addSubjectStudent")
	public boolean assignSubjectToStudent(@RequestParam int stuId,@RequestParam int subId) {
		return service.assignSubjectToStudent(stuId, subId);

	}
	
	@GetMapping("/get/student/subjects")
	public ResponseEntity<Object> getSubjectsOfStudent(@RequestParam int stuId) {
		return service.getSubjectsAssignedToStudent(stuId);
		
	}
	
	
	@PutMapping("/marks")
	public Marks setMarksOfStudent(@RequestParam int stuId,@RequestParam int subId,@RequestBody Marks marks) {
		return service.setMarksOfStduent(stuId, subId ,marks);
	}
	
	@PutMapping("/attendance")
	public boolean setAttendance(@RequestParam int studId,@RequestParam int attendance) {
	
		return service.setAttendance(studId, attendance);
	}
	
	@GetMapping("/getResult")
	public ResponseEntity<?> getResult(@RequestParam int stuId) {
		return service.getResult(stuId);
	}
	
	@GetMapping("/getStudents")
	public List<Student> getAllStudent() {
		return service.getAllStudent();
	}
	
	@DeleteMapping("/removeStudent")
	public boolean removeStudent(@RequestParam int studentId){
		return service.removeStudent(studentId);
	}
	
	@DeleteMapping("/removeStaff")
	public boolean removeStaff(@RequestParam int staffId,@RequestParam int adminId){
		return service.removeStaff(staffId,adminId);
	}


}
