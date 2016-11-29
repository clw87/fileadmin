package com.chang.es.factory;

import org.elasticsearch.common.xcontent.XContentBuilder;

import com.chang.es.common.enumeration.IndexNameEnum;
import com.chang.es.common.enumeration.IndexTypeEnum;
import com.chang.es.schema.FileIndexSchema;

public class SchemaFactory {
	
	public static XContentBuilder create(IndexNameEnum indexName, IndexTypeEnum indexType) throws Exception{
		
		if(indexName.getName().equals(IndexNameEnum.FILE_PROP.getName())){
			return FileIndexSchema.getFileIndexSchema();
		}else{
			return null;
		}
	}
}
