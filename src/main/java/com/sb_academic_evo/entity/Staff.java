package com.sb_academic_evo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_staff_map")
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int staffId;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = StaffProfile.class)
	private StaffProfile staffProfile;

	@ManyToMany(cascade = CascadeType.ALL, targetEntity = Subject.class)
	@JoinTable(
			  name = "subject_like", 
			  joinColumns = @JoinColumn(name = "staff_map_id"), 
			  inverseJoinColumns = @JoinColumn(name = "subject_map_id"))
	private List<Subject> subjects;

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public StaffProfile getStaffProfile() {
		return staffProfile;
	}

	public void setStaffProfile(StaffProfile staffProfile) {
		this.staffProfile = staffProfile;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
