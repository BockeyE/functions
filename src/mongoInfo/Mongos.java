package mongoInfo;

import java.util.Date;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author bockey
 */
public class Mongos {

    @Value("${spring.data.mongodb.host}")
    static String host;
    private static final DB db = new MongoClient(host).getDB("acms");
    private static final MongoDatabase dbs = new MongoClient(host).getDatabase("acms");

    public static boolean checkFileExist(String md5) {
        DBCollection collection = db.getCollection("fs.files");
        BasicDBObject aim = new BasicDBObject("md5", md5);
        DBObject one = collection.findOne(aim);
        if (one == null) {
            System.out.println("file not exist");
            return false;
        }
        System.out.println("file  exist");
        System.out.println("md5 from mongo");
        System.out.println(one.toMap().toString());
        return true;
    }



    public static void getFileInfo() {
        DBCollection collection = db.getCollection("fs.files");
//        BasicDBObject aim = new BasicDBObject("_id", "5c7f897568913a3e3c38a5da");
        BasicDBObject aim = new BasicDBObject("_id", new ObjectId("5c7f897568913a3e3c38a5da"));
        DBObject one = collection.findOne(aim);
        System.out.println(one.toMap().toString());
        System.out.println("============>>>>>>>>>>>");

        MongoCollection<Document> collection1 = dbs.getCollection("fs.files");
        Document document = collection1.find(aim).first();
        System.out.println(document.toString());
        System.out.println(document.toJson());



    }
}
