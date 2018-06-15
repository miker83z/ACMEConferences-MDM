package it.unibo.soseng.mdm.util;

import java.io.BufferedReader;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Class used to handle CSV.
 * 
 * @author Davide Marchi
 *
 */
public class CSVUtils {

	private static final String DEFAULT_SEPARATOR = ",";
	
	protected String csvFile;
	protected String csvSplitBy;
	protected Integer numberOfLines;
	
	public CSVUtils() {
		
	}	
	public CSVUtils(String csvFile) {
		this.csvFile = csvFile;
		this.csvSplitBy = DEFAULT_SEPARATOR;
		this.numberOfLines = countLines();
	}
	public CSVUtils(String csvFile, String csvSplitBy) {
		this(csvFile);
		this.csvSplitBy = csvSplitBy;
		this.numberOfLines = countLines();
	}
	
	public void setCsvFile(String csvFile) {
		this.csvFile = csvFile;
	}
	public String getCsvFile() {
		return csvFile;
	}
	public void setCsvSplitBy(String csvSplitBy) {
		this.csvSplitBy = csvSplitBy;
	}
	public String getCsvSplitBy() {
		return csvSplitBy;
	}
	public Integer getNumberOfLines() {
		return numberOfLines;
	}
	
	/**
	 * Read the first line from the CSV.
	 * @return The read line.
	 */
	public String[] readLine() {
		return readLine(1);
	}	
	/**
	 * Read a specific line from the CSV
	 * @param lineNumber The line number with partner informations.
	 * @return The read line
	 */
	public String[] readLine(Integer lineNumber) {
		String line = "";
		Integer counter = 0;
		Boolean found = false;
		String[] result = null;
				
		// Try to read the file
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvFile)))) {			
			// Read until the end or until the selected line
			while ((line = br.readLine()) != null && !found) {
				// Line found
				if (counter == lineNumber-1) {
					// Save the informations
					result = line.split(csvSplitBy);
					// End of the loop
					found = true;
				} 
				else {
					counter++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Return the number of lines in the CSV file.
	 * @return Number of lines.
	 */
	protected Integer countLines() {
		Integer counter = 0;
		
		// Try to read the file
		System.out.println("PRINT-READ: " + getClass().getResource(csvFile).toString());
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvFile)))) {	
			// Read until the end
			while ((br.readLine()) != null) {
				counter++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("[ERROR] The file does not exist.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("[ERROR] Null pointer.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return counter;
	}
	
}
