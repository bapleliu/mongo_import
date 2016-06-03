package mongo.core;

import com.mongodb.client.MongoDatabase;

/**
 * mongo连接管理，MongoClient内置有连接池，每次getDatabase获取数据库时，相当于从连接池中拿到一个连接。
 * @Author huaat-cxn
 */
public class MongoConnFactory extends MongoClientFactory{

	
	public static MongoDatabase getDB(String dbName){
		if(dbName==null){
			dbName = defaultDb;
		}
		MongoDatabase db = client.getDatabase(dbName);
		if(db==null){
			throw new RuntimeException("连接MongoDB失败，请检查配置参数！");
		}
		return db;
	}
	
	public static MongoDatabase getDB(){
		return getDB(null);
	}
	
}
