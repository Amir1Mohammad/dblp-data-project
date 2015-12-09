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
        boolean attri = false;
        String  ID;
        HashSet<Integer> persons = new HashSet<>();
        // File file = new File("test_ashrafi.txt");
        // file.createNewFile();
        // FileWriter writer = new FileWriter(file);     


    @Override
    public void startElement(String uri,String localName, String qName, Attributes attributes)throws SAXException{
        try{
        

                if (qName.equalsIgnoreCase("ATTRIBUTES")){
                    // System.out.println("Man Injam (ATTRIBUTES)");
                    attr = true;
                }if (qName.equalsIgnoreCase("ATTRIBUTE")){
                    // System.out.println("Man Injam (ATTRIBUTE)");
                    attri = true;
                    String name;
                    name = attributes.getValue("NAME");
                }if (qName.equalsIgnoreCase("ATTR-VALUE")){
                    // System.out.println("Man Injam (ATTR-VALUE) ");
                    attr_2 = true;
                    ID = attributes.getValue("ITEM-ID");
                }if(qName.equalsIgnoreCase("COL-VALUE")){
                    // System.out.println("Man Injam (COL-VALUE)");
                    col_value = true;
                    // System.out.println("END The first Algorithm .");
                }
            }catch(Exception g){
                System.out.println("Exception2");
                //g.printStackTrace();
            }
        }
            

//the second Algorithm .
   @Override
     public void endElement(String uri, 
        String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("COL-VALUE")) {
           col_value = false;
        }else if (qName.equalsIgnoreCase("ATTRIBUTES")){
            attr = false;
            // writer.close();
            System.out.println(persons);
            }
     }

    @Override
    public void characters(char ch[],int start, int length)throws SAXException {
        if (col_value){        
            String str = new String(ch, start, length);
            if (Objects.equals(str , "journal")){
              try{
                  persons.add(Integer.parseInt(ID));

                  // writer.write(ID+"\n"); 
                  // writer.flush(); //flush the writer

                  // create a file reader Object :
                  //FileReader fr = new FileReader(file);
                  //char [] a = new char[50];
                  //fr.read(a);
                  //for(char c : a)
                  // fr.close();


              }catch(Exception e){
                 System.out.println("Cant create txt file :(");
                 e.printStackTrace();
              }
      
            }
        
        //e.printStackTrace();
        }
        else if (attri){
          String str = new String(ch, start, length);
            if (Objects.equals(str , "title")){
        }
    }
  }
}