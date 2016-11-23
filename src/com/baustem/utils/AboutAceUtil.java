package com.baustem.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.baustem.obrmanager.entity.Bundle;
import com.baustem.obrmanager.entity.Product;

public class AboutAceUtil {

	private static int BUFFER_SIZE = 8 * 1024;
	private static File REPO_FILE = new File("repository.tmp").getAbsoluteFile();

	public static String getAceServiceUrl() {
		InputStream input = AboutAceUtil.class
				.getResourceAsStream("/obr-config.properties");

		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return prop.getProperty("obrService");
	}
	
	public static String generateRepoXml(List<Bundle> bundles){
		StringBuilder sb = new StringBuilder();
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		sb.append("<?xml version='1.0' encoding='utf-8'?>\n<?xml-stylesheet type='text/xsl' href='http://www2.osgi.org/www/obr2html.xsl'?>\n\n<repository name='OSGiRepository'increment='"+timeStamp+"'xmlns='http://www.osgi.org/xmlns/repository/v1.0.0'>");
		for (Bundle bundle : bundles) {
			sb.append(bundle.getXmlContent()+"\n");
		}
		sb.append("</repository>");
		return sb.toString();
	}
	
	
	public static List<Bundle> parseContentToBndList(File oldContent) {
		SAXReader reader = new SAXReader();
		List<Bundle> bnds = new ArrayList<Bundle>();
		BufferedReader input = null;
		StringBuilder sb = new StringBuilder();
		String content;
		try {
			input = new BufferedReader(new FileReader(oldContent));
			String s;
			while((s=input.readLine())!=null){
				sb.append(s+"\n");
			}
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		content = sb.toString();
		try {
			Document doc = reader.read(oldContent);
			Element root = doc.getRootElement();
			List<Element> elements = root.elements("resource");
			for (Element node : elements) {
				List<Element> subNodes = node.elements();
				if (subNodes.size() > 0) {
					Bundle bnd = getNodeAttributes(content, node);
					bnds.add(bnd);
				}
			}
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}

		return bnds;
	}

	public static List<Bundle> parseContentToBndList(String content) {
		SAXReader reader = new SAXReader();
		List<Bundle> bnds = new ArrayList<Bundle>();
		BufferedReader input = new BufferedReader(new StringReader(content));
		try {
			Document doc = reader.read(input);
			Element root = doc.getRootElement();
			List<Element> elements = root.elements("resource");
			for (Element node : elements) {
				List<Element> subNodes = node.elements();
				if (subNodes.size() > 0) {
					Bundle bnd = getNodeAttributes(content, node);
					bnds.add(bnd);
				}
			}
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}

		return bnds;
	}

	private static Bundle getNodeAttributes(String content, Element node) {
		List<Bundle> bndList = new ArrayList<Bundle>();
		List<Attribute> attNodes = node.attributes();
		List<String> attris = new ArrayList<String>();
		for (Attribute att : attNodes) {
			String value = att.getValue();
			attris.add(value);
		}
		Element nodeSize = node.element("size");

		int start = content.indexOf("<resource id='" + attris.get(0) + "'");
		int end = content.indexOf("</resource>", start);
		String xmlContent = content.substring(start, end);
		Bundle bnd = new Bundle(attris.get(0), attris.get(1), attris.get(2),
				attris.get(3), attris.get(4), nodeSize.getTextTrim(),
				xmlContent + "</resource>");
		return bnd;
	}
	
	
	public static File getREPO_FILE() {
		return REPO_FILE;
	}
	
	
	public static boolean isExists(){
		return getREPO_FILE().exists();
	}

	
	private static List<Bundle> differenceSet(List<Bundle> first,List<Bundle> second){
		first.removeAll(second);
		return first;
	}
	
	public static boolean compare(List<Bundle> addBundles,List<Bundle> removeBundles,String newContent) throws IOException {
		File oldContent = getREPO_FILE();
		List<Bundle> oldBnds = parseContentToBndList(oldContent);
		List<Bundle> newBnds = parseContentToBndList(newContent);
		if(oldBnds.size()<newBnds.size()){
			addBundles.addAll(differenceSet(newBnds, oldBnds));
			return false;
		} else if(oldBnds.size()>newBnds.size()){
			removeBundles.addAll(differenceSet(oldBnds, newBnds));
			return false;
		} else {
			addBundles.addAll(differenceSet(oldBnds, newBnds));
			return (addBundles.size()==0);
		} 
	}

	public static File downloadToTempFile(Reader source) throws IOException {
		
		File tempFile = REPO_FILE;
		PrintWriter fos = null;
		try {
			fos = new PrintWriter(tempFile);
			int read;
			char[] buffer = new char[BUFFER_SIZE];
			while ((read = source.read(buffer)) >= 0) {
				fos.write(buffer, 0, read);
			}
			fos.flush();
			fos.close();
			return tempFile;
		} finally {
			closeQuietly(fos);
		}

	}

	private static void closeQuietly(Closeable resource) {
		try {
			if (resource != null) {
				resource.close();
			}
		} catch (IOException e) {
			// Ignored...
		}
	}

	public static String getProductUrl(HttpServletRequest request, Product product) {
		String server = request.getServerName();
		int port = request.getServerPort();
		String context = request.getContextPath();
		return "http://"+server+":"+port+context+"/store/"+product.getName()+".xml";
	}

	public static void storeRepoXml(HttpServletRequest request, Product product) {
		try {
			String path = request.getSession().getServletContext().getRealPath("/")+"store/";
			PrintWriter pw = new PrintWriter(new File(path+product.getName()+".xml"));
			BufferedReader br = new BufferedReader(new StringReader(product.getRepoXml()));
			
			char[] buff = new char[2048];
			int b;
			while((b=br.read(buff))>0){
				pw.write(buff,0,b);
			}
			pw.flush();
			pw.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	






}
