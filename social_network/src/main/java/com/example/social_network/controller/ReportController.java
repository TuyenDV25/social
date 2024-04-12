package com.example.social_network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.service.ReportService;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("api/v1/report")
public class ReportController {

	@Autowired
	ReportService reportService;

	@GetMapping("/download")
	public ResponseEntity<Resource> getFile() {
		String filename = "weeklyReport.xlsx";
		InputStreamResource file = new InputStreamResource(reportService.load());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
}
