package com.Indianpolitics.politician_info.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.Indianpolitics.politician_info.dao.PoliticianInfoDao;
import com.Indianpolitics.politician_info.entity.PoliticianInfo;
import com.Indianpolitics.politician_info.entity.VoterInputDetails;
import com.Indianpolitics.politician_info.validation.Validation;

@Service
public class PoliticianInfoServiceImpl implements PoliticianInfoService {

	String excludedRow;
	int totalRecordCount = 0;
	@Autowired
	private PoliticianInfoDao politicianInfoDaodao;

	@Override
	public boolean savePolticianInfo(PoliticianInfo politicianInfo) {
		boolean savePolticianInfo = politicianInfoDaodao.savePolticianInfo(politicianInfo);
		return savePolticianInfo;
	}

	// upload data by excel sheet in database

	public List<PoliticianInfo> readExcelfile(String filepath) {

		// for read file
		FileInputStream fileInputStream = null;
		List<PoliticianInfo> listOfPoliticianInfo = new ArrayList<PoliticianInfo>();
		PoliticianInfo politicianInfo = null;
		Workbook workbook = null;
		try {
			fileInputStream = new FileInputStream(new File(filepath));
			// flow be like workbook -> sheet-> rows->cell
			workbook = new XSSFWorkbook(fileInputStream);

			Sheet sheetAt = workbook.getSheetAt(0);
			totalRecordCount = sheetAt.getLastRowNum();
			Iterator<Row> rows = sheetAt.rowIterator();
			int rowcount = 0;
			while (rows.hasNext()) {
				Row row = rows.next();

				if (rowcount == 0) {
					rowcount++;
					continue;
				}
				politicianInfo = new PoliticianInfo();

//				Thread.sleep(1); // need to slow processing due to same id generation
//
//				String Id = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new java.util.Date());
//				politicianInfo.setPoliticianId(Id);

				Iterator<Cell> cells = row.cellIterator();
				// cells -> cell (single box)
				while (cells.hasNext()) {
					Cell cell = cells.next();

					int columnindex = cell.getColumnIndex();
					switch (columnindex) {
					case 0:
						politicianInfo.setPoliticianName(cell.getStringCellValue());

						break;
					case 1:
						politicianInfo.setElectionArea(cell.getStringCellValue());

						break;
					case 2:
						politicianInfo.setPoliticianParty(cell.getStringCellValue());

						break;
					case 3:
						politicianInfo.setVotes((int) cell.getNumericCellValue());

						break;

					}
				}
				boolean vaildatiOnPoliticianInfo = Validation.vaildationPoliticianInfo(politicianInfo);
				if (vaildatiOnPoliticianInfo) {
					listOfPoliticianInfo.add(politicianInfo);

				} else {
					int rowNum = row.getRowNum() + 1;
					excludedRow = excludedRow + rowNum + ",";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return listOfPoliticianInfo;
	}

	public HashMap<String, String> uploadExcelSheet(MultipartFile file, HttpSession httpSession) {

		String path = httpSession.getServletContext().getRealPath("/");
		String fileName = file.getOriginalFilename();
		HashMap<String, String> map = new HashMap<>();

		byte[] data = null;
		try {
			data = file.getBytes();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FileOutputStream fos = null;
		try {
			System.out.println(path);
			fos = new FileOutputStream(new File(path + File.separator + fileName));
			fos.write(data);

			List<PoliticianInfo> listOfPoliticianInfo = readExcelfile(path + File.separator + fileName);
			for (PoliticianInfo politicianInfo : listOfPoliticianInfo) {
				System.out.println(listOfPoliticianInfo);

				int uploadCountOfPoliticianInfo = politicianInfoDaodao.uploadListPolticianInfo(listOfPoliticianInfo);

				System.out.println(uploadCountOfPoliticianInfo);
				map.put("Total Record In Sheet", String.valueOf(totalRecordCount));
				map.put("Uploaded Record In DB", String.valueOf(uploadCountOfPoliticianInfo));
				map.put("Bad Record Row Number", excludedRow);
				map.put("Total Excluded", String.valueOf(totalRecordCount - uploadCountOfPoliticianInfo));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;

	}

	@Override
	public List<PoliticianInfo> getAllPolticianInfo() {
		List<PoliticianInfo> allPolticianInfo = politicianInfoDaodao.getAllPolticianInfo();
		return allPolticianInfo;
	}

	public PoliticianInfo getPolitcianInfoByName(String politicianName) {

		PoliticianInfo politcianInfoByName = politicianInfoDaodao.getPolitcianInfoByName(politicianName);

		return politcianInfoByName;

	}

	public List <PoliticianInfo> countByProjections(){
		List<PoliticianInfo> countByProjections = politicianInfoDaodao.countByProjections();
		
		return countByProjections;
		
	}
	
	
	public int countOfPoliticians() {

		int countOfPoliticians = politicianInfoDaodao.countOfPoliticians();
		return countOfPoliticians;
	}

	

	public List<PoliticianInfo> sortByCriteria(){
		List<PoliticianInfo> sortByCriteria = politicianInfoDaodao.sortByCriteria();
		return sortByCriteria;
		
	}
	
	
	
	
	@Override
	public List<PoliticianInfo> sortBy(String sortBy) {
		List<PoliticianInfo> sortByAny = politicianInfoDaodao.sortBy(sortBy);
		return sortByAny;
	}

	@Override
	public List<PoliticianInfo> sortByDsc(String sortByDsc) {
		List<PoliticianInfo> sortByDscAny = politicianInfoDaodao.sortByDsc(sortByDsc);
		return sortByDscAny;
	}

	@Override
	public PoliticianInfo maxVotesOfPolitician(String electionArea) {
		PoliticianInfo maxVotesOfPolitician = politicianInfoDaodao.maxVotesOfPolitician(electionArea);
		return maxVotesOfPolitician;
	}

	@Override
	public boolean updatePoliticianVotes(PoliticianInfo politicianInfo) {

		boolean updatePoliticianVotes = politicianInfoDaodao.updatePoliticianVotes(politicianInfo);
		return updatePoliticianVotes;

	}

	@Override
	public int getcountOfMlaByParty(String nameOfParty) {

		int countofMlaByParty = politicianInfoDaodao.getcountOfMlaByParty(nameOfParty);
		return countofMlaByParty;

	}

	// Api for RegionEntity

	@Override
	public List<PoliticianInfo> getListofPoliticainByPartyName(String partyName) {

		List<PoliticianInfo> listofPoliticainByPartyName = politicianInfoDaodao
				.getListofPoliticainByPartyName(partyName);
		return listofPoliticainByPartyName;
	}

	public boolean deletePoliticianRecord(String politicianName) {
		boolean deletePoliticianRecord = politicianInfoDaodao.deletePoliticianRecord(politicianName);

		return deletePoliticianRecord;

	}

	@Override
	public boolean updatVotesByVoter(VoterInputDetails voterInput) {
		boolean updatVotesByVoter = politicianInfoDaodao.updatVotesByVoter(voterInput);
		return updatVotesByVoter;
	}

	public boolean validUserDetails(String username) {
		
		
		return false;

	}
	
	

}
