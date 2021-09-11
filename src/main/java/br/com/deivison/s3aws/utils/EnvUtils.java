package br.com.deivison.s3aws.utils;

import java.util.Map;

public class EnvUtils {
	
	private EnvUtils() {}
	
	public static Map<String, String> getEnv() {
		return System.getenv();
	}
}
