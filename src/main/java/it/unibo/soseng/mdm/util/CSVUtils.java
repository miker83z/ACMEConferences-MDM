package it.unibo.soseng.mdm.util;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;


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
	
	/**
	 * Replace quotes to avoid errors.
	 * @param value String to check
	 * @return The corrected string
	 */
	private String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }
	
	/**
	 * Write a single line in the file following CSV format
	 * @param writer The file writer 
	 * @param value The string to write
	 * @throws IOException 
	 */
	// public void writeLine(FileWriter writer, String value) throws IOException {
	// public void writeLine(BufferedWriter writer, String value) throws IOException {	
	public void writeLine(FileOutputStream writer, String value) throws IOException {	
		StringBuilder sb = new StringBuilder();
		System.out.println(value);
		sb.append(followCVSformat(value));
		sb.append("\n");
		// writer.append(sb.toString());
		writer.write(sb.toString().getBytes());
	}
	
	/**
	 * Write multiple lines in the file following CSV format
	 * @param values The lines to write
	 * @throws IOException 
	 */
	public void writeLines(List<String> values) throws IOException {		
		 
		System.out.println("PRINT-WRITE: " + getClass().getResource(csvFile).getPath());
		FileOutputStream writer = new FileOutputStream(getClass().getResource(csvFile).getPath());
		// BufferedWriter writer = new BufferedWriter(new FileWriter(getClass().getResource(csvFile).toString()));
		// FileWriter writer = new FileWriter(getClass().getResource(csvFile).toString());
		/* PROVE DA FARE */ 

		for (String value : values) {
	        writeLine(writer, value);
		}
		writer.flush();
        writer.close();
	}
	
}
