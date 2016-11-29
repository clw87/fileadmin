package com.chang.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.dic.Dictionary;

import com.chang.entity.FileEntity;

/***************************************************************
 * 
 * 通用算法集合，AlgorithmUtils
 * 
 * @author changliwei
 *
 **************************************************************/
public class AlgorithmUtils {
	
	/**
     * 全角字符串转换半角字符串
     * 
     * @param fullWidthStr　非空的全角字符串
     * @return 半角字符串
     */
    public static String fullWidth2halfWidth(String fullWidthStr) {
        if (null == fullWidthStr || fullWidthStr.length() <= 0) {
            return "";
        }
        char[] charArray = fullWidthStr.toCharArray();
        //对全角字符转换的char数组遍历
        for (int i = 0; i < charArray.length; ++i) {
            int charIntValue = (int) charArray[i];
            //如果符合转换关系,将对应下标之间减掉偏移量65248;如果是空格的话,直接做转换
            if (charIntValue >= 65281 && charIntValue <= 65374) {
                charArray[i] = (char) (charIntValue - 65248);
            } else if (charIntValue == 12288) {
                charArray[i] = (char) 32;
            }
        }
        return new String(charArray);
    }
    
    /**
     * 编辑距离，计算两个
     * 字符串的相似度
     * 
     * @param src
     * @param tag
     * @return
     */
    public static int editDistance(String src, String tag){
    	int d[][];  
        int sLen = src.length();  
        int tLen = tag.length();  
        int si;   
        int ti;   
        char ch1;  
        char ch2;  
        int cost;  
        if(sLen == 0) {  
            return tLen;  
        }  
        if(tLen == 0) {  
            return sLen;  
        }  
        d = new int[sLen+1][tLen+1];  
        for(si=0; si<=sLen; si++) {  
            d[si][0] = si;  
        }  
        for(ti=0; ti<=tLen; ti++) {  
            d[0][ti] = ti;  
        }  
        for(si=1; si<=sLen; si++) {  
            ch1 = src.charAt(si-1);  
            for(ti=1; ti<=tLen; ti++) {  
                ch2 = tag.charAt(ti-1);
                if(ch1 == ch2) {
                    cost = 0;
                } else {
                    cost = 1;
                }
                d[si][ti] = Math.min(Math.min(d[si-1][ti]+1, d[si][ti-1]+1), d[si-1][ti-1]+cost);
            }  
        }  
        return d[sLen][tLen];
    }
    
    /**
	 * 判断字符串是否是全部是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if(str == null || str.trim().equals("")){
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 将传入的字符串进行MD5算法加密
	 * 
	 * @param anyStr
	 * @return
	 */
	public static String getMD5(String anyStr) {
		if(null == anyStr){
			return "";
		}
		String md5_value = "";
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(anyStr.getBytes());
			byte tmp[] = md.digest(); 
			char str[] = new char[16 * 2]; 
			int k = 0; 
			for (int i = 0; i < 16; i++) { 
				byte byte0 = tmp[i]; 
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
				str[k++] = hexDigits[byte0 & 0xf]; 
			}
			md5_value = new String(str); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5_value;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
		/** 
	    * 将时间转换为时间戳
	    */    
	    public static String dateToStamp(String s) throws ParseException{
	        String res;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	        Date date = simpleDateFormat.parse(s);
	        long ts = date.getTime();
	        res = String.valueOf(ts);
	        return res;
	    }


	    /** 
	    * 将时间戳转换为时间
	    */
	    public static String stampToDate(String s){
	        String res;
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	        long lt = new Long(s);
	        Date date = new Date(lt);
	        res = simpleDateFormat.format(date);
	        return res;
	    }
	    
	    public static List<String> getIKAnalyzerResult(String originTxt, boolean useSmart, Collection<String> words)throws Exception{
			if(originTxt == null || originTxt.trim().equals("")){
				return null;
			}
			
			//如下代码为动态增加新词的办法，可以完善成动态加载的接口
			if(null != words && words.size() != 0){
				Configuration cfg = DefaultConfig.getInstance();
				Dictionary dic = Dictionary.initial(cfg);
				dic = Dictionary.getSingleton();
				dic.addWords(words);
			}
			
			InputStream in = new ByteArrayInputStream(originTxt.getBytes());
			IKSegmenter ik = new IKSegmenter(new InputStreamReader(in), useSmart);
			
			List<String> result = new ArrayList<String>();
			Lexeme t = null;
			while( (t=ik.next()) != null){
				result.add(t.getLexemeText());
			}
			return result;
		}
	    
	    /**
	     * 递归类
	     * 
	     * @author chang
	     *
	     */
	    private static class Recursion{
	    	
	    	/**
	    	 * 递归获取目录下的文件名字列表
	    	 * @param path 要递归的目录或者文件路径
	    	 * @param isSimpleName 是否仅返回文件名字，否的话返回绝对路径
	    	 * @return
	    	 */
	    	private static List<FileEntity> getFileNameList(String path){
	    		List<FileEntity> result = new ArrayList<FileEntity>();
	    		if(null == path || path.trim().length() == 0){
	    			return result;
	    		}
	    		
	    		File originDir = new File(path);
	    		if(originDir.isDirectory()){
	    			if(originDir.listFiles() == null ){
	    				return result;
	    			}
	    			List<File> filePath = (List<File>) Arrays.asList(originDir.listFiles());
	    			if(null == filePath || filePath .size() == 0){
	    				return result;
	    			}
	    			for(File p : filePath){
	    				if(null == p){
	    					continue;
	    				}
	    				List<FileEntity> tempResult = getFileNameList(p.getAbsolutePath());
	    				if(tempResult != null && tempResult.size() > 0){
	    					result.addAll(tempResult);
	    				}
	    			}
	    		}else if(originDir.isFile()){
	    			FileEntity entity = new FileEntity();
	    			entity.setFullName(originDir.getAbsolutePath());
	    			entity.setName(originDir.getName());
	    			entity.setId(getMD5(originDir.getName()));
	    			if(entity.getName().lastIndexOf(".") == -1){
	    				entity.setExtension("");
	    			}else{
	    				entity.setExtension(entity.getName().substring(entity.getName().lastIndexOf(".")+1));
	    				entity.setName(entity.getName().substring(0, entity.getName().lastIndexOf(".")));
	    			}
	    			entity.setLastModify(Long.parseLong( AlgorithmUtils.stampToDate(String.valueOf(originDir.lastModified()) ) ) );
	    			result.add(entity);
	    		}
	    		
	    		return result;
	    	}
    	}
	    public static List<FileEntity> recursionFileNameForDir(String dir){
	    	List<FileEntity> result = Recursion.getFileNameList(dir);
	    	return result;
	    }
    
	
//    public static void main(String[] args) throws Exception{
//    	System.out.println("原字符串: " + "１２６６５中华ｓｙｓｔｅｍ。．");
//    	System.out.println("转换后半角字符: " + fullWidth2halfWidth("１２６６５中华ｓｙｓｔｅｍ。．"));
//    	
//    	System.out.println(editDistance("abcd", "efga"));
//    	System.out.println(getIKAnalyzerResult("万科和王石是什么关系,万科是不是真的是王石的", true));
//    	System.out.println(getIKAnalyzerResult("万科和王石是什么关系,万科是不是真的是王石的", false));
//    }
    
}
