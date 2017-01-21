package my.ssh.test.biz.util;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 2017/1/20 0020.
 */
public class ElasticsearchTest {

    private TransportClient client = null;
    @Before
    public void before() throws UnknownHostException {
        // TODO Auto-generated method stub
        // 设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        // 创建client
        client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    @Test
    public void put(){
        Map<String,String> map = new HashMap<>();
        map.put("id","1");
        map.put("name","郑丽");
        map.put("sex","女");
        IndexRequestBuilder indexRequestBuilder = client.prepareIndex("student_index", "student_info").setSource(JSON.toJSONString(map));
        ListenableActionFuture<IndexResponse> listenableActionFuture =  indexRequestBuilder.execute();
        IndexResponse response = listenableActionFuture.actionGet();
        System.out.println(response.getId());
    }

    @Test
    public void delete(){
        DeleteResponse response = client.prepareDelete("student_index","student_info","1").execute().actionGet();
        System.out.println(response.status());
    }

    @Test
    public void search() {
        QueryBuilder matchQuery = QueryBuilders.matchQuery("id", "1");
        HighlightBuilder hiBuilder=new HighlightBuilder();
        hiBuilder.preTags("<h2>");
        hiBuilder.postTags("</h2>");
        hiBuilder.field("name");
        // 搜索数据
        SearchResponse response = client.prepareSearch("student_index")
                .setQuery(matchQuery)
                .highlighter(hiBuilder)
                .execute().actionGet();
        //获取查询结果集
        SearchHits searchHits = response.getHits();
        System.out.println("共搜到:"+searchHits.getTotalHits()+"条结果!");
        //遍历结果
        for(SearchHit hit:searchHits){
            System.out.println("String方式打印文档搜索内容:");
            System.out.println(hit.getSourceAsString());
            System.out.println("Map方式打印高亮内容");
            System.out.println(hit.getHighlightFields());

            System.out.println("遍历高亮集合，打印高亮片段:");
            Text[] text = hit.getHighlightFields().get("name").getFragments();
            for (Text str : text) {
                System.out.println(str.string());
            }
        }
    }

}
