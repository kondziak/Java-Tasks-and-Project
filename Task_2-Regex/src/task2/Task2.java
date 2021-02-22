package task2;

import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.regex.*;

public class Task2 {
	private static final String PAGE = "http://www.jaknapisac.com/jak-napisac-e-mail/";
	
	public static void main(String args[]) {
		WebDescription description = new WebDescription(PAGE);
		 try {
			description.prepareConnection();
			description.getContent();
			description.showEmails();
			description.showLinks();
			description.writeToFile(description.getUrl(), description.getHead(), description.getIP());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class WebDescription{
	
	final String LINK;
	final String emailPattern = "[a-zA-Z0-9_.-]{7,}@[a-zA-Z0-9_.-]{5,}";
	final String linkPattern = "href\\s*=\\s*(\"([^\"]*\")|('[^']*'))";
	final String head = "<head>.*</head>";
	private URL url;
	private BufferedReader br = null;
	private Pattern pattern;
	private Pattern secPattern;
	private Pattern third;
	private StringBuffer sb = new StringBuffer();
	
	public WebDescription(final String LINK) {
		this.LINK = LINK;
	}
	
	public URL getUrl() {
		return url;
	}
	
	public void prepareConnection() throws MalformedURLException, IOException{
		url = new URL(LINK);
		pattern = Pattern.compile(emailPattern);
		secPattern = Pattern.compile(linkPattern);
		third = Pattern.compile(head);
	}
	
	public void getContent() throws IOException {
		String line = null;
		br = new BufferedReader(new InputStreamReader(url.openStream()));
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		if(br != null) {
			br.close();
		}
	}

	public String getHead(){
		Matcher matcher = third.matcher(sb.toString());
		StringBuffer buff = new StringBuffer();
		while(matcher.find()){
			buff.append(matcher.group().toString());
		}
		return buff.toString();
	}

	public String getIP() throws UnknownHostException {
		InetAddress ia = InetAddress.getByName(url.getHost());
		String temp = ia.toString();
		String IP = temp.substring(temp.indexOf("/")+1,temp.length());
		return IP;
	}
	
	public void showEmails() {
		Matcher matcher = pattern.matcher(sb.toString());
		while(matcher.find()){
			System.out.println(matcher.group().toString());
		}
	}
	
	public void showLinks() {
		Matcher matcher = secPattern.matcher(sb.toString());
		while(matcher.find()){
			System.out.println(matcher.group().toString());
		}
	}
	
	public void writeToFile(URL info,String content, String IP) throws IOException {
		FileWriter myFile = null;
		myFile = new FileWriter("data.txt");
		myFile.write(info.getPath() + " " + info.getHost() + " " + info.getPort() + " " + info.getDefaultPort()+
				" " + info.getProtocol() + " " + info.getQuery() + " " +info.getFile()+ " "+ info.getRef()+ "\n");
		myFile.write(content + "\n" + IP);
		if(myFile != null) {
			myFile.close();
		}
	}	
}