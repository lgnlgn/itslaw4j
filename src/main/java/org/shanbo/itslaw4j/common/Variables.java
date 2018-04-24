package org.shanbo.itslaw4j.common;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import static org.shanbo.itslaw4j.common.Constants.*;
import com.alibaba.fastjson.JSONObject;

public class Variables {

	private static Options options = new Options();
	static CommandLineParser parser;
	static JSONObject DEFAULT_INFO = new JSONObject();
	static String usage = "[-y <year>][-t <caseType>][-j <judgeType>][-d [dir]][-s [courtStart]][-e [courtEnd]][-v]";
	
	static int year = 2015;
	static int judgeType =1;
	static int caseType = 2;
	static int courtStart = 1;
	static int courtEnd = 3568;
	static int interval = 1000;
	static boolean shutdown = false;
	static String dataDir = "./";
	static boolean verbose = false;
	
	static {
		initOptions();
	}
	
	
	static void initOptions(){
		options.addOption( "y", "start", true, "set year, e.g. 2015" );
		options.addOption( "s", "start", true, "set court_id STARTS from max(COURT_START, already_done), [default = 1]" );
		options.addOption( "e", "end", true, "set court_id ENDS from , [default = 3568]" );
		options.addOption( "t", "case", true, "set caseType, in [1,2,3,4], 民事.刑事.行政.执行." );
		options.addOption( "j", "judge", true, "set judgeType, in [1,2,3,4,5], 判决.裁定.通知.决定.调解" );
		options.addOption( "d", "dir", true, "data directory for saving [default = .]" );
		options.addOption( "i", "interval", true, "set crawling INTERVAL ms , [default = 1000]" );
		options.addOption( "v", "start", false, "set it to print crawling detail" );
		options.addOption( "p", "shutdown", false, "set it to poweroff whether task finished or error occurs" );
		options.addOption( "h", "help", false, "show help message" );
	}
	
	static void initInfo(){
		DEFAULT_INFO.put(COURT_ID, 0);
		DEFAULT_INFO.put(TOTAL_COUNT, -1);
		DEFAULT_INFO.put(FINISHED_IDX, 0);
		DEFAULT_INFO.put(NEXT_AREA, 0);
		DEFAULT_INFO.put(NEXT_INDEX, 0);
		DEFAULT_INFO.put(NEXT_DOC_ID, "");
	}
	
	public static void parseArgs(String[] args){
		parser = new PosixParser();
		HelpFormatter formatter = new HelpFormatter();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("help")){
				formatter.printHelp(usage, options );
			}else{
				verbose = cmd.hasOption('v');
				shutdown = cmd.hasOption('p');
				dataDir = cmd.getOptionValue('d', ".");  
				if (!cmd.hasOption('y') ){
					throw new RuntimeException("missing argument: YEAR ");
				}

				
				year = Integer.parseInt(cmd.getOptionValue('y'));  
				courtStart = Integer.parseInt(cmd.getOptionValue('s', "1"));  
				courtEnd = Integer.parseInt(cmd.getOptionValue('e', "3568"));  
				interval = Integer.parseInt(cmd.getOptionValue('e', "1000")); 
				
				String caseType = cmd.getOptionValue('t');
				String judgeType = cmd.getOptionValue('j');
				if (caseType == null || !"1-2-3-4-".contains(caseType)){
					throw new RuntimeException("caseType must be an integer and between [1,4]; got " + caseType);
				}
				if (judgeType == null || !"1-2-3-4-5".contains(judgeType)){
					throw new RuntimeException("judgeType must be an integer and between [1,5]; got " + judgeType);
				}
				if (courtStart < 0 || courtStart > courtEnd || courtEnd > 3568){
					throw new RuntimeException("Make sure courtStart < courtEnd and between [1,3568]");
				}
			}
			
			
			
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp(usage, options );
		}
	
	}
	
	
	
	public static void main(String[] args) {
		args = new String[]{"-s", "2", "-y", "2011", "-t", "3", "-j", "1"};
		Variables.parseArgs(args);
	}
	
	public static String getDateDir(){
		return dataDir;
	}
	
}
