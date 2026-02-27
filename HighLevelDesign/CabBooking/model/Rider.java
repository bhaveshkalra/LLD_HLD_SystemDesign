package com.deepak.cabbooking.model;

public class Rider {
	Integer id;
	String name;
	
	public Rider(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Rider [id=" + id + ", name=" + name + "]";
	}
	

}
