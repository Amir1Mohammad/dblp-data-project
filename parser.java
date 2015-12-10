/**
 * Created by Amir Mohammad and sepehr on 11/26/2015.
 */
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
            File inputFile = new File("/home/cloner/Desktop/dblp-data.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class UserHandler extends DefaultHandler {
    
    boolean obj_type_flag= false;
    
    boolean link_type_flag= false;
    
    boolean obj_col_value = false;
    
    boolean link_obj_col_value = false;
    
    String ID;
    
    String ID2;
    
    String attr_name;
    
    String item_type;
    
    private Locator locator;
    
    Map<String,String> obj_id = new HashMap<>();
    
    HashMap<String,HashSet<String>> attributes_obj = new HashMap<>();

    HashMap<String,HashSet<String>> attributes_link = new HashMap<>();

    Map<String,String> link_id = new HashMap<>();

    @Override
    public void setDocumentLocator(final Locator locator) {
        this.locator = locator; // Save the locator, so that it can be used later for line tracking when traversing nodes.
    }



    @Override
    public void startElement(String uri,String localName, String qName, Attributes attributes)throws SAXException {
        if (qName.equalsIgnoreCase("ATTRIBUTE")) {

            item_type = attributes.getValue("ITEM-TYPE");
            attr_name = attributes.getValue("NAME");
            if (Objects.equals(attr_name, "          "))
                { System.out.println(locator.getLineNumber());}

            if (Objects.equals(item_type, "O")) {
                if (Objects.equals(attr_name, "object-type")) {
                    obj_type_flag = true;
                }
            }
            else if (Objects.equals(item_type, "L")) {
                if (Objects.equals(attr_name, "link-type")) {
                    link_type_flag = true;
                }
            }

        }




        else if ((Objects.equals(item_type, "O"))&&(qName.equalsIgnoreCase("ATTR-VALUE"))) {
            ID = attributes.getValue("ITEM-ID");
            if (!obj_type_flag){
                String temp = obj_id.get(ID);
                attributes_obj.get(temp).add(attr_name);
            }

        }



        else if ((Objects.equals(item_type, "L"))&&(qName.equalsIgnoreCase("ATTR-VALUE"))){
            ID2 = attributes.getValue("ITEM-ID");

            if (!link_type_flag){
                String temp = link_id.get(ID2);
                attributes_link.get(temp).add(attr_name);
            }
        }



        else if ((Objects.equals(item_type, "O")) && qName.equalsIgnoreCase("COL-VALUE")) {
            obj_col_value = true;
        }




        else if ((Objects.equals(item_type, "L")) && qName.equalsIgnoreCase("COL-VALUE")){
            link_obj_col_value =true;
        }
    }




    @Override
    public void endElement(String uri,String localName, String qName) throws SAXException {
        if (obj_type_flag&&(qName.equalsIgnoreCase("ATTRIBUTE"))) {
            obj_type_flag = false;
        }else if (link_type_flag&&(qName.equalsIgnoreCase("ATTRIBUTE"))) {
            link_type_flag = false;
        }else if (qName.equalsIgnoreCase("PROX3DB")){
            System.out.println(attributes_obj);
            System.out.println(attributes_link);

        }
    }



    @Override
    public void characters(char ch[],int start, int length) throws SAXException {
        if (obj_type_flag&&obj_col_value) {
            String str = new String(ch, start, length);
            attributes_obj.put(str,new HashSet<>());
            obj_id.put(ID,str);
            obj_col_value =false;
        }
        else if(link_type_flag&&link_obj_col_value) {
            String str = new String(ch, start, length);
            if(!(Objects.equals(str, "editor-of")) && !(Objects.equals(str, "in-proceedings")) && !(Objects.equals(str, "cites")) && !(Objects.equals(str, "in-collection")) && !(Objects.equals(str, "in-journal")) && !(Objects.equals(str, "author-of"))){System.out.println(locator.getLineNumber());}
            attributes_link.put(str,new HashSet<>());
            link_id.put(ID2,str);
            link_obj_col_value =false;
        }
    }
}


//HNT 
// Have Nice Time