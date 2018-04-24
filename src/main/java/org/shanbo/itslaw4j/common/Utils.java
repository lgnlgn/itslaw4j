package org.shanbo.itslaw4j.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class Utils {
	
	static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static JSONObject readInfo(String dataDir, int courtId){
	    String courtDir = dataDir + "/" + courtId;
	    String infoPath = courtDir + "/info.txt";
	    if (!new File(courtDir).isDirectory() || !new File(infoPath).isFile()){
	    	return null;
	    }

	    while(true){
	        try{
	            BufferedReader reader = new BufferedReader(new FileReader(infoPath));
	            JSONObject cc = JSONObject.parseObject(reader.readLine());
	            reader.close();
	            return cc;
	        }catch(IOException e){
	        	logger.warn("read info error!", e);
	            try {
					Thread.sleep(32);
				} catch (InterruptedException e1) {
				}
	        }
	    }
	}
	
	public static void createInfo(String dataDir, int courtId){
	    String courtDir = dataDir + "/" + courtId;
	    String infoPath = courtDir + "/info.txt";

	}
}
