package com.Indianpolitics.politician_info.validation;

import java.util.List;

import com.Indianpolitics.politician_info.entity.PoliticianInfo;

public class Validation {

	public static boolean checkNull(PoliticianInfo politicianInfo) {
		
		boolean isvalid= false;
		if (politicianInfo == null) 
			
		return isvalid;
		return isvalid;

	}

	public static boolean vaildationPoliticianInfo(PoliticianInfo politicianInfo) {

		boolean validationOfPoliticianInfo = true;

		if(checkNull(politicianInfo)) {
			return false;
		}

		if (politicianInfo.getVotes() <= 0) {
			validationOfPoliticianInfo = false;
		}
		return validationOfPoliticianInfo;

	}

}
