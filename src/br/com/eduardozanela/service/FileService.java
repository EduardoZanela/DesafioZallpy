package br.com.eduardozanela.service;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileService {
	
	private static final String FILE_EXTENTION = ".dat";
	private static final String DATA_INPUT = "\\data\\in\\";
	private static final String DATA_OUTPUT = "\\data\\out\\";
	private static final String HOMEPATH_ENV_VARIABLE = "HOMEPATH";
	
	public List<File> readFiles(){
		
		File dir = new File(System.getenv(HOMEPATH_ENV_VARIABLE).concat(DATA_INPUT));
		if (dir.exists() && dir.isDirectory()) {
			FileFilter filter = file -> file.getName().endsWith(FILE_EXTENTION);
			return Arrays.asList(dir.listFiles(filter));
		} else {
			System.err.println("Directory not found");
		}
		return new ArrayList<>();
	}

	public void createFile(String fileName, String content) throws IOException {
		Files.write(Paths.get(System.getenv(HOMEPATH_ENV_VARIABLE).concat(DATA_OUTPUT).concat(fileName).concat(".done.dat")), content.getBytes());
	}
	
	public void deleteFile(String path, String fileName) {
		try {
			Files.delete(Paths.get(path.concat(fileName)));
		} catch (IOException e) {
			System.err.println("Error to delete file: " + path.concat(fileName));
			e.printStackTrace();
		}
	}

	public void createDirectoryIfNotExists(){
		File output = new File(System.getenv(HOMEPATH_ENV_VARIABLE).concat(DATA_OUTPUT));
		if(!output.exists()) {
			output.mkdir();
		}
		File input = new File(System.getenv(HOMEPATH_ENV_VARIABLE).concat(DATA_INPUT));
		if(!input.exists()) {
			input.mkdir();
		}
	}

	public String getFileNameWithoutExtension(String fileName){
		return fileName.replaceFirst("[.][^.]+$", "");
	}
	
	public void watchFiles() {
		try(WatchService service = FileSystems.getDefault().newWatchService()){
			ReportService reportService = new ReportService();
			Path path = Paths.get(System.getenv(HOMEPATH_ENV_VARIABLE).concat(DATA_INPUT));
			
			WatchKey watchKey;
			watchKey = path.register(service, StandardWatchEventKinds.ENTRY_CREATE);

			while(true){
								
				for(WatchEvent<?> event : watchKey.pollEvents()) {
					Path file = path.resolve((Path) event.context());
					// Necessary because system not yet release the file lock and file is being created
					Thread.sleep(500L);
					reportService.generateReport(System.getenv(HOMEPATH_ENV_VARIABLE).concat(DATA_INPUT), file.getFileName().toString());
				}
			}
			
		} catch (IOException | InterruptedException e) {
			System.err.println("Error to watch files");
			e.printStackTrace();
		}
	}
}
