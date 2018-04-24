package org.shanbo.itslaw4j.request;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

public class WebBrower {
	static String detail_url_tpl = "https://www.itslaw.com/api/v1/detail?timestamp=1505136306015&judgementId=$0&area=$6&sortType=1&conditions=court%2B$5%2B5%2CcaseType%2B$4%2B10%2CtrialYear%2B$3%2B7%2CjudgementType%2B$7%2B9";;
	static String detail_ref_tpl = "https://www.itslaw.com/detail?judgementId=$0&area=$6&index=$1&count=$2&sortType=1&conditions=trialYear%2B$3%2B7&conditions=caseType%2B$4%2B10&conditions=court%2B$5%2B5%2CjudgementType%2B$7%2B9";

	static String case_url_tpl = "https://www.itslaw.com/api/v1/caseFiles?startIndex=0&countPerPage=2&sortType=1&conditions=trialYear%2B$0%2B7%2B$0&conditions=caseType%2B$1%2B10%2Bxxxxxx&conditions=judgementType%2B$2%2B9%2Bzzzzzzz";
	static String case_ref_tpl = "https://www.itslaw.com/search?searchMode=judgements&sortType=1&conditions=trialYear%2B$0%2B7%2B$0&conditions=caseType%2B$1%2B10%2Bxxxxxx&conditions=judgementType%2B$2%2B9%2Bzzzzzzz";

	static String list_url_tpl = case_url_tpl + "&conditions=court%2B$3%2B5%2Bxxxxxxxxx";
	static String list_ref_tpl = case_ref_tpl + "&conditions=court%2B$3%2B5%2Bxxxxxxxxx&searchView=text";

	WebClient client;
	public WebBrower(){
		client = new WebClient(BrowserVersion.CHROME);
		client.getOptions().setActiveXNative(false);
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		
	}
}
