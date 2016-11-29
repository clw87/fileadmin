package com.chang.es;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.chang.es.common.enumeration.IndexNameEnum;
import com.chang.es.common.enumeration.IndexTypeEnum;
import com.chang.es.datasource.FileIndexDataSource;
import com.chang.es.factory.SchemaFactory;
import com.chang.es.schema.FileIndexSchema;

public class ESIndexBuilder {
	public static boolean isExistsIndexAndType(String indexName,String indexType){
	    TypesExistsResponse  response = ESUtils.getClientInstance().admin().indices()
	            .typesExists(new TypesExistsRequest(new String[]{indexName}, indexType)).actionGet();
	    return response.isExists();
	}
	
	/**
     * 判断指定的索引名是否存在
     * @param indexName 索引名
     * @return  存在：true; 不存在：false;
     */
    public static boolean isExistsIndex(String indexName){
        IndicesExistsResponse  response =  ESUtils.getClientInstance().admin().indices()
        		.exists(new IndicesExistsRequest().indices(new String[]{indexName})).actionGet();
        return response.isExists();
    }

    /**
     * 批量添加索引
     * @throws Exception
     */
	public static void bulkProcess(IndexNameEnum indexName, IndexTypeEnum indexType, String... args) throws Exception {
		Client c = ESUtils.getClientInstance();
		if (null == c) {
			return;
		}
		
		//判断索引是否存在
		if( !isExistsIndex(indexName.getName()) ){
			XContentBuilder settings = null;
			if(indexName.getName().equals(IndexNameEnum.FILE_PROP.getName())){
				settings = FileIndexSchema.getFileIndexSetting();
				System.out.println(settings.string());
			}
			c.admin().indices().prepareCreate(indexName.getName()).setSettings(settings).execute().actionGet();
		}
		
		XContentBuilder schema = SchemaFactory.create(indexName, indexType);
		if(null == schema){
			return;
		}
		
		//判断类型是否存在
		if( !isExistsIndexAndType(indexName.getName(), indexType.getName()) ){
			PutMappingRequest mapping = Requests.putMappingRequest(indexName.getName()).type(indexType.getName()).source(schema);
			c.admin().indices().putMapping(mapping).actionGet();
		}
		
		//判断填充哪部分索引
		if(indexName.getName().equals(IndexNameEnum.FILE_PROP.getName())){
			//第二个参数为路径
			FileIndexDataSource.putDataSource(c, args[0]);
		}

		
	}

//	/**
//	 * 单条添加索引
//	 * @throws Exception
//	 */
//	public static void indexPOIDataIntoES() throws Exception {
//		Client c = ESUtils.getClientInstance();
//		if (null == c) {
//			System.out.println("Error To Put POI Data Into the Index of ES");
//			return;
//		}
//		c.admin().indices().prepareCreate(IndexNameEnum.GOVERMENT_AREA.getName()).execute()
//				.actionGet();
//		XContentBuilder poiSchema = GovernmentAreaPoiSchema.getPoiSchema();
//
//		PutMappingRequest mapping = Requests
//				.putMappingRequest(IndexNameEnum.GOVERMENT_AREA.getName()).type(IndexTypeEnum.POI.getName())
//				.source(poiSchema);
//
//		c.admin().indices().putMapping(mapping).actionGet();
//
//		Integer offset = 0;
//		Integer limit = 1000;
//
//		ResultSet res = DBConnector.getPOIDataFromDB(offset, limit);
//
//		while (res.next()) {
//			XContentBuilder sourceData = GovernmentAreaPoiSchema.getPOISourceData(res);
//			
//			IndexResponse response = c
//					.prepareIndex(IndexNameEnum.GOVERMENT_AREA.getName(), IndexTypeEnum.POI.getName(), res.getString("id"))
//					.setSource(sourceData.string()).execute().actionGet();
//			System.out.println(response.isCreated());
//			
//			while (res.next()) {
//				sourceData = GovernmentAreaPoiSchema.getPOISourceData(res);
//				
//				response = c
//						.prepareIndex(IndexNameEnum.GOVERMENT_AREA.getName(), IndexTypeEnum.POI.getName(), res.getString("id"))
//						.setSource(sourceData.string()).execute().actionGet();
//				System.out.println(response.isCreated());
//			}
//
//			offset += limit;
//			System.out.println(offset);
//			res = DBConnector.getPOIDataFromDB(offset, limit);
//		}
//
//	}
}
