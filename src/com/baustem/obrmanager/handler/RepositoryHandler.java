package com.baustem.obrmanager.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
public class RepositoryHandler {

	@RequestMapping("/{name}")
	public void getRepository(@PathVariable("name") String fileName,
			HttpServletRequest request,HttpServletResponse response){
		String filePath = request.getSession().getServletContext().getRealPath("/")+"store/";
		File file = new File(filePath+fileName+".xml");
		boolean exists = file.exists();
		BufferedReader br = null;
		PrintWriter out = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		try {
			out = response.getWriter();
		if(exists){
				br = new BufferedReader(new FileReader(file));
				char[] buff = new char[2048];
				int b ;
				while((b = br.read(buff))>0){
					out.write(buff,0,b);
				}
			
		}else{
			out.print("库文件没有找到了！");
		}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(out!=null){
				out.close();
			}
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	
}
