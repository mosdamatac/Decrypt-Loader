/**
 * Project: 3613_lab10_start
 * File: Decoder.java
 * Date: Jun 22, 2016
 * Time: 11:47:00 AM
 */
package a00973641.lab.decode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import a00973641.lab.view.LabFrame;

/**
 * @author Mara
 *
 */
public class Decoder {

	// salt for password-based encryption-decryption algorithm
	private static final byte[] salt = { (byte) 0xf5, (byte) 0x33, (byte) 0x01, (byte) 0x2a, (byte) 0xb2, (byte) 0xcc,
			(byte) 0xe4, (byte) 0x7f };

	// iteration count
	private int iterationCount = 100;

	public byte[] readAndDecrypt(String fileName, String password) {
		Cipher cipher = null;

		try {
			// create password based encryption key object
			PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
			// obtain instance for secret key factory
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			// generate secret key for encryption
			SecretKey secretKey = keyFactory.generateSecret(keySpec);
			// specify parameters used for encryption
			PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterationCount);

			// get cipher instance
			cipher = Cipher.getInstance("PBEWithMD5AndDES");
			// initialize cipher in decrypt mode
			cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
		}
		// handle NoSuchAlgorithmException
		catch (NoSuchAlgorithmException exception) {
			LabFrame.showError(exception.getMessage());
			exception.printStackTrace();
			System.exit(1);
		}

		// handle InvalidKeySpecException
		catch (InvalidKeySpecException exception) {
			LabFrame.showError(exception.getMessage());
			exception.printStackTrace();
			System.exit(1);
		}

		// handle InvalidKeyException
		catch (InvalidKeyException exception) {
			LabFrame.showError(exception.getMessage());
			exception.printStackTrace();
			System.exit(1);
		}

		// handle NoSuchPaddingException
		catch (NoSuchPaddingException exception) {
			LabFrame.showError(exception.getMessage());
			exception.printStackTrace();
			System.exit(1);
		}

		// handle InvalidAlgorithmParameterException
		catch (InvalidAlgorithmParameterException exception) {
			LabFrame.showError(exception.getMessage());
			exception.printStackTrace();
			System.exit(1);
		}

		@SuppressWarnings("rawtypes")
		Vector fileBytes = readFile(cipher, fileName);
		byte[] decryptedText = new byte[fileBytes.size()];

		for (int i = 0; i < fileBytes.size(); i++) {
			decryptedText[i] = ((Byte) fileBytes.elementAt(i)).byteValue();
		}

		return decryptedText;
	}

	/**
	 * Read byte values from the given file.
	 * 
	 * @param cipher
	 * @param fileName
	 * @return vector of bytes read from given file
	 */
	private Vector<Byte> readFile(Cipher cipher, String fileName) {
		Vector<Byte> fileBytes = new Vector<>();
		// read contents from file
		try {
			File file = new File(fileName);
			FileInputStream fileInputStream = new FileInputStream(file);
			CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);

			int ch;
			@SuppressWarnings("unused")
			int i = 0;
			while ((ch = cipherInputStream.read()) != -1) {
				byte b = (byte) (ch);
				fileBytes.add(new Byte(b));
				i++;
			}
			cipherInputStream.close();
		}
		// handle IOException
		catch (IOException exception) {
			LabFrame.showError(exception.getMessage());
			exception.printStackTrace();
			System.exit(1);
		}

		// return buffer.toByteArray();
		return fileBytes;
	}
}
