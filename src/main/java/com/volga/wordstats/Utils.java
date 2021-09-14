package com.volga.wordstats;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {
	public static String getMd5Hash(String filepath) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (InputStream is = Files.newInputStream(Paths.get(filepath));
				DigestInputStream dis = new DigestInputStream(is, md)) {
			dis.readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] digest = md.digest();
		String md5hash = Base64.getEncoder().encodeToString(digest);
		
		return md5hash;
	}
}
