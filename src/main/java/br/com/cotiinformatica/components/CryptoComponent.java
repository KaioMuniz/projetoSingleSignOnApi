package br.com.cotiinformatica.components;

import java.security.MessageDigest;

import org.springframework.stereotype.Component;

@Component
public class CryptoComponent {

	public String getSHA256(String value) {
		
		try {			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			byte[] hashBytes = digest.digest(value.getBytes("UTF-8"));
			
			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			
			return hexString.toString();			
		} catch (Exception e) {
			throw new RuntimeException("Erro ao gerar hash SHA-256", e);
		}
	}
}
