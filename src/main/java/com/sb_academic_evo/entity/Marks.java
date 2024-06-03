package com.sb_academic_evo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Marks {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int markId;
	private int internalPercentage;
	private int externalPercentage;
	private String overallResult;
	@ManyToOne(targetEntity = Student.class,cascade = CascadeType.ALL)
	private Student student;
	public int getMarkId() {
		return markId;
	}
	public void setMarkId(int markId) {
		this.markId = markId;
	}
	public int getInternalPercentage() {
		return internalPercentage;
	}
	public void setInternalPercentage(int internalPercentage) {
		this.internalPercentage = internalPercentage;
	}
	public int getExternalPercentage() {
		return externalPercentage;
	}
	public void setExternalPercentage(int externalPercentage) {
		this.externalPercentage = externalPercentage;
	}
	public String getOverallResult() {
		return overallResult;
	}
	public void setOverallResult(String overallResult) {
		this.overallResult = overallResult;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	
	

}
