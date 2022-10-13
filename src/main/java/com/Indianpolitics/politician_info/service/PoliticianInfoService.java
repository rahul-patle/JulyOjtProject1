package com.Indianpolitics.politician_info.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.Indianpolitics.politician_info.entity.PoliticianInfo;
import com.Indianpolitics.politician_info.entity.VoterInputDetails;

public interface PoliticianInfoService {

	public boolean savePolticianInfo(PoliticianInfo politicianInfo);

	public HashMap<String, String> uploadExcelSheet(MultipartFile file, HttpSession httpsession);

	public List<PoliticianInfo> getAllPolticianInfo();

	public PoliticianInfo getPolitcianInfoByName(String politicianName);
	
	public List <PoliticianInfo> countByProjections();
	
	public int countOfPoliticians();

	public List<PoliticianInfo> sortBy(String sortBy);

	public List<PoliticianInfo> sortByDsc(String sortByDsc);

	public PoliticianInfo maxVotesOfPolitician(String electionArea);

	public boolean updatePoliticianVotes(PoliticianInfo politicianInfo );
	
	public int getcountOfMlaByParty(String nameOfParty) ;
	

	public List<PoliticianInfo> sortByCriteria() ;
	
	
	public List<PoliticianInfo> getListofPoliticainByPartyName(String partyName) ;
	
	public boolean deletePoliticianRecord(String politicianName);
	
	public boolean updatVotesByVoter(VoterInputDetails voterInput);
		
}