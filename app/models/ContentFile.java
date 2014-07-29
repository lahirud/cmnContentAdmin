package models;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * Java MongoDB : Save image example
 *
 */

public class ContentFile {
    public static void saveFile(File file, String fileName, String contentType) {

        try {

            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("cmn_content");

            // create a "photo" namespace
            GridFS gfsPhoto = new GridFS(db, "resource");
            // get image file from local drive
            GridFSInputFile gfsFile = gfsPhoto.createFile(file);

            // set a new filename for identify purpose
            gfsFile.setFilename(fileName);
            gfsFile.setContentType(contentType);

            // save the image file into mongoDB
            gfsFile.save();

            System.out.println("Done");

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public static List<Object> getFile(String fileName){
    	List<Object> result = new ArrayList<Object>();
        try {

            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("cmn_content");

            // create a "photo" namespace
            GridFS gfsPhoto = new GridFS(db, "resource");

            // get image file by it's filename
            GridFSDBFile imageForOutput = gfsPhoto.findOne(fileName);
            
            
            System.out.println("Done");
            
            result.add(imageForOutput.getInputStream());
            result.add(imageForOutput.getContentType());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}