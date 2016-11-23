package com.baustem.obrmanager.entity;

public class Bundle extends IdEntity {
	//resource id
	private String resId;
	//bundle 描述名字
	private String presentationName;
	//bundle 的唯一特征名
	private String symbolicName;
	//repository's bundle uri
	private String uri;
	//bundle version
	private String version;
	//bundle 大小
	private String size;
	//bundle xmlContent
	private String xmlContent;
	
	public Bundle() {
	}


	public Bundle(String resId, String presentationName, String symbolicName,
			String uri, String version, String size,String xmlContent) {
		super();
		this.resId = resId;
		this.presentationName = presentationName;
		this.symbolicName = symbolicName;
		this.uri = uri;
		this.version = version;
		this.size = size;
		this.xmlContent = xmlContent;
	}


	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	public void setSymbolicName(String symbolicName) {
		this.symbolicName = symbolicName;
	}

	public String getPresentationName() {
		return presentationName;
	}

	public void setPresentationName(String presentationName) {
		this.presentationName = presentationName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getXmlContent() {
		return xmlContent;
	}
	
	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resId == null) ? 0 : resId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bundle other = (Bundle) obj;
		if (resId == null) {
			if (other.resId != null)
				return false;
		} else if (!resId.equals(other.resId))
			return false;
		return true;
	}


	/**
	 * testing toString()
	 */
	@Override
	public String toString() {
		return "Bundle [resId=" + resId + ", symbolicName=" + symbolicName
				+ ", presentationName=" + presentationName + ", uri=" + uri
				+ ", size=" + size + ", version=" + version + "]";
	}
	

}
