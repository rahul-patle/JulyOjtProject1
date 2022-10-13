package com.Indianpolitics.politician_info.sorting;

import java.util.Comparator;

import com.Indianpolitics.politician_info.entity.PoliticianInfo;

public class SortByPoliticianVotes implements Comparator<PoliticianInfo> {

	@Override
	public int compare(PoliticianInfo p1, PoliticianInfo p2) {

		if (p1.getVotes() == (p2.getVotes())) {

			return 0;
		} else if (p1.getVotes() > p2.getVotes())

			return 1;
		else {
			return -1;
		}
	}
}
