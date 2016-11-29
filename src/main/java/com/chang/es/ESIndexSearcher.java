package com.chang.es;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;

import com.chang.es.common.enumeration.IndexNameEnum;
import com.chang.es.common.enumeration.IndexTypeEnum;

public class ESIndexSearcher {
	public static void search() {
		Client c = ESUtils.getClientInstance();
		if (c == null) {
			return;
		}
		SearchResponse response = c.prepareSearch(IndexNameEnum.FILE_PROP.getName())
				.setTypes(IndexTypeEnum.FILE.getName()).setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.termQuery("id", "373819297230496"))
				.execute().actionGet();

		System.out.println(response.getHits().getAt(0).getSourceAsString());
	}
	
	
}
