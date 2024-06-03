package com.sb_academic_evo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_student_map")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int stuMapId;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = StudentProfile.class)
	private StudentProfile studentProfile;

	public int getStuMapId() {
		return stuMapId;
	}

	public void setStuMapId(int stuMapId) {
		this.stuMapId = stuMapId;
	}

	public StudentProfile getStudentProfile() {
		return studentProfile;
	}

	public void setStudentProfile(StudentProfile studentProfile) {
		this.studentProfile = studentProfile;
	}

}
