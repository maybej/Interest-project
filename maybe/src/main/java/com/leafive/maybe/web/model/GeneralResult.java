package com.leafive.maybe.web.model;

import java.util.List;

import lombok.Data;

@Data
public class GeneralResult {
	
	private long total;
		
	private List<?> list;  
}
