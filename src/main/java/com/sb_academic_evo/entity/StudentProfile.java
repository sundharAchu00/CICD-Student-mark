package com.sb_academic_evo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_profile_student")
public class StudentProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int stuProfileId;
	private String studentName;
	private String studentEmail;
	@Lob
	@Column(length = 200000,columnDefinition = "longblob")
	private byte[] stuProfilePicture;
	private String stuPassword;
	private long stuContact;
	@Column(nullable = false)
	private double stuAttPercentage;

	public int getStuProfileId() {
		return stuProfileId;
	}

	public void setStuProfileId(int stuProfileId) {
		this.stuProfileId = stuProfileId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public byte[] getStuProfilePicture() {
		return stuProfilePicture;
	}

	public void setStuProfilePicture(byte[] stuProfilePicture) {
		this.stuProfilePicture = stuProfilePicture;
	}

	public String getStuPassword() {
		return stuPassword;
	}

	public void setStuPassword(String stuPassword) {
		this.stuPassword = stuPassword;
	}

	public long getStuContact() {
		return stuContact;
	}

	public void setStuContact(long stuContact) {
		this.stuContact = stuContact;
	}

	public double getStuAttPercentage() {
		return stuAttPercentage;
	}

	public void setStuAttPercentage(double stuAttPercentage) {
		this.stuAttPercentage = stuAttPercentage;
	}

	public String getStudentEmail() {
		return studentEmail;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

}
