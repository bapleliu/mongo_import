package mongo.query;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCursor;

import net.sf.json.JSONObject;

/**
 * 通用select方法
 * @Author huaat-cxn
 */
public class MongoSelect extends MongoBaseQuery {

	public MongoSelect() {
	}
	public MongoSelect(String dataBase) {
		super(dataBase);
	}
	/**
	 * 查询，自动过滤id
	 * @param collectionName 集合名
	 * @param condition 查询条件
	 */
	public Document select(String collectionName,Bson condition){
		return getCollection(collectionName).find(condition).projection(exclude(ID)).first();
	}
	
	public JSONObject selectJson(String collectionName,Bson condition){
		Document document = getCollection(collectionName).find(condition).projection(exclude(ID)).first();
		return JSONObject.fromObject(document);
	}
	/**
	 * 批量查询
	 * @param collectionName
	 * @param condition
	 * @return
	 */
	public List<Document> selectList(String collectionName,Bson condition){
		MongoCursor<Document> result = getCollection(collectionName).find(condition).projection(exclude(ID)).iterator();
		List<Document> rtn = new ArrayList<Document>();
		try {
			while(result.hasNext()){
				rtn.add(result.next());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			if(result!=null){
				result.close();
			}
		}
		return rtn;
	}
}
