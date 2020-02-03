package br.com.eduardozanela;

import br.com.eduardozanela.service.FileService;
import br.com.eduardozanela.service.ReportService;

public class DesafioApplication {
		
	public static void main(String[] args) {
		ReportService reportService = new ReportService();
		reportService.generateReportApplicationStart();
		FileService fileService = new FileService();
		fileService.createDirectoryIfNotExists();
		fileService.watchFiles();
	}
}
