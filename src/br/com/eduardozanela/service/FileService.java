package br.com.eduardozanela.service;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileService {
	
	private static final String FILE_EXTENTION = ".dat";
	private static final String DATA_INPUT = "/data/in";
	private static final String DATA_OUTPUT = "/data/out/";
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
}
