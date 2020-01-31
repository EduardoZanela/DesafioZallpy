package br.com.eduardozanela;

import br.com.eduardozanela.service.ReportService;

public class DesafioApplication {
		
	public static void main(String[] args) {
		ReportService reportService = new ReportService();
		reportService.generateReport();
	}
}
