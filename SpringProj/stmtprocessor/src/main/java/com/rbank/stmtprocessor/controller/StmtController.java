package com.rbank.stmtprocessor.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rbank.stmtprocessor.beans.StmtRecord;
import com.rbank.stmtprocessor.service.StmtService;

@RestController
@CrossOrigin
public class StmtController {

	@Autowired
	StmtService stmtService;
		
	@RequestMapping(value = "/stmtprocess", method = RequestMethod.PUT)
	public byte[] processStatement(@RequestParam("inputFile") MultipartFile inputFile, HttpServletResponse response) {
		List<StmtRecord> processedRecords = new ArrayList<>();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			processedRecords = stmtService.extractExcel(inputFile);
			stmtService.processRecords(processedRecords);
			stmtService.generateResult(processedRecords, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}

}
