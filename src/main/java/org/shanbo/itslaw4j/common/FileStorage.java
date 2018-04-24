package org.shanbo.itslaw4j.common;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import static org.shanbo.itslaw4j.common.Constants.*;

public class FileStorage {
	
	static String INFO_FILE = "/info.txt";
	static Logger logger = LoggerFactory.getLogger(FileStorage.class);
	static int LINES_PER_BLOCK = 100;
	String dataDir;
    String courtDir;
    String infoPath;
    int courtId;
    
    
    public FileStorage(){
    	
    }

    public void set(String dataDir, int courtId){
    	this.dataDir = dataDir;
    	this.courtId =  courtId;
    	courtDir = dataDir + "/" + courtId;
    	infoPath = courtDir + INFO_FILE;
    }
    
	public JSONObject readInfo(){
	    if (!new File(courtDir).isDirectory() || !new File(infoPath).isFile()){
	    	return null;
	    }
	    while(true){
	        try{
	            JSONObject cc = JSONObject.parseObject(
	            					FileUtils.readFileToString(
	            						new File(infoPath), "UTF-8"));
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
    
	public void saveInfo(JSONObject info) throws IOException{
	    FileUtils.write(new File(infoPath) ,  info.toJSONString(), "UTF-8");
	}
	
	public void createInfo() throws IOException{
		new File(courtDir).mkdirs();
		if (!new File(infoPath).isFile()){
			JSONObject info = new JSONObject();
			info.putAll(Variables.DEFAULT_INFO);
			info.put(COURT_ID, courtId);
			saveInfo(info);
		}
	}
	
	public void updateInfo(JSONObject info, String next_docid, int next_area){
	    info.put(NEXT_DOC_ID,  next_docid);
	    info.put(NEXT_AREA,  next_area);
	    info.put(NEXT_INDEX,  info.getIntValue(NEXT_INDEX) + 1);
	    info.put(FINISHED_IDX,  info.getIntValue(FINISHED_IDX) + 1);;
	    info.put(NEXT_DOC_ID,  next_docid);
	    info.put("time", Calendar.getInstance().getTime().toString()) ;
	}
	
	public void saveContent(String content, int nextIndex) throws IOException{
		int blockId = ((nextIndex - 1) / LINES_PER_BLOCK);
		FileUtils.write(new File(courtDir + "/" + blockId) ,  content + "\n", "UTF-8");
	}
	
	
}
