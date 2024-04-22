package com.example.social_network.controller;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:8080")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/report")
public class ReportController {

	private final ReportService reportService;

	@GetMapping()
	@Operation(summary = "API dowload report")
	@ApiResponse(responseCode = "200", description = "dowload report successfully")
	@ApiResponse(responseCode = "400", description = "dowload report error")
	public ResponseEntity<Resource> getFile() {
		String filename = "weeklyReport.xlsx";
		InputStreamResource file = new InputStreamResource(reportService.load());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
}
