package mongo.core;

import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.CreateCollectionOptions;

/**
 * mongo collection管理
 * @Author huaat-cxn
 */
public class MCollectionManager {

	private Set<String> clsNames = new HashSet<String>();
	/**加载所有所有集合名称*/
	public void loadAllCollections(String dbName){
		MongoCursor<String> list = MongoConnFactory.getDB(dbName).listCollectionNames().iterator();
		while(list.hasNext()){
			clsNames.add(list.next());
		}
		if(clsNames.size()==0){
			System.err.println("数据库 "+dbName+" 没有任何集合！");
		}
	}
	/**
	 * 检查集合是否存在
	 * @param dbName 数据库名
	 * @param clsName 集合名
	 */
	public boolean checkCls(String dbName,String clsName){
		if(clsNames==null || clsNames.size()==0){
			loadAllCollections(dbName);
		}
		return clsNames.contains(clsName);
	}
	
	/**
	 * 获取集合
	 * @param dbName 数据库名
	 * @param collectionName 集合名称
	 * @param TClass 文档对象封装后的类型
	 */
	public <T> MongoCollection<T> getCollection(String dbName,String collectionName,Class<T> TClass){
		MongoCollection<T> cls = MongoConnFactory.getDB(dbName).getCollection(collectionName,TClass);
		if(cls==null){
			throw new RuntimeException("MongoCollection Not Find! collectionName = "+collectionName);
		}
		return cls;
	}
	/**
	 * 从默认数据库获取指定类型集合
	 * @param collectionName 集合名称
	 * @param TClass 文档对象封装后的类型
	 */
	public <T> MongoCollection<T> getCollection(String collectionName,Class<T> TClass){
		
		return getCollection(null, collectionName, TClass);
	}
	/**
	 * 从默认数据库获取文档集合
	 * @param collectionName 集合名称
	 */
	public MongoCollection<Document> getCollection(String collectionName){
		
		return getCollection(null, collectionName, Document.class);
	}
	/**
	 * 从指定数据库获取文档集合
	 * @param dbName 数据库名
	 * @param collectionName 文档名
	 */
	public MongoCollection<Document> getCollection(String dbName,String collectionName){
		
		return getCollection(dbName, collectionName, Document.class);
	}
	/**
	 * 创建集合
	 * @param dbName 数据库名
	 * @param collectionName 集合名
	 * @param ops 集合属性
	 */
	public void createCollection(String dbName,String collectionName,CreateCollectionOptions ops){
		MongoConnFactory.getDB(dbName).createCollection(collectionName, ops);
	}
	public void createCollection(String dbName,String collectionName){
		MongoConnFactory.getDB(dbName).createCollection(collectionName);
	}
	public void createCollection(String collectionName,CreateCollectionOptions ops){
		MongoConnFactory.getDB().createCollection(collectionName, ops);
	}
	public void createCollection(String collectionName){
		MongoConnFactory.getDB().createCollection(collectionName);
	}
}
