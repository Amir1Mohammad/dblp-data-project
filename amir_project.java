import java.io.*;
import java.io.File;
import java.util.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;



class SAXParserDemo {
    public static void main(String[] args){

        try {
            System.out.println("open the xml file");
            File inputFile = new File("/home/cloner/Desktop/dblp-data.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
        } catch (Exception e) {
            System.out.println("Exception1");
            //e.printStackTrace();
        }
    
}
}
class UserHandler extends DefaultHandler {


        //defind the name and the test :
        boolean journal_name = false;
        boolean people_name = false;
        boolean Attributes = false;
        boolean col_value = false;
        boolean attr = false;
        boolean attr_2 = false;
     


    @Override
    public void startElement(String uri,String localName, String qName, Attributes attributes)throws SAXException{
        try{
        

            // System.out.println("Man Injam");
                // fixme

                if (qName.equalsIgnoreCase("ATTRIBUTES")){
                    System.out.println("Man Injam (ATTRIBUTES)");
                    attr = true;
                }if (qName.equalsIgnoreCase("ATTRIBUTE")){
                    System.out.println("Man Injam (ATTRIBUTE)");
                    attr = true;
                }if (qName.equalsIgnoreCase("ATTR-VALUE")){
                    System.out.println("Man Injam (ATTR-VALUE) ");
                    attr_2 = true;
                }if(qName.equalsIgnoreCase("COL-VALUE")){
                    System.out.println("Man Injam (COL-VALUE)");
                    col_value = true;
                    System.out.println("END The first Algorithm .");
                }
            }catch(Exception g){
                System.out.println("Exception2");
                //g.printStackTrace();
            }
        }
            

//the second Algorithm .


    @Override
    public void characters(char ch[],int start, int length)throws SAXException {
        try{
        if (col_value){        
            System.out.println("Characters");
            File f = null;
      boolean bool = false;
      
      try{
          File file = new File("test_ashrafi.txt");
          file.createNewFile();
          FileWriter writer = new FileWriter(file);
          writer.write("This is an example"); 
          writer.flush(); //flush the writer
          writer.close();
          // create a file reader Object :
          FileReader fr = new FileReader(file);
          char [] a = new char[50];
          fr.read(a);
          for(char c : a)
            System.out.println(c);
          fr.close();


      }catch(Exception e){
         System.out.println("Cant create txt file :(");
         e.printStackTrace();
      }
        }
    }catch(Exception e){
        System.out.println("Exception3");
        //e.printStackTrace();
        }
    }
}