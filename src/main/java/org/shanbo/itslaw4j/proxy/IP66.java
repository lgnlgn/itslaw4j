package org.shanbo.itslaw4j.proxy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shanbo.itslaw4j.common.Constants;
import org.shanbo.itslaw4j.common.Variables;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.PageModelPipeline;


@TargetUrl("http://www.66ip.cn/areaindex_\\d+/1.html")
public class IP66 {

	@ExtractBy(value = "//TABLE[@width='100%']", notNull = true)
	String table;
	
	public static class MyPipeline implements PageModelPipeline<IP66>{

		Pattern p = Pattern.compile("<td>(\\d+\\.\\d+\\.\\d+\\.\\d+)</td>\\s*?<td>(\\d+)</td>");
		public MyPipeline(String dir){
			path = Variables.getDateDir() + Constants.PROXY_FILE;
		}
		
		String path;
		
		@Override
		public void process(IP66 t, Task task) {
			Matcher matcher = p.matcher(t.table);
			BufferedWriter bWriter = null;
			try {
				bWriter = new BufferedWriter(new FileWriter(path, true));
				while(matcher.find()){
					String host = matcher.group(1);
					String port = matcher.group(2);
					try {
						bWriter.write(host + ":" + port + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				bWriter.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			
		}
		
	}
	
	

}
