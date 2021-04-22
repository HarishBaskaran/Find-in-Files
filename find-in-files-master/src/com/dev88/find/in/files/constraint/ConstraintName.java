package com.dev88.find.in.files.constraint;

import java.io.File;
import java.util.regex.Pattern;

public class ConstraintName implements Constraint {
	private Pattern pattern;
	
	public ConstraintName(String line) {
		pattern = Pattern.compile(line);
	}

	@Override
	public boolean test(File file) {
		return pattern.matcher(file.getName()).matches();
	}
	
}
