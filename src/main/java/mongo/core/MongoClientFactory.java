package mongo.core;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
/**
 * mangoClient工厂，启动时实例化mango client
 * @Author huaat-cxn
 */
@SuppressWarnings("unused")
public class MongoClientFactory{

	private static Logger logger = Logger.getLogger("mongo");
	
	public final static String DB_HOST="conn.mongodb.host";
	public final static String DB_PORT="conn.mongodb.port";
	public final static String DB_DEFAULT="conn.mongodb.database";
	public final static String DB_USER="conn.mongodb.username";
	public final static String DB_PWD="conn.mongodb.password";
	/**jdbc.properties配置*/
	protected static Properties dbProps = null;
	/**MongoClient实例*/
	protected static MongoClient client = null;
	/**默认数据库*/
	protected static String defaultDb = null;
	/**用户认证数据库*/
	private final static String AUTH_DB="admin"; 
	private static String host;
	private static int port;
	private static String username;
	private static String password;
	/**超时设置（ms）*/
	private static int timeout = 5000;
	private static int maxConnections = 100;
	
	static{
		try {
			logger.info("初始化MongoClient...");
			dbProps=PropertiesLoaderUtils.loadAllProperties("jdbc.properties");
			host = dbProps.getProperty(DB_HOST);
			port = Integer.parseInt(dbProps.getProperty(DB_PORT));
			defaultDb = dbProps.getProperty(DB_DEFAULT);
			
			connectAnonymous();
		} catch (Exception e) {
        	e.printStackTrace();
        	System.exit(-1);
		}
	}
	
	public MongoClient createInstance(){
		testConnect();
		return client;
	}
	/**
	 * 测试mongdb是否可用，因为isLocked方法会校验数据库权限，所以可以检测连接及权限。
	 */
	private static void testConnect(){
		client.isLocked();
	}
	
	/**
	 * 匿名连接
	 */
	private static void connectAnonymous(){
		client = new MongoClient(host, port);
	}
	/**
	 * 认证连接
	 */
	private static void connectAuth() throws IOException{
		username = dbProps.getProperty(DB_USER);
		password = dbProps.getProperty(DB_PWD);
		
		List<ServerAddress> seedList = new ArrayList<ServerAddress>();
		seedList.add(new ServerAddress(host, port));
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(MongoCredential.createScramSha1Credential(username, AUTH_DB, password.toCharArray()));
		//默认连接数为
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(maxConnections)
        		.connectTimeout(timeout).socketTimeout(timeout).serverSelectionTimeout(timeout).build();
		client = new MongoClient(seedList, credentials,options);
	}
}
