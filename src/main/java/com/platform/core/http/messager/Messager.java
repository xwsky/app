package com.platform.core.http.messager;


import com.platform.core.http.messager.impl.JsonMessager;
import com.platform.core.http.messager.impl.TextMessager;

public class Messager {

	public static JsonMessager getJsonMessager(){
		return new JsonMessager();
	}
	
	public static TextMessager getTextMessager(){
		return new TextMessager();
	}
	
}
