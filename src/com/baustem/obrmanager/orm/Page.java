package com.baustem.obrmanager.orm;

import java.util.List;

public class Page<T> {
	
	private Integer pageSize;
	private Integer pageNo;
	
	private Long totalCount;
	private List<T> content;
	
	public boolean isHasNext(){
		if(pageNo == getTotalPages()){
			return false;
		}
		return true;
	}
	
	public boolean isHasPre(){
		if(pageNo == 1){
			return false;
		}
		return true;
	}
	
	public int getNextPages(){
		if(isHasNext()) 
			return pageNo + 1;
		return getTotalPages();
	}
	
	public int getPrePages(){
		if(isHasPre())
			return pageNo - 1;
		return 1;
	}
	
	
	public Integer getTotalPages(){
		return (int)(totalCount + (pageSize - 1))/pageSize;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		if(pageNo<=0){
			pageNo = 1;
		}
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		if(pageNo <= 0){
			pageNo = 0;
		}
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		if(pageNo >= this.getTotalPages()){
			pageNo = this.getTotalPages();
		}
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
	
	
	
	
	
	

}
