package com.Indianpolitics.politician_info.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.Indianpolitics.politician_info.entity.PoliticianInfo;
import com.Indianpolitics.politician_info.entity.VoterInputDetails;

import com.Indianpolitics.politician_info.sorting.SortByElectionArea;

import com.Indianpolitics.politician_info.sorting.SortByPoliticianVotes;

@Repository
public class PoliticianInfoDaoImpl implements PoliticianInfoDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean savePolticianInfo(PoliticianInfo politicianInfo) {

		Session session = null;
		Transaction transaction = null;
		boolean addedPoliticianInfo = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			PoliticianInfo politicianInfo2 = session.get(PoliticianInfo.class, politicianInfo.getPoliticianName());

			if (politicianInfo2 == null) {
				session.save(politicianInfo);
				transaction.commit();
				addedPoliticianInfo = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return addedPoliticianInfo;
	}

	public int uploadListPolticianInfo(List<PoliticianInfo> listOfPoliticianInfo) {
		Session session = null;
		Transaction transaction = null;
		int count = 0;
		try {
			for (PoliticianInfo politicianInfo : listOfPoliticianInfo) {

				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				session.save(politicianInfo);
				transaction.commit();
				count = count + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return count;
	}

	public List<PoliticianInfo> getAllPolticianInfo() {

		Session session = sessionFactory.openSession();
		List listOFPolitician = null;
		try {
			Criteria criteria = session.createCriteria(PoliticianInfo.class);
			listOFPolitician = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listOFPolitician;
	}

	public PoliticianInfo getPolitcianInfoByName(String politicianName) {
		List<PoliticianInfo> allPolticianInfo = getAllPolticianInfo();
		Session session = sessionFactory.openSession();
		PoliticianInfo politicianInfo = null;

		try {

			politicianInfo = session.get(PoliticianInfo.class, politicianName);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return politicianInfo;

	}

	public List<PoliticianInfo> countByProjections() {
		Session session = sessionFactory.openSession();

		Criteria createCriteria = session.createCriteria(PoliticianInfo.class);
		
		Criteria setProjection = createCriteria.setProjection(Projections.count("politicianName"));
		List list = setProjection.list();
		return list;

	}

	public int countOfPoliticians() {
		Session session = sessionFactory.openSession();
		List list = null;
		int count = 0;
		try {
			Criteria createCriteria = session.createCriteria(PoliticianInfo.class);
			Collections.sort(null);
			Criteria setProjection = createCriteria.setProjection(Projections.rowCount());
			list = setProjection.list();

			List<PoliticianInfo> allPolticianInfo = getAllPolticianInfo();
			for (PoliticianInfo politicianInfo : allPolticianInfo) {
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		System.out.println(count);
		return count;
	}

	public List<PoliticianInfo> sortByCriteria() {
		Session session = sessionFactory.openSession();
		Criteria createCriteria = session.createCriteria(PoliticianInfo.class);
		// createCriteria.addOrder(Order.asc("votes"));
		createCriteria.addOrder(Order.desc("votes"));

		List list = createCriteria.list();
		return list;

	}

	@Override
	public List<PoliticianInfo> sortBy(String sortBy) {
		List<PoliticianInfo> allPolticianInfo = getAllPolticianInfo();
		Session session = sessionFactory.openSession();
		try {
////			if (sortBy.equalsIgnoreCase("votes")) {
			Collections.sort(allPolticianInfo, new SortByPoliticianVotes());

//// criteria  
//			} else if (sortBy.equalsIgnoreCase("electionArea")) {
//				Collections.sort(allPolticianInfo, new SortByElectionArea());

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

		return allPolticianInfo;
	}

	public List<PoliticianInfo> sortByDsc(String sortByDsc) {

		List<PoliticianInfo> allPolticianInfo = getAllPolticianInfo();

		try {

			if (sortByDsc.equalsIgnoreCase("electionArea")) {
				Collections.sort(allPolticianInfo, new SortByElectionArea());
				Collections.reverse(allPolticianInfo);

			} else if (sortByDsc.equalsIgnoreCase("votes")) {
				Collections.sort(allPolticianInfo, new SortByPoliticianVotes());
				Collections.reverse(allPolticianInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return allPolticianInfo;
	}

	@Override
	public PoliticianInfo maxVotesOfPolitician(String electionArea) {
		PoliticianInfo politicianInfo = null;
		Session session = null;
		List list = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(PoliticianInfo.class);
			criteria.add(Restrictions.eq("electionArea", electionArea));
			list = criteria.list();
			politicianInfo = (PoliticianInfo) list.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return politicianInfo;
	}

// update votes
	public boolean updatePoliticianVotes(PoliticianInfo politicianInfo) {
		Session session = null;
		Transaction transaction = null;
		PoliticianInfo politicianInfo1 = null;
		boolean isUpdated = false;
		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
//comming from jason
			int voteIncreased = politicianInfo.getVotes();
			politicianInfo1 = session.get(PoliticianInfo.class, politicianInfo.getPoliticianName());
//from database	

			int votes = politicianInfo1.getVotes();
			if (politicianInfo1 != null) {
				session.evict(politicianInfo1);
// because multiple session is associated 	

// updated votes			
				politicianInfo.setVotes(votes + voteIncreased);
				session.update(politicianInfo);
				transaction.commit();
				isUpdated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return isUpdated;

	}

	public boolean updatVotesByVoter(VoterInputDetails voterInput) {
		Session session = null;
		Transaction transaction = null;
		PoliticianInfo politicianInfo1 = null;

		boolean isUpdated = false;

		try {

			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			PoliticianInfo politicianInfo = session.get(PoliticianInfo.class, voterInput.getPoliticianName());

			int dataBaseVote = politicianInfo.getVotes();

			politicianInfo.setVotes(dataBaseVote + 1);

			session.update(politicianInfo);
			transaction.commit();
			isUpdated = true;

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return isUpdated;
	}

	public int getcountOfMlaByParty(String nameOfParty) {

		List<PoliticianInfo> allPolticianInfo = getAllPolticianInfo();
		Session session = sessionFactory.openSession();
		int countPerParty = 0;

		for (PoliticianInfo politicianInfo : allPolticianInfo) {

			if (politicianInfo.getPoliticianParty().equalsIgnoreCase(nameOfParty)) {
				countPerParty++;
			}
		}
		return countPerParty;
	}

	public List<PoliticianInfo> getListofPoliticainByPartyName(String partyName) {
		Session session = sessionFactory.openSession();
		List list = null;
		try {
			Criteria criteria = session.createCriteria(PoliticianInfo.class);
			Criteria criteria2 = criteria.add(Restrictions.eq("politicianParty", partyName));
			list = criteria2.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean deletePoliticianRecord(String politicianName) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		boolean isDeleted = false;
		try {
			PoliticianInfo politicianInfo = session.get(PoliticianInfo.class, politicianName);

			session.delete(politicianInfo);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return isDeleted;

	}

}