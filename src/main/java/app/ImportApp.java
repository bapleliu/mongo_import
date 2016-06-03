package app;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.bson.Document;

import mongo.query.MongoInsert;

public class ImportApp {
	
	private static Logger log = Logger.getLogger("app");
	private static MongoInsert insert = new MongoInsert("hdap");
	/*
	 * args[0]	文件名
	 * args[1]	集合名
	 * args[2]	列名，用%分割（如 buyer_nick%oid%num_iid%item_title%content）
	 * args[3]
	 */
	public static void main(String[] args) {
		log.info("**Starting**");
		try(Stream<String> lines = Files.lines(Paths.get(args[0]));){
			String clsName = args[1];
			String[] fields = args[2].split("%");
			lines.filter(t->{return t!=null&&t.length()>0;})
				.forEach(s->{
					try {
						String[] items = s.split("");
						if(items.length!=fields.length){
							log.warning("items.length not equals fields.length!");
						}
						Document data = new Document();
						for(int i=0;i<fields.length;i++){
							data.append(fields[i], items[i]);
						}
						insert.insert(clsName, data);
					} catch (Exception e) {
						log.info("Handler Exception:"+s);
					}
				});
		} catch (Exception e) {
			log.log(Level.SEVERE,"Something bad happened..", e);
		}
		log.info("**Done**");
	}
}