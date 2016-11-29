package com.chang.es;

import java.net.InetSocketAddress;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.plugin.analysis.ik.AnalysisIkPlugin;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.chang.es.common.enumeration.ClusterIPEnum;

public class ESUtils {

	@SuppressWarnings("resource")
	private static class ClientManager {
		private static Client esClient = null;
		static {
			Settings settings = Settings.builder()
			// .put("client.transport.sniff", true)
			// .put("client.transport.ping_timeout", 10)
			// .put("client.transport.nodes_sampler_interval", 5)
					.put("cluster.name", "chang-es").build();

				esClient = new PreBuiltTransportClient(settings, AnalysisIkPlugin.class)
						.addTransportAddress(
								new InetSocketTransportAddress(new InetSocketAddress(ClusterIPEnum.CHANG_ES1.getName(), 9300)));
		}

	}

	public static Client getClientInstance() {
		return ClientManager.esClient;
	}
	
//	public static void main(String[] args) throws Exception {
//		//批量提交
//		long start = System.currentTimeMillis();
//		ESIndexBuilder.bulkProcess(IndexNameEnum.FILE_PROP, IndexTypeEnum.FILE);
//        System.out.println( "本次索引构建耗时： " + ((double)System.currentTimeMillis() - start)/1000.0 + "s");
//	}

}
