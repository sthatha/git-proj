package com.rbank.stmtprocessor.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rbank.stmtprocessor.beans.StmtRecord;

@Service
public class StmtService {

	String[] HEADERS = { "Reference Number", "Description" };

	public List<StmtRecord> extractExcel(MultipartFile inputFile) throws IOException {

		List<StmtRecord> processedRecords = new ArrayList<>();

		InputStream inputStream = inputFile.getInputStream();
		CSVParser csvparser = null;

		try {
			csvparser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT.withFirstRecordAsHeader()
					.withIgnoreHeaderCase().withSkipHeaderRecord().withTrim());
			for (CSVRecord csvRecord : csvparser) {
				StmtRecord stmtRecord = new StmtRecord();
				stmtRecord.setTransRefNumber(Long.valueOf(csvRecord.get(0)));
				stmtRecord.setAcctNumber(csvRecord.get(1));
				stmtRecord.setDescription(csvRecord.get(2));
				stmtRecord.setStartBalance(new BigDecimal(csvRecord.get(3)));
				stmtRecord.setMutation(new BigDecimal(csvRecord.get(4)));
				stmtRecord.setEndBalance(new BigDecimal(csvRecord.get(5)));
				processedRecords.add(stmtRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			csvparser.close();
		}
		return processedRecords;
	}

	public void processRecords(List<StmtRecord> inputRecords) {

		Set<Long> temp = new HashSet<>();
		Set<Long> duplicates = inputRecords.stream().map(record -> record.getTransRefNumber())
				.filter(record -> !temp.add(record)).collect(Collectors.toSet());
		inputRecords.parallelStream().forEach(record -> {
			if (record.getStartBalance().add(record.getMutation()).compareTo(record.getEndBalance()) != 0
					|| duplicates.contains(record.getTransRefNumber())) {
				record.setHasError(true);
			}
		});
	}

	public void generateResult(List<StmtRecord> inputRecords, OutputStream outputStream) throws IOException {
		try (CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(outputStream),
				CSVFormat.DEFAULT.withHeader(HEADERS))) {
			inputRecords.stream().filter(StmtRecord::isHasError).forEach(record -> {
				try {
					printer.printRecord(record.getTransRefNumber(), record.getDescription());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
