package br.com.eduardozanela.service;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileService {
	
	private static final String FILE_EXTENTION = ".dat";
	private static final String DATA_INPUT = "\\data\\in";
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
}
