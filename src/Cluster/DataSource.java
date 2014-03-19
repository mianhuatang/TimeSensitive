package Cluster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class DataSource {
	ArrayList<DataObject> objects;
	public ArrayList<DataObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<DataObject> objects) {
		this.objects = objects;
	}
	public void readFile(String filename) throws IOException{
		FileReader fr;
		fr = new FileReader(filename);
		BufferedReader input =new BufferedReader(fr, 20 * 1024 * 1024);
		String line="";
		ArrayList<DataObject> objects= new ArrayList<DataObject>();
		int i=0;
		while(( line=input.readLine())!=null){
			String []linearray=line.split("\t");
			DataObject object=new DataObject();
			object.Pquery=linearray[0];
			object.URL=linearray[10];
			object.SessionID=linearray[4];
			object.full=line;
			objects.add(object);
			i++;
		}
		setObjects(objects);
	}
}
