package com.Indianpolitics.politician_info.sorting;

import java.util.Comparator;

import com.Indianpolitics.politician_info.entity.PoliticianInfo;

public class SortByElectionArea implements Comparator<PoliticianInfo>{

	@Override
	public int compare(PoliticianInfo p1, PoliticianInfo p2) {
		
		return (p1.getElectionArea()).compareTo(p2.getElectionArea());
	}

	
}
