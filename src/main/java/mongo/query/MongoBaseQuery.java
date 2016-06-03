package mongo.query;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import mongo.core.MCollectionManager;
import mongo.core.MongoConnFactory;

/**
 * 数据库查询通用方法
 * @Author huaat-cxn
 */
public class MongoBaseQuery implements MQueryConstant{

	private static MCollectionManager collectionManager = new MCollectionManager();
	
	private String dataBase;

	/**
	 * 使用默认数据库
	 */
	public MongoBaseQuery() {
		super();
		this.dataBase = null;
	}
	/**
	 * 指定数据库
	 * @param dataBase
	 */
	public MongoBaseQuery(String dataBase) {
		super();
		this.dataBase = dataBase;
	}

	protected MongoDatabase getDB(){
		return MongoConnFactory.getDB(dataBase);
	}
	
	protected MongoCollection<Document> getCollection(String name){
		return collectionManager.getCollection(name);
	}
	
	protected boolean checkCollectionExist(String collectionName){
		return collectionManager.checkCls(null, collectionName);
	}
	/**
	 * 过滤字段
	 * @param field 字段名，如 id
	 */
	protected Bson exclude(String field) {
		return new Document(field, 0);//0表示不查询字段
	}
}
