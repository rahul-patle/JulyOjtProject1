package com.Indianpolitics.politician_info.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.micrometer.core.lang.NonNull;

@Entity
public class PoliticianInfo {

	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private String politicianName;
	@NotNull(message = "enter election area")
	private String electionArea;
	@NotNull(message = "enter politician party ")
	private String politicianParty;
	@Min(1)
	private int votes;

	public PoliticianInfo() {
		super();
	}

	public PoliticianInfo(String politicianName, @NotNull(message = "enter election area") String electionArea,
			@NotNull(message = "enter politician party ") String politicianParty,@Min(1) int votes) {
		super();
		this.politicianName = politicianName;
		this.electionArea = electionArea;
		this.politicianParty = politicianParty;
		this.votes = votes;
	}

	public String getPoliticianName() {
		return politicianName;
	}

	public void setPoliticianName(String politicianName) {
		this.politicianName = politicianName;
	}

	public String getElectionArea() {
		return electionArea;
	}

	public void setElectionArea(String electionArea) {
		this.electionArea = electionArea;
	}

	public String getPoliticianParty() {
		return politicianParty;
	}

	public void setPoliticianParty(String politicianParty) {
		this.politicianParty = politicianParty;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "PoliticianInfo [politicianName=" + politicianName + ", electionArea=" + electionArea
				+ ", politicianParty=" + politicianParty + ", votes=" + votes + "]";
	}

}