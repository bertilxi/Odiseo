package app.comun;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Encargada de la encriptación de contraseñas
 */
public abstract class EncriptadorPassword {

	private final static Integer ITERATIONS = 10000;
	private final static Integer KEY_LENGTH = 256;
	private final static String SAL_GLOBAL = "G��vh�{|Xз�m�Ũ`h";

	/**
	 * Encripta un String con el algoritmo MD5.
	 *
	 * @param palabra
	 *            palabra a encriptar, se borrará al terminar.
	 * @param sal
	 *            sal para ocultar la palabra.
	 * @return String
	 */
	public static String encriptarMD5(char[] palabra, String sal) {
		return new String(hashPassword(palabra, (sal + SAL_GLOBAL).getBytes(), ITERATIONS, KEY_LENGTH));
	}

	private static byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength) {
		try{
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
			SecretKey key = skf.generateSecret(spec);
			byte[] res = key.getEncoded();
			Arrays.fill(password, '\u0000'); // clear sensitive data
			return res;
		} catch(NoSuchAlgorithmException | InvalidKeySpecException e){
			throw new RuntimeException(e);
		}
	}

	public static String generarSal() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		return new String(bytes);
	}
}