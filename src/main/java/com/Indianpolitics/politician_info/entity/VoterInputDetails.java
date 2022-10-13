package com.Indianpolitics.politician_info.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class VoterInputDetails {
@Id
	private String voterName;

	private String politicianName;

	private String politicianParty;

	public VoterInputDetails(String voterName, String politicianName, String politicianParty) {
		super();
		this.voterName = voterName;
		this.politicianName = politicianName;
		this.politicianParty = politicianParty;
	}

	public VoterInputDetails() {
		super();

	}
	public String getVoterName() {
		return voterName;
	}

	public void setVoterName(String voterName) {
		this.voterName = voterName;
	}

	public String getPoliticianName() {
		return politicianName;
	}

	public void setPoliticianName(String politicianName) {
		this.politicianName = politicianName;
	}

	public String getPoliticianParty() {
		return politicianParty;
	}

	public void setPoliticianParty(String politicianParty) {
		this.politicianParty = politicianParty;
	}

	@Override
	public String toString() {
		return "VoterInputDetails [voterName=" + voterName + ", politicianName=" + politicianName + ", politicianParty="
				+ politicianParty + "]";
	}

}
