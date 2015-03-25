package utils;

import java.io.File;

public class Validator {
	
	public static Boolean data_file(File f){
		return f.exists() && f.canRead();
	}
}
