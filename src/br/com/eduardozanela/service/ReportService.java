package br.com.eduardozanela.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.eduardozanela.dto.Buyer;
import br.com.eduardozanela.dto.Item;
import br.com.eduardozanela.dto.Sell;
import br.com.eduardozanela.dto.Seller;

public class ReportService {
	
	private static final String ITEM_ARRAY_START = "[";
	private static final String ITEM_ARRAY_END = "]";
	private static final String SEPARATOR_OBJECT_ITEM = "-";
	private static final String SEPARATOR_ARRAY_ITEMS = ",";
	private static final String SEPARATOR = "ç";
	private static final String SELLER_ID = "001";
	private static final String BUYER_ID = "002";
	private static final String SELL_ID = "003";
		
	private FileService fileService;
	
	public ReportService(){
		this.fileService = new FileService();
	}
	
	public void generateReport(String path, String fileName) {
			System.out.println("Start generating report for file: " + path.concat(fileName));
			List<Seller> sellers = new ArrayList<>();
			List<Buyer> buyers = new ArrayList<>();
			List<Sell> sells = new ArrayList<>();

			FileReader fileReader = null;
			BufferedReader reader = null;
			try {
				fileReader = new FileReader(path.concat(fileName));
				reader = new BufferedReader(fileReader);
				
				String line = reader.readLine();
				while (line != null) {
					String[] splitFileLine = line.split(SEPARATOR);
					if(splitFileLine[0].equals(SELLER_ID) && splitFileLine.length >= 4) {
						sellers.add(new Seller(splitFileLine[1], splitFileLine[2], Double.parseDouble(splitFileLine[3])));
					} else if(splitFileLine[0].equals(BUYER_ID) && splitFileLine.length >= 4) {
						buyers.add(new Buyer(splitFileLine[1], splitFileLine[2], splitFileLine[3]));
					} else if(splitFileLine[0].equals(SELL_ID) && splitFileLine.length >= 4) {

						String replace = splitFileLine[2].replace(ITEM_ARRAY_START, "");
						replace = replace.replace(ITEM_ARRAY_END, "");
						String[] stringItems = replace.split(SEPARATOR_ARRAY_ITEMS);

						List<Item> items = new ArrayList<>();
						for(String stringItem : stringItems){
							String[] splitItem = stringItem.split(SEPARATOR_OBJECT_ITEM);
							items.add(new Item(Long.valueOf(splitItem[0]), Integer.valueOf(splitItem[1]), Double.valueOf(splitItem[2])));
						}
						sells.add(new Sell(splitFileLine[1], items, splitFileLine[3]));
					}
					line = reader.readLine();
				}
				this.fileService.createFile(this.fileService.getFileNameWithoutExtension(fileName), createResponse(sellers, buyers, sells));
			} catch (IOException e) {
				System.err.println("Error to generete report for file: " +path.concat(fileName));
				e.printStackTrace();
			} finally {
				 if (fileReader != null) {
			        try {
			        	fileReader.close();
			        } catch (IOException e) {
			        	System.err.println("Error to clone file reader");
			            e.printStackTrace();
			        }
			    }
				 if (reader != null) {
			        try {
			        	reader.close();
			        } catch (IOException e) {
			        	System.err.println("Error to clone buffer reader");
			            e.printStackTrace();
			        }
			    }
			}
			this.fileService.deleteFile(path, fileName);
	}

	private String createResponse(List<Seller> sellers, List<Buyer> buyers, List<Sell> sells){
		String clientsQuantity = "Quantidade de clientes no arquivo de entrada: " + buyers.size();
		String sellersQuantity = "Quantidade de vendedor no arquivo de entrada: " + sellers.size();
		Map<String, Double> collect = sells.stream().collect(Collectors.toMap(a -> a.getSaleId(), sell -> sell.getItem().stream().mapToDouble(item -> item.getPrice()).sum()));
		String saleId = collect.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
		String expensiveSale = "ID da venda mais cara: " + saleId;
		String worstSeller = "O pior vendedor: " + "";
		return clientsQuantity + "\n" + sellersQuantity + "\n" + expensiveSale + "\n" + worstSeller;
	}

	public void generateReportApplicationStart() {
		List<File> readFiles = this.fileService.readFiles();
		for(File file : readFiles) {
			String path = file.getAbsolutePath().replace(file.getName(), "");
			generateReport(path, file.getName());
		}		
	}
	
}
