package mongo.query;

import java.util.List;

import org.bson.Document;

import net.sf.json.JSONObject;

/**
 * 通用插入方法
 * @Author huaat-cxn
 */
public class MongoInsert extends MongoBaseQuery {

	public MongoInsert() {
	}
	public MongoInsert(String dataBase) {
		super(dataBase);
	}

	/**
	 * 插入数据
	 * @param collectionName 目标集合
	 * @param data 数据
	 */
	public void insert(String collectionName,Document data){
		getCollection(collectionName).insertOne(data);
	}
	public void insert(String collectionName,Object obj){
		getCollection(collectionName).insertOne(Document.parse(JSONObject.fromObject(obj).toString()));
	}
	/**
	 * 批量插入数据
	 */
	public void insert(String collectionName,List<Document> list){
		getCollection(collectionName).insertMany(list);
	}
	
	public boolean checkCls(String clsName){
		return checkCollectionExist(clsName);
	}
}
