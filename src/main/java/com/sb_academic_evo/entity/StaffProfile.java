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
@Table(name = "tbl_profile_staff")
public class StaffProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int staffProfileId;
	private String staffName;
	private String staffEmail;
	@Lob
	@Column(columnDefinition = "longblob")
	private byte[] staffProfilePicture;
	private String staffPassword;
	private long staffContact;

	public int getStaffProfileId() {
		return staffProfileId;
	}

	public void setStaffProfileId(int staffProfileId) {
		this.staffProfileId = staffProfileId;
	}

	public byte[] getStaffProfilePicture() {
		return staffProfilePicture;
	}

	public void setStaffProfilePicture(byte[] staffProfilePicture) {
		this.staffProfilePicture = staffProfilePicture;
	}

	public String getStaffPassword() {
		return staffPassword;
	}

	public void setStaffPassword(String staffPassword) {
		this.staffPassword = staffPassword;
	}

	public long getStaffContact() {
		return staffContact;
	}

	public void setStaffContact(long staffContact) {
		this.staffContact = staffContact;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffEmail() {
		return staffEmail;
	}

	public void setStaffEmail(String staffEmail) {
		this.staffEmail = staffEmail;
	}

}
