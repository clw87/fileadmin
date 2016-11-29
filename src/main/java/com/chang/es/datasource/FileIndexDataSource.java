package com.chang.es.datasource;

import java.util.List;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.chang.common.AlgorithmUtils;
import com.chang.entity.FileEntity;
import com.chang.es.common.enumeration.IndexNameEnum;
import com.chang.es.common.enumeration.IndexTypeEnum;
import com.chang.es.schema.FileIndexSchema;

public class FileIndexDataSource {
	
	public static void putDataSource(Client c, String dir) throws Exception {
		BulkProcessor bulkProcessor = BulkProcessor.builder(c, new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				System.out.println("Prepare to send data to index...");
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				System.out.println("send data to index finished!");
			}

			// 设置ConcurrentRequest 为0，Throwable不抛错
			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				System.out.println("happen fail = " + failure.getMessage() + " cause = " + failure.getCause());
			}
		})
		.setBulkActions(10000)
		.setBulkSize(new ByteSizeValue(20, ByteSizeUnit.MB))
		.setFlushInterval(TimeValue.timeValueSeconds(5))
		.setConcurrentRequests(1).build();

		//取数据
		List<FileEntity> list = AlgorithmUtils.recursionFileNameForDir(dir);
		if(list != null && list.size() > 0){
			for(FileEntity entity : list){
				XContentBuilder sourceData = FileIndexSchema.getFileIndexData(entity);
				bulkProcessor.add(new IndexRequest(IndexNameEnum.FILE_PROP.getName(), IndexTypeEnum.FILE.getName(), entity.getId()).source(sourceData.string()));
			}
		}
		
		bulkProcessor.flush();
		bulkProcessor.close();
	}
}
