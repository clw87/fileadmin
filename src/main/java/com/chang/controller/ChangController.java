package com.chang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chang.entity.ResponseFileEntity;
import com.chang.es.ESIndexBuilder;
import com.chang.es.common.enumeration.IndexNameEnum;
import com.chang.es.common.enumeration.IndexTypeEnum;
import com.chang.service.ChangService;


/** 常氏接口  */
@ComponentScan
@RestController
@RequestMapping(value = "/chang")
public class ChangController {
	
	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody List<ResponseFileEntity> search(HttpServletRequest request,
			@RequestParam(value = "k", defaultValue="") String query,
			@RequestParam(value = "pagenum", defaultValue="1") int pagenum,
			@RequestParam(value = "pagesize", defaultValue="20") int pagesize) throws Exception{
		if(query == null || query.trim().equals("")){
			return null;
		}
		if(pagenum < 1){
			pagenum = 1;
		}
		if(pagesize < 1){
			pagesize = 20;
		}
		return ChangService.findFileByName(query, pagenum, pagesize);
	}
	
	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST} )
	public @ResponseBody String index(HttpServletRequest request,
			@RequestParam(value = "k", defaultValue="") String path
			) throws Exception{
		ESIndexBuilder.bulkProcess(IndexNameEnum.FILE_PROP, IndexTypeEnum.FILE, path);
		return "Index File OK!";
	}

}
