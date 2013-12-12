package pporan.maven.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Utils {

	private String prefix = "etribe";
	
	public byte[] getHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		password = prefix+password;
		return digest.digest(password.getBytes("UTF-8"));
	}
	
	public byte[] getHash(int iterationNb, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		password = prefix+password;
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		for(int i=0; i < iterationNb; i++){
			digest.reset();
			input = digest.digest(input);
		}
		return input;
	}
	
}
