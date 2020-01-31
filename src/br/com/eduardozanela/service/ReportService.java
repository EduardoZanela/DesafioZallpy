package br.com.eduardozanela.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.eduardozanela.dto.Seller;

public class ReportService {
	
	private static final String SEPARATOR = "ç";
	private static final String SELLER_ID = "001";
	private static final String BUYER_ID = "002";
	private static final String SELL_ID = "003";
		
	private FileService fileService;
	
	public ReportService(){
		this.fileService = new FileService();
	}
	
	public void generateReport() {
		List<File> readFiles = this.fileService.readFiles();
		List<Seller> seller = new ArrayList<>();
		for(File file : readFiles) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
				String line = reader.readLine();
				while (line != null) {
					String[] splitItem = line.split(SEPARATOR);
					if(splitItem[0].equals(SELLER_ID) && splitItem.length > 4) {
						seller.add(new Seller(splitItem[1], splitItem[2], Double.parseDouble(splitItem[3])));
					}
					System.out.println(line);					
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
