
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
//import org.apache.commons.codec.binary.Base64;
//import able.com.util.tools.fcc.StringUtil;
import java.nio.charset.Charset;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : AES256_GCM_Cipher.java
 * @Description : 클래스 설명을 기술합니다.
 * @author Windows 占쏙옙占쏙옙占?-Dhttps.protocols=TLSv1,TLSv1.1,TLSv1.2
 * @since 2020. 6. 27.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2020. 6. 27.     장성범                                     AES256_GCM         최초 생성
 * </pre>
 */

public class AES256_GCM_Cipher {

    
    static String plainText = "This is a plain text which need to be encrypted by Java AES 256 GCM Encryption Algorithm";
    
    public static final int AES_KEY_SIZE = 256;
    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;
    public static SecretKey key=null;
    public static byte[] IV=null;
    
    public static void main(String[] args) throws Exception
    {
    	HashMap hmap=new HashMap();
    	hmap.put("1",100);
    	hmap.put("2",200);
    	hmap.put("3",300);
    	hmap.put("4",400);
    	hmap.put("5",500);
    	
    	Iterator<String> keys=hmap.keySet().iterator();
    	while(keys.hasNext())
    	{
    		String keyValue=keys.next();
    		System.out.println(hmap.get(keyValue));
    	}
    	
    	System.out.println("원본"+plainText);
    	byte[]cipherText=encrypt(plainText);
    	Base64.getEncoder().encodeToString(cipherText);
    	
    	String decryptedText=decrypt(cipherText);
    	System.out.println("암호화"+cipherText);
    	System.out.println("복호화"+decryptedText);
    	
    	byte[] mapText=encryptMap(hmap);
    	System.out.println("복호화"+decryptMap(mapText));
    }
    
    public static SecretKey setKey() throws Exception{
    	KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
    	keyGenerator.init(AES_KEY_SIZE);
    	
    	key=keyGenerator.generateKey();
    	return key;
    }
    
    public static byte[] generateIV() {
    	IV=new byte[GCM_IV_LENGTH];
    	SecureRandom random=new SecureRandom();
    	random.nextBytes(IV);
    	return IV;
    }
    
    public static byte[] encrypt(String plainText) throws Exception
    {
     
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        
       
        SecretKey key=setKey();
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        
       
        byte[]IV=generateIV();
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
        
       
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
        
       
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        
        return cipherText;
    }

    public static String decrypt(byte[] cipherText) throws Exception
    {
     
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        
     
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        
    
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
        
      
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
        
      
        byte[] decryptedText = cipher.doFinal(cipherText);
        
        return new String(decryptedText);
    }
    
    
   
