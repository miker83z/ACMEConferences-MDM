package it.unibo.soseng.mdm.acme;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.soseng.mdm.acme.venue.model.Address;
import it.unibo.soseng.mdm.acme.venue.model.PartnerData;
import it.unibo.soseng.mdm.acme.venue.model.PartnerDatas;

class TestCSVUtils {
	
	private static final String CSV_FILENAME = "/data/test-partner-list.csv";
	private static final String CSV_SPLIT_BY = ";";

	@Test
	void testWriteLine() {
		// Partners
		PartnerDatas partnerDatas = new PartnerDatas(testRetrievePartnersList());
		
		// Update file
		try {
			partnerDatas.updatePartnersCSV(CSV_FILENAME, CSV_SPLIT_BY);
		} catch (IOException e) {
			e.printStackTrace();
			fail("failed");
		}

	}
	
	private List<PartnerData> testRetrievePartnersList() {
		List<PartnerData> partnerList = new ArrayList<>();
		// Addresses
		String[] countries = {"Italy", "Italy", "Italy"};
		String[] cities = {"Ferrara", "Bologna", "Imola"};
		String[] streets = {"Via Garibaldi, 1", "Via Zamboni, 2", "Via Marchesini, 3"};
		String[] postalCodes = {"11111", "22222", "33333"};
		
		// Partners
		String[] names = {"Dreaming solutions", "Hotel Venezia", "Tiffany"};
		String[] types = {"Manor", "Hotel", "Bar"};
		String[] emails = {"dreaming@solutions.com", "hotel@venezia.com", "tiffany@bar.it"};
		String[] phoneNumbers = {"0532121212", "0544232323", "051343434"};
		
		// Create a list with partner informations
		for (int i = 0; i < countries.length; i++) {
			partnerList.add(
					new PartnerData(names[i], types[i], emails[i], phoneNumbers[i], 
							new Address(countries[i], cities[i], streets[i], postalCodes[i])));
		}
		
		// Set the last one as contacted
		partnerList.get(partnerList.size()-1).setContacted(true);
		
		return partnerList;
	}

}
