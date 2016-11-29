package com.chang.service;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import com.chang.entity.ResponseFileEntity;
import com.chang.es.ESUtils;
import com.chang.es.common.enumeration.IndexNameEnum;
import com.chang.es.common.enumeration.IndexTypeEnum;

@Service
public class ChangService {
	public static List<ResponseFileEntity> findFileByName(String name, int pageNum, int pageSize){
		Client c = ESUtils.getClientInstance();
		if (c == null) {
			return null;
		}
		SearchResponse response = c.prepareSearch(IndexNameEnum.FILE_PROP.getName())
				.setTypes(IndexTypeEnum.FILE.getName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchQuery("name", name)).setSize(pageSize).setFrom((pageNum-1)*pageSize)
				.execute().actionGet();
		List<ResponseFileEntity> result = new ArrayList<ResponseFileEntity>();
		
		if(response == null || response.getHits() == null || response.getHits().getHits().length == 0){
			return result;
		}
		for(SearchHit hit : response.getHits().getHits()){
			ResponseFileEntity en = new ResponseFileEntity();
			en.setExtension(hit.getSource().get("extension").toString());
			en.setName(hit.getSource().get("name").toString());
			en.setPath(hit.getSource().get("fullname").toString().replaceAll("/home/chang/app/", "http://127.0.0.1:80/"));
			result.add(en);
		}
		return result;
	}

}
