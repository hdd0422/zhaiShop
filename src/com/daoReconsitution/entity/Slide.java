package com.daoReconsitution.entity;
/**
 * 
 * @author 韩豆豆
 * 
 * @title 幻灯片实体类
 */
public class Slide {

	private int id;
	private String name, proPic, url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProPic() {
		return proPic;
	}

	public void setProPic(String proPic) {
		this.proPic = proPic;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Slide() {
		super();
	}

	public Slide(int id, String name, String proPic, String url) {
		super();
		this.id = id;
		this.name = name;
		this.proPic = proPic;
		this.url = url;
	}

	@Override
	public String toString() {
		return "Slide [id=" + id + ", name=" + name + ", proPic=" + proPic + ", url=" + url + "]";
	}

}
