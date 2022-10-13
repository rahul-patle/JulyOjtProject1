package com.Indianpolitics.politician_info.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Indianpolitics.politician_info.entity.PoliticianInfo;
import com.Indianpolitics.politician_info.entity.VoterInputDetails;

import com.Indianpolitics.politician_info.exception.MoreThanOneVoteAtATimeNotAllowed;
import com.Indianpolitics.politician_info.exception.PoliticianAlreadyExistException;
import com.Indianpolitics.politician_info.exception.PoliticiansNotFoundException;

import com.Indianpolitics.politician_info.service.PoliticianInfoService;

@RestController
public class PoliticianInfoController {

	@Autowired
	private PoliticianInfoService politicianInfoServiceService;
	// database name case must be same

//1//
	@PostMapping(value = "/savePoliticianInfo")
	public ResponseEntity<Boolean> savePoliticianInfo(@Valid @RequestBody PoliticianInfo politicianInfo) {

		boolean savePolticianInfo = politicianInfoServiceService.savePolticianInfo(politicianInfo);

		if (savePolticianInfo)
			return new ResponseEntity<Boolean>(savePolticianInfo, HttpStatus.OK);
		else
			throw new PoliticianAlreadyExistException("PoliticianAlreadyExistException");

	}

//2//
	@PostMapping("/uploadExcelSheet")
	public ResponseEntity<HashMap<String, String>> uploadExcelSheet(@RequestParam MultipartFile file,
			HttpSession httpsession) {

		HashMap<String, String> map = politicianInfoServiceService.uploadExcelSheet(file, httpsession);
		if (map != null)
			return new ResponseEntity<HashMap<String, String>>(map, HttpStatus.OK);
		else
			throw new PoliticianAlreadyExistException("PoliticianAlreadyExistException");

	}

//3//
	@GetMapping(value = "/getAllPolticianInfo")
	public ResponseEntity<List<PoliticianInfo>> getAllPolticianInfo() {

		List<PoliticianInfo> listOfPolitician = politicianInfoServiceService.getAllPolticianInfo();
		if (listOfPolitician != null)
			return new ResponseEntity<List<PoliticianInfo>>(listOfPolitician, HttpStatus.OK);
		else {

			throw new PoliticiansNotFoundException("Politicians not present");
		}

	}

//4//
	@GetMapping(value = "/getPolitcianInfoByName")

	public ResponseEntity<PoliticianInfo> getPolitcianInfoByName(@RequestParam String politicianName) {

		PoliticianInfo politcianInfoByName = politicianInfoServiceService.getPolitcianInfoByName(politicianName);

		if (politcianInfoByName != null)
			return new ResponseEntity<PoliticianInfo>(politcianInfoByName, HttpStatus.OK);
		else {

			throw new PoliticiansNotFoundException("Politician information not present");
		}
	}

	@GetMapping(value = "/countByProjections")

	public ResponseEntity<List<PoliticianInfo>> countByProjections() {

		List<PoliticianInfo> countByProjections = politicianInfoServiceService.countByProjections();

		if (countByProjections != null) {
			return new ResponseEntity<List<PoliticianInfo>>(countByProjections, HttpStatus.OK);
		} else {

			throw new PoliticiansNotFoundException("Politician information not present");
		}
	}

//5//
	@GetMapping(value = "/countOfPoliticians")

	public ResponseEntity<Integer> countOfPoliticians() {

		int countOfPoliticians = politicianInfoServiceService.countOfPoliticians();

		if (countOfPoliticians >= 0) {
			return new ResponseEntity<Integer>(countOfPoliticians, HttpStatus.OK);
		} else {

			throw new PoliticiansNotFoundException("Politician information not present");

		}
	}

	@GetMapping(value = "/sortByCriteria")

	public ResponseEntity<List<PoliticianInfo>> sortByCriteria() {
		List<PoliticianInfo> sortByCriteria = politicianInfoServiceService.sortByCriteria();
		if (sortByCriteria != null) {
			return new ResponseEntity<List<PoliticianInfo>>(sortByCriteria, HttpStatus.OK);
		} else {

			throw new PoliticiansNotFoundException("Politician information not present");
		}
	}

//6//
	@GetMapping(value = "/sortBy")
	public ResponseEntity<List<PoliticianInfo>> sortBy(@RequestParam String sortBy) {
		List<PoliticianInfo> sortBy2 = politicianInfoServiceService.sortBy(sortBy);

		if (!sortBy2.isEmpty()) {
			return new ResponseEntity<List<PoliticianInfo>>(sortBy2, HttpStatus.OK);
		} else {

			throw new PoliticiansNotFoundException("Politician not exist");
		}
	}

//7//
	@GetMapping(value = "/sortByDsc")
	public ResponseEntity<List<PoliticianInfo>> sortByDsc(@RequestParam String sortByDsc) {
		List<PoliticianInfo> sortByDscAny = politicianInfoServiceService.sortByDsc(sortByDsc);

		if (!sortByDscAny.isEmpty()) {
			return new ResponseEntity<List<PoliticianInfo>>(sortByDscAny, HttpStatus.OK);
		} else {

			throw new PoliticiansNotFoundException("Politician not exist");

		}
	}

//8//
	@PutMapping(value = "/updatePoliticianVotes")
	public ResponseEntity<Boolean> updatePoliticianVotes(@RequestBody PoliticianInfo politicianInfo) {
		boolean updatePoliticianVotes = politicianInfoServiceService.updatePoliticianVotes(politicianInfo);

		if (updatePoliticianVotes) {
			return new ResponseEntity<Boolean>(updatePoliticianVotes, HttpStatus.OK);
		} else {

			throw new MoreThanOneVoteAtATimeNotAllowed("Politician nam");

		}
	}

//9//
	@GetMapping(value = "/maxVotesOfPolitician/{electionArea}")
	public ResponseEntity<PoliticianInfo> maxVotesOfPolitician(@PathVariable String electionArea) {
		PoliticianInfo maxVotesOfPolitician = politicianInfoServiceService.maxVotesOfPolitician(electionArea);

		if (maxVotesOfPolitician != null) {
			return new ResponseEntity<PoliticianInfo>(maxVotesOfPolitician, HttpStatus.OK);
		} else {

			throw new PoliticiansNotFoundException("Politician not exist");
		}
	}

//10//
	@GetMapping(value = "/getcountofMlaByParty")
	public ResponseEntity<Integer> getcountOfMlaByParty(@RequestParam String nameOfParty) {
		int getcountofMlaByParty = politicianInfoServiceService.getcountOfMlaByParty(nameOfParty);
		if (getcountofMlaByParty >= 0) {
			return new ResponseEntity<Integer>(getcountofMlaByParty, HttpStatus.OK);
		} else {

			throw new PoliticiansNotFoundException("PoliticiansNotFoundException");
		}

	}

//11//
	@GetMapping(value = "/getListofPoliticainByPartyName")
	public ResponseEntity<List<PoliticianInfo>> getListofPoliticainByPartyName(@RequestParam String partyName) {
		List<PoliticianInfo> listofPoliticainByPartyName = politicianInfoServiceService
				.getListofPoliticainByPartyName(partyName);
		if (!listofPoliticainByPartyName.isEmpty()) {

			return new ResponseEntity<List<PoliticianInfo>>(listofPoliticainByPartyName, HttpStatus.OK);
		} else {

			throw new PoliticiansNotFoundException("Something went wrong check credentials input");

		}
	}

//12//
	@DeleteMapping(value = "/deletePoliticianRecord/{politicianName}")
	public ResponseEntity<Boolean> deletePoliticianRecord(@PathVariable String politicianName) {
		boolean deletePoliticianRecord = politicianInfoServiceService.deletePoliticianRecord(politicianName);

		if (deletePoliticianRecord) {

			return new ResponseEntity<Boolean>(deletePoliticianRecord, HttpStatus.OK);
		} else {

			throw new PoliticiansNotFoundException("Something went wrong check credentials input");
		}
	}

//13//
	@PutMapping(value = "/updatVotesByVoter")
	public ResponseEntity<Boolean> updatVotesByVoter(@RequestBody VoterInputDetails voterInput) {
		boolean updatVotesByVoter = politicianInfoServiceService.updatVotesByVoter(voterInput);

		if (updatVotesByVoter) {
			return new ResponseEntity<Boolean>(updatVotesByVoter, HttpStatus.OK);
		} else {

			throw new PoliticiansNotFoundException(voterInput.getPoliticianName() + "Politician details not found ");
		}
	}

}
