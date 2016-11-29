package com.chang.es.schema;

import java.io.IOException;
import java.sql.SQLException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.chang.entity.FileEntity;
import com.chang.es.common.enumeration.FileIndexFieldEnum;

public class FileIndexSchema {
	public static XContentBuilder getFileIndexData(FileEntity entity)
			throws IOException, SQLException {
		XContentBuilder sourceData = XContentFactory.jsonBuilder()
				.startObject()
				.field(FileIndexFieldEnum.ID.getName(), entity.getId())
				.field(FileIndexFieldEnum.NAME.getName(), entity.getName())
				.field(FileIndexFieldEnum.FULL_NAME.getName(), entity.getFullName())
				.field(FileIndexFieldEnum.EXTENSION.getName(), entity.getExtension())
				.field(FileIndexFieldEnum.LAST_MODIFY_TIME.getName(), entity.getLastModify())
				.field(FileIndexFieldEnum.TAG.getName(), entity.getTag())
				.endObject();
		return sourceData;
	}
	
	/**
	 * POI schema定义
	 * @return
	 * @throws Exception
	 */
	public static XContentBuilder getFileIndexSchema() throws Exception {
		XContentBuilder poiSchema = XContentFactory.jsonBuilder()
				.startObject()
				.startObject("file")
				.startObject("properties")
				.startObject(FileIndexFieldEnum.ID.getName()).field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
				.startObject(FileIndexFieldEnum.NAME.getName()).field("type", "string").field("analyzer", "ngram_15").field("store", "yes").field("index", "analyzed").endObject()
				.startObject(FileIndexFieldEnum.FULL_NAME.getName()).field("type", "string").field("analyzer", "ngram_15").field("store", "yes").field("index", "analyzed").endObject()
				.startObject(FileIndexFieldEnum.EXTENSION.getName()).field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
				.startObject(FileIndexFieldEnum.TAG.getName()).field("type", "string").field("analyzer", "ngram_15").field("store", "yes").field("index", "analyzed").endObject()
				.startObject(FileIndexFieldEnum.LAST_MODIFY_TIME.getName()).field("type", "long").field("store", "yes").field("index", "not_analyzed").endObject()
				.endObject()
				.endObject()
				.endObject();

		return poiSchema;
	}
	
	
	public static XContentBuilder getFileIndexSetting()
			throws IOException, SQLException {
		XContentBuilder sourceData = XContentFactory.jsonBuilder()
				.startObject()
					.field("number_of_shards", 1)
					.field("number_of_replicas", 0)
					.startObject("requests")
						.startObject("cache").field("enable", true).endObject()
					.endObject()
					.startObject("analysis")
						.startObject("analyzer")
							.startObject("ngram_15")
								.field("tokenizer", "ngram_1_5")
							.endObject()
						.endObject()
						.startObject("tokenizer")
							.startObject("ngram_1_5")
								.field("type", "ngram")
								.field("min_gram", "1")
								.field("max_gram", "5")
								.startArray("token_chars")
									.value("letter")
									.value("digit")
								.endArray()
							.endObject()
						.endObject()
					.endObject()
				.endObject();
		return sourceData;
	}

}
