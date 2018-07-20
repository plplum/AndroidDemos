package com.example.demos.http.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtil {
	
	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	

	/**
	 * do Get request
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String doGetByHttp(String url) throws Exception {
		URL oLocalURL = new URL(url);
		URLConnection oConnection = oLocalURL.openConnection();
		HttpURLConnection oHttpURLConnection = (HttpURLConnection) oConnection;
		oHttpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
		oHttpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		oHttpURLConnection.connect();
		InputStream oInputStream = null;
		InputStreamReader oInputStreamReader = null;
		BufferedReader oReader = null;
		StringBuffer sbResultBuffer = new StringBuffer();
		String sTempLine = null;
		if (oHttpURLConnection.getResponseCode() >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + oHttpURLConnection.getResponseCode());
		}
		try {
			oInputStream = oHttpURLConnection.getInputStream();
			oInputStreamReader = new InputStreamReader(oInputStream, "UTF-8");
			oReader = new BufferedReader(oInputStreamReader);
			while ((sTempLine = oReader.readLine()) != null) {
				sbResultBuffer.append(sTempLine);
			}
		} finally {
			if (oReader != null) {
				oReader.close();
			}
			if (oInputStreamReader != null) {
				oInputStreamReader.close();
			}
			if (oInputStream != null) {
				oInputStream.close();
			}
		}
		String sMessage = sbResultBuffer.toString();
		return sMessage;
	}

	
	
	/**
	 * do Get request
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream getInputStream(String url) throws Exception {
		URL oLocalURL = new URL(url);
		URLConnection oConnection = oLocalURL.openConnection();
		HttpURLConnection oHttpURLConnection = (HttpURLConnection) oConnection;
		oHttpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
		oHttpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		oHttpURLConnection.connect();
		InputStream oInputStream = null;
		if (oHttpURLConnection.getResponseCode() >= 300) {
			throw new Exception("HTTP Request is not success, Response code is " + oHttpURLConnection.getResponseCode());
		}
		oInputStream = oHttpURLConnection.getInputStream();
		return oInputStream;
	}

	
	
	

	/**
	 * do Get request
	 * 
	 * @param type
	 *            access to PortalDomain/EngineDomain
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String doGetByHttps(String url, String user, String ip) throws Exception {

		SSLContext oSC = SSLContext.getInstance("SSL");
		oSC.init(null, new TrustManager[] { new TrustAnyTrustManager() },
				new java.security.SecureRandom());
		
		URL oLocalURL = new URL(url);

		URLConnection oConnection = oLocalURL.openConnection();
		setTimedOut(oConnection);
		HttpsURLConnection oHttpURLConnection = (HttpsURLConnection) oConnection;
		oHttpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
		oHttpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		oHttpURLConnection.setSSLSocketFactory(oSC.getSocketFactory());
		oHttpURLConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
		oHttpURLConnection.connect();
		//LogUtil.info(m_Log, user, ip, new String[] { "G->P", "->" }, url);
		InputStream oInputStream = null;
		InputStreamReader oInputStreamReader = null;
		BufferedReader oReader = null;
		StringBuffer sbResultBuffer = new StringBuffer();
		String sTempLine = null;
		if (oHttpURLConnection.getResponseCode() >= 300) {
			throw new Exception(
					"HTTP Request is not success, Response code is "
							+ oHttpURLConnection.getResponseCode());
		}
		try {
			oInputStream = oHttpURLConnection.getInputStream();
			oInputStreamReader = new InputStreamReader(oInputStream, "UTF-8");
			oReader = new BufferedReader(oInputStreamReader);

			while ((sTempLine = oReader.readLine()) != null) {
				sbResultBuffer.append(sTempLine);
			}
		} finally {
			if (oReader != null) {
				oReader.close();
			}
			if (oInputStreamReader != null) {
				oInputStreamReader.close();
			}
			if (oInputStream != null) {
				oInputStream.close();
			}
		}
		String sMessage = sbResultBuffer.toString();
		System.out.println(sMessage);
		/*sMessage = LogUtil.filterPwd(sMessage);
		LogUtil.info(m_Log, user, ip, new String[] { "P->G", "<-" }, sMessage
				.replaceAll("><", ">" + Constants.NEWLINE + "<"));*/
		return sbResultBuffer.toString();
	}

	/**
	 * do Post request
	 * 
	 * @param type
	 *            access to PortalDomain/EngineDomain
	 * @param url
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public String doPost(String url, String xmlString, String user, String ip)
			throws Exception {

		URL oLocalURL = new URL(url);

		URLConnection oConnection = oLocalURL.openConnection();
		setTimedOut(oConnection);
		HttpURLConnection oHttpURLConnection = (HttpURLConnection) oConnection;

		oHttpURLConnection.setDoOutput(true);
		oHttpURLConnection.setRequestMethod("POST");
		oHttpURLConnection.setRequestProperty("Accept-Charset",
				"UTF-8");
		oHttpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		oHttpURLConnection.setRequestProperty("Content-Length", String
				.valueOf(xmlString.length()));
		oHttpURLConnection.connect();
		//LogUtil.info(m_Log, user, ip, new String[] { "G->P", "->" }, url);
		OutputStream oOutputStream = null;
		OutputStreamWriter oOutputStreamWriter = null;
		InputStream oInputStream = null;
		InputStreamReader oInputStreamReader = null;
		BufferedReader oReader = null;
		StringBuffer sbResultBuffer = new StringBuffer();
		String sTempLine = null;

		try {
			oOutputStream = oHttpURLConnection.getOutputStream();
			oOutputStreamWriter = new OutputStreamWriter(oOutputStream);

			oOutputStreamWriter.write(xmlString);
			oOutputStreamWriter.flush();
			/*xmlString = LogUtil.filterPwd(xmlString);
			LogUtil.info(m_Log, user, ip, new String[] { "G->P", "->" },
					xmlString.replaceAll("><", ">" + Constants.NEWLINE + "<"));*/
			if (oHttpURLConnection.getResponseCode() >= 300) {
				return "error code :" + oHttpURLConnection.getResponseCode();
			}

			oInputStream = oHttpURLConnection.getInputStream();
			oInputStreamReader = new InputStreamReader(oInputStream,
					"UTF-8");
			oReader = new BufferedReader(oInputStreamReader);

			while ((sTempLine = oReader.readLine()) != null) {
				sbResultBuffer.append(sTempLine);
			}

		} finally {
			if (oOutputStreamWriter != null) {
				oOutputStreamWriter.close();
			}
			if (oOutputStream != null) {
				oOutputStream.close();
			}
			if (oReader != null) {
				oReader.close();
			}
			if (oInputStreamReader != null) {
				oInputStreamReader.close();
			}
			if (oInputStream != null) {
				oInputStream.close();
			}
		}

		String sMessage = sbResultBuffer.toString();
		/*sMessage = LogUtil.filterPwd(sMessage);
		LogUtil.info(m_Log, user, ip, new String[] { "P->G", "<-" }, sMessage
				.replaceAll("><", ">" + Constants.NEWLINE + "<"));*/
		return sbResultBuffer.toString();
	}

	public String doPostByHttps(String url, String xmlString, String user, String ip) throws Exception {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
				new java.security.SecureRandom());

		URL oLocalURL = new URL(url);
		URLConnection oConnection = oLocalURL.openConnection();
		setTimedOut(oConnection);
		HttpsURLConnection oHttpURLConnection = (HttpsURLConnection) oConnection;
		oHttpURLConnection.setDoOutput(true);
		oHttpURLConnection.setRequestMethod("POST");
		oHttpURLConnection.setRequestProperty("Accept-Charset",
				"UTF-8");
		oHttpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		oHttpURLConnection.setRequestProperty("Content-Length", String
				.valueOf(xmlString.length()));
		oHttpURLConnection.setSSLSocketFactory(sc.getSocketFactory());
		oHttpURLConnection.setHostnameVerifier(new TrustAnyHostnameVerifier());
		oHttpURLConnection.connect();
		//LogUtil.info(m_Log, user, ip, new String[] { "G->P", "->" }, url);
		OutputStream oOutputStream = null;
		OutputStreamWriter oOutputStreamWriter = null;
		InputStream oInputStream = null;
		InputStreamReader oInputStreamReader = null;
		BufferedReader oReader = null;
		StringBuffer sbResultBuffer = new StringBuffer();
		String sTempLine = null;

		try {
			oOutputStream = oHttpURLConnection.getOutputStream();
			oOutputStreamWriter = new OutputStreamWriter(oOutputStream);

			oOutputStreamWriter.write(xmlString);
			oOutputStreamWriter.flush();

//			String logXml = LogUtil.filterPwd(xmlString);
			/*LogUtil.info(m_Log, user, ip, new String[] { "G->P", "->" },
					xmlString.replaceAll("><", ">" + Constants.NEWLINE + "<"));*/
			if (oHttpURLConnection.getResponseCode() >= 300) {
				return "error code :" + oHttpURLConnection.getResponseCode();
			}

			oInputStream = oHttpURLConnection.getInputStream();
			oInputStreamReader = new InputStreamReader(oInputStream);
			oReader = new BufferedReader(oInputStreamReader);

			while ((sTempLine = oReader.readLine()) != null) {
				sbResultBuffer.append(sTempLine);
			}

		} finally {
			if (oOutputStreamWriter != null) {
				oOutputStreamWriter.close();
			}
			if (oOutputStream != null) {
				oOutputStream.close();
			}
			if (oReader != null) {
				oReader.close();
			}
			if (oInputStreamReader != null) {
				oInputStreamReader.close();
			}
			if (oInputStream != null) {
				oInputStream.close();
			}
		}
		String message = sbResultBuffer.toString();
	/*	message = LogUtil.filterPwd(message);
		LogUtil.info(m_Log, user, ip, new String[] { "P->G", "<-" }, message
				.replaceAll("><", ">" + Constants.NEWLINE + "<"));*/
		return sbResultBuffer.toString();
	}

	/*public String doPut(String url, String xmlString, String user, String ip) throws Exception {
		
		HttpResponse response = null;
		DefaultHttpClient client = null;
		StringBuffer sbResultBuffer = new StringBuffer();
		InputStreamReader oInputReader = null;
		BufferedReader oReader = null;
		try {
			SSLContext oCtx = SSLContext.getInstance("TLS");
			oCtx.init(null, new TrustManager[]{new TrustAnyTrustManager()}, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(oCtx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("https", 443, socketFactory));
			ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager(schemeRegistry);
			connManager.setDefaultMaxPerRoute(20);
			HttpHost host = new HttpHost(Constants.G_SHKTRADINGENGINEDOMAIN);
			connManager.setMaxForRoute(new HttpRoute(host), 20);
			
			client = new DefaultHttpClient(connManager);
			HttpPut oPut = new HttpPut(url);
			oPut.setHeader("Content-type", "application/x-www-form-urlencoded");
			StringEntity params =new StringEntity(xmlString);
			oPut.setEntity(params);
			response = client.execute(oPut);
			
			xmlString = LogUtil.filterPwd(xmlString);
			LogUtil.info(m_Log, user, ip, new String[] { "P->G", "<-" }, xmlString
					.replaceAll("><", ">" + Constants.NEWLINE + "<"));
			
			oInputReader = new InputStreamReader(response.getEntity().getContent());
			BufferedReader rd = new BufferedReader(oInputReader);
			String sLine = null;
			while ((sLine = rd.readLine()) != null) {
				sbResultBuffer.append(sLine);
			}
		} finally {
			if(oReader!=null){
				oReader.close();
			}
			if(oInputReader!=null){
				oInputReader.close();
			}
		}
		
		String sMessage = sbResultBuffer.toString();
		sMessage = LogUtil.filterPwd(sMessage);
		LogUtil.info(m_Log, user, ip, new String[] { "P->G", "<-" }, sMessage
				.replaceAll("><", ">" + Constants.NEWLINE + "<"));
		return sbResultBuffer.toString();
		
	}

	public String doDelete(String url, String xmlString, String user, String ip) throws Exception {

		HttpResponse response = null;
		DefaultHttpClient client = null;
		StringBuffer sbResultBuffer = new StringBuffer();
		InputStreamReader oInputReader = null;
		BufferedReader oReader = null;
		try {
			SSLContext oCtx = SSLContext.getInstance("TLS");
			oCtx.init(null, new TrustManager[]{new TrustAnyTrustManager()}, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(oCtx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("https", 443, socketFactory));
			ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager(schemeRegistry);
			connManager.setDefaultMaxPerRoute(20);
			HttpHost host = new HttpHost(Constants.G_SHKTRADINGENGINEDOMAIN);
			connManager.setMaxForRoute(new HttpRoute(host), 20);
			
			client = new DefaultHttpClient(connManager);
			HttpDelete del = new HttpDelete(url);
			del.setHeader("Content-type", "application/x-www-form-urlencoded");
			StringEntity params =new StringEntity(xmlString);
			del.setEntity(params);
			response = client.execute(del);
			
			xmlString = LogUtil.filterPwd(xmlString);
			LogUtil.info(m_Log, user, ip, new String[] { "P->G", "<-" }, xmlString
					.replaceAll("><", ">" + Constants.NEWLINE + "<"));
			
			oInputReader = new InputStreamReader(response.getEntity().getContent());
			BufferedReader rd = new BufferedReader(oInputReader);
			String sLine = null;
			while ((sLine = rd.readLine()) != null) {
				sbResultBuffer.append(sLine);
			}
		} finally {
			if(oReader!=null){
				oReader.close();
			}
			if(oInputReader!=null){
				oInputReader.close();
			}
		}
		
		String sMessage = sbResultBuffer.toString();
		sMessage = LogUtil.filterPwd(sMessage);
		LogUtil.info(m_Log, user, ip, new String[] { "P->G", "<-" }, sMessage
				.replaceAll("><", ">" + Constants.NEWLINE + "<"));
		return sbResultBuffer.toString();
		
	}
	*/
	/**
	 * set Timedout
	 * 
	 * @param connection
	 * @return
	 */
	private static void setTimedOut(URLConnection connection) {
		connection.setConnectTimeout(30000);
		connection.setReadTimeout(30000);
	}

	
}
