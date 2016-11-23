package com.baustem.obrmanager.service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baustem.obrmanager.entity.Bundle;
import com.baustem.obrmanager.entity.Product;
import com.baustem.obrmanager.mapper.BundleMapper;
import com.baustem.obrmanager.orm.Page;
import com.baustem.utils.AboutAceUtil;

@Service
public class BundleService {
	
	private static final String REPOSITORY_XML = "repository.xml";
	private static String ACEURL = "";
	
	
	@Autowired
	private BundleMapper bndMapper;

	public Page<Bundle> getPage(int pageNo, int pageSize,
			Map<String, Object> reqParamMap) {
		try {
			remoteAccessAceService();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Map<String,Object> mybatisParamsMap = parseParamsToMybatisMap(reqParamMap);
		long totalCount = bndMapper.getTotalCount(mybatisParamsMap);
		
		Page<Bundle> page = new Page<Bundle>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(totalCount);
		
		int firstIndex = (page.getPageNo()-1)*page.getPageSize();
		mybatisParamsMap.put("firstIndex", firstIndex);
		mybatisParamsMap.put("items", pageSize);
		
		List<Bundle> bnds = bndMapper.getBndList(mybatisParamsMap);
		page.setContent(bnds);
		
		return page;
	}

	private void remoteAccessAceService() throws IOException {
		String aceUrlRepo = AboutAceUtil.getAceServiceUrl();
		if(ACEURL.equalsIgnoreCase("")){
			ACEURL = aceUrlRepo;
		}
		if(aceUrlRepo.endsWith("/")){
			aceUrlRepo += REPOSITORY_XML;
		} else{
			aceUrlRepo += "/"+REPOSITORY_XML;
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet reqGet = new HttpGet(aceUrlRepo);
		CloseableHttpResponse response = httpClient.execute(reqGet);
		String content;
		try {
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
			File repo_FILE = AboutAceUtil.getREPO_FILE();
			boolean flag = false;
			if(!repo_FILE.exists()){
				flag = true;
				AboutAceUtil.downloadToTempFile(new StringReader(content));
			} 
			List<Bundle> addBundles = new ArrayList<Bundle>();
			List<Bundle> removeBundles = new ArrayList<Bundle>();
			boolean compared = AboutAceUtil.compare(addBundles, removeBundles, content);
			if(!compared&&addBundles.size()>0){
				AboutAceUtil.downloadToTempFile(new StringReader(content));
				batchSaveBnds(addBundles);
			} else if(!compared&&removeBundles.size()>0){
				AboutAceUtil.downloadToTempFile(new StringReader(content));
				batchDeleteBnds(removeBundles);
			}
			if(flag){
				List<Bundle> bndList = AboutAceUtil.parseContentToBndList(content);
				clearDataTable();
				batchSaveBnds(bndList);
			}
			EntityUtils.consume(entity);
		} finally{
			response.close();
		}
		
		
	}

	public void batchDeleteBnds(List<Bundle> removeBundles) {
		bndMapper.batchDeleteBnds(removeBundles);
	}

	@Transactional
	public void clearDataTable() {
		bndMapper.clearRelates();
		bndMapper.clearBundles();
	}

	private void batchSaveBnds(List<Bundle> bndList) {
		bndMapper.batchSave(bndList);
	}
	
	private Map<String, Object> parseParamsToMybatisMap(
			Map<String, Object> paramsMap) {
		Map<String,Object> map = new HashMap<String, Object>();
		Set<Entry<String, Object>> entrySet = paramsMap.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if(key.startsWith("LIKE_")){
				key = key.substring(key.indexOf("_")+1);
				value = "%"+value+"%";
			}
			if(key.startsWith("date")){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					value = format.parse((String)value);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			map.put(key, value);
			
		}
		return map;
	}

	public void uploadBundle(String filename, File file) throws IOException {
		if(ACEURL.equalsIgnoreCase("")){
			ACEURL = AboutAceUtil.getAceServiceUrl();
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost reqPost = new HttpPost(ACEURL);
		FileEntity inEntity = new FileEntity(file,ContentType.create("text/plain", "UTF-8"));
		try {
			reqPost.setEntity(inEntity);
			CloseableHttpResponse response = httpClient.execute(reqPost);
			String content;
			try {
				HttpEntity entity = response.getEntity();
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() >= 300) {
					throw new HttpResponseException(
							statusLine.getStatusCode(),
							statusLine.getReasonPhrase());
				}
				if(entity!=null){
					content = EntityUtils.toString(entity);
					EntityUtils.consume(entity);
				}
				if (entity == null) {
					throw new ClientProtocolException("Response contains no content");
				}
			} finally{
				response.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	@Transactional(readOnly=true)
	public Bundle getBndById(Long id) {
		return bndMapper.getBndById(id);
	}

	public void remoteDeleteBundle(Bundle bundle) {
		if(ACEURL.equalsIgnoreCase("")){
			ACEURL = AboutAceUtil.getAceServiceUrl();
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String uri = bundle.getUri();
		
		String filename = uri.substring(uri.lastIndexOf("obr/"));
		filename = filename.substring(filename.indexOf("/"));
		HttpDelete httpDelete = new HttpDelete(ACEURL+filename);
		
		try {
			CloseableHttpResponse response = httpClient.execute(httpDelete);
			String content;
			try {
				StatusLine statusLine = response.getStatusLine();
				HttpEntity entity = response.getEntity();
				if (statusLine.getStatusCode() >= 300) {
			        throw new HttpResponseException(
			                statusLine.getStatusCode(),
			                statusLine.getReasonPhrase());
				}
				if(entity!=null){
					content = EntityUtils.toString(entity);
					EntityUtils.consume(entity);
				}
			}  finally{
				response.close();
			}
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void updateBundle(String filename, File temp) {
		if(ACEURL.equalsIgnoreCase("")){
			ACEURL = AboutAceUtil.getAceServiceUrl();
		}
		
		try {
			Response exec = Request
					.Post(ACEURL)
			        .bodyForm(Form.form()
			        .add("replace", "true")
			        .add("filename", filename).build())
			        .bodyFile(temp,ContentType.create("text/plain", "UTF-8"))
			        .execute();
			
			try {
				HttpResponse response = exec.returnResponse();
				StatusLine statusLine = response.getStatusLine();
				HttpEntity entity = response.getEntity();
				String content;
				if (statusLine.getStatusCode() >= 300) {
			        throw new HttpResponseException(
			                statusLine.getStatusCode(),
			                statusLine.getReasonPhrase());
				}
				if(entity!=null){
					content = EntityUtils.toString(entity);
					EntityUtils.consume(entity);
				}
			} finally{
				
			}
			
		}  catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	@Transactional
	public List<Bundle> getAllBundles() {
		return bndMapper.getAllBundles();
	}


	public List<Integer> getRelation(Bundle bundle) {
		return bndMapper.getRelations(bundle);
	}
	
	
}
