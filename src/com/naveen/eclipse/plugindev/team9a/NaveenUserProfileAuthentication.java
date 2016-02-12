package com.naveen.eclipse.plugindev.team9a;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
 
//TODO: Rename?  GitVizModelDAOService
public class NaveenUserProfileAuthentication {
 
	public static void main(String[] args) throws Throwable {
		/*
		String link = "https://raw.githubusercontent.com/sampsyo/beets/master/beets/ui/commands.py"; // You can change the raw url to whichever Github source code .
		URL rawUrl = new URL(link);
		HttpURLConnection rawHttp = (HttpURLConnection) rawUrl.openConnection();
		Map<String, List<String>> rawHeader = rawHttp.getHeaderFields();
 
		// If URL is getting 301 and 302 redirection HTTP code then get new URL link.
		// This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
		for (String header : rawHeader.get(null)) {
			if (header.contains(" 302 ") || header.contains(" 301 ")) {
				link = rawHeader.get("Location").get(0);
				rawUrl = new URL(link);
				rawHttp = (HttpURLConnection) rawUrl.openConnection();
				rawHeader = rawHttp.getHeaderFields();
			}
		}
		InputStream gitStream = rawHttp.getInputStream();
		String rawResponse = rawGetStringFromStream(gitStream);
		System.out.println(rawResponse);
		*/
	}
 
        // ConvertStreamToString() Utility - we name it as rawGetStringFromStream()
	private static String rawGetStringFromStream(InputStream gitStream) throws IOException {
		if (gitStream != null) {
			Writer gitWriter = new StringWriter();
 
			char[] gitBuffer = new char[2048];
			try {
				Reader gitReader = new BufferedReader(new InputStreamReader(gitStream, "UTF-8"));
				int counter;
				while ((counter = gitReader.read(gitBuffer)) != -1) {
					gitWriter.write(gitBuffer, 0, counter);
				}
			} finally {
				gitStream.close();
			}
			return gitWriter.toString();
		} else {
			return "No Contents";
		}
	}
}