package br.com.eduardozanela;

import br.com.eduardozanela.service.FileService;

public class DesafioApplication {
		
	public static void main(String[] args) {
		FileService fileService = new FileService();
		fileService.createDirectoryIfNotExists();
		fileService.watchFiles();
	}
}
