package com.sb_academic_evo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sb_academic_evo.entity.Staff;
import com.sb_academic_evo.entity.StaffProfile;
import com.sb_academic_evo.entity.Student;
import com.sb_academic_evo.repository.StaffRepo;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class StaffServiceImpl implements StaffService {

	private StaffRepo repo;

	public StaffServiceImpl() {
		super();
	}

	@Autowired
	public StaffServiceImpl(StaffRepo repo) {
		super();
		this.repo = repo;
	}

	@Override
	public boolean addStudent(int id, Student student) {

		return repo.addStudent(student);
	}

	@Override
	public Staff findStaffById(int id) {

		return repo.findStaffById(id);
	}

	@Override
	public boolean updatePasswordUsingOldPassword(Staff staff, String newPass) {

		Staff staffTemp = repo.findStaffById(staff.getStaffId());

		StaffProfile profile = staffTemp.getStaffProfile();

		if ((staff.getStaffProfile().getStaffPassword()).equals(profile.getStaffPassword())) {
			profile.setStaffPassword(newPass);
			staffTemp.setStaffProfile(profile);
			repo.updateStaff(staffTemp);
			return true;
		}

		return false;
	}

	@Override
	public boolean updatePasswordUsingPhoneNumber(Staff staff, String newPass) {

		Staff staffTemp = repo.findStaffById(staff.getStaffId());

		StaffProfile profile = staffTemp.getStaffProfile();

		if (staff.getStaffProfile().getStaffContact() == profile.getStaffContact()) {
			profile.setStaffPassword(newPass);
			staffTemp.setStaffProfile(profile);
			repo.updateStaff(staffTemp);
			return true;
		}

		return false;
	}

	@Override
	public List<Student> getAllStudents() {

		return repo.getAllStudents();
	}


	@Override
	public ResponseEntity<?> findByEmail(String email, String password) {

		Staff staff = null;

		if (!email.isEmpty()) {
			staff = repo.findByEmail(email);
			if( password.equals(staff.getStaffProfile().getStaffPassword())) {
				return ResponseEntity.ok(staff);
			}else {
				return ResponseEntity.badRequest().body("bad credentials");
			}
		}

		return ResponseEntity.badRequest().body("User not found");

	}

}
