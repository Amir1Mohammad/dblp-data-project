//import the Module
import java.io.*;
import java.io.File;
import java.lang.Integer;
import java.lang.String;
import java.lang.System;
import java.util.*;
import java.util.HashSet;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;



class amir_project {
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


    boolean journal_name = false;
    boolean people_name = false;
    boolean Attributes = false;
    boolean col_value_flag = false;
    boolean attr = false;
    boolean attr_value_flag = false;
    boolean attri = false;
    boolean obj_type_flag = false;
    boolean title_flag = false;
    String  ID;
    String name;
    HashSet<Integer> journals = new HashSet<>();
    HashSet<String> titles = new HashSet<>();


    @Override
    public void startElement(String uri,String localName, String qName, Attributes attributes)throws SAXException{



        if (qName.equalsIgnoreCase("ATTRIBUTES")) {
            attr = true;
        }
        if (qName.equalsIgnoreCase("ATTRIBUTE")) {
            attri = true;
            name = attributes.getValue("NAME");
            if (Objects.equals(name, "object-type"))
                obj_type_flag = true;
            else if (Objects.equals(name, "title"))
                title_flag = true;

        }
        if (qName.equalsIgnoreCase("ATTR-VALUE")) {
            attr_value_flag = true;
            ID = attributes.getValue("ITEM-ID");
        }
        if (qName.equalsIgnoreCase("COL-VALUE")) {
            col_value_flag = true;

        }


    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("ATTRIBUTE")) {
            if (obj_type_flag) obj_type_flag = false;
            if (title_flag) title_flag = false;
        }
        if (qName.equalsIgnoreCase("COL-VALUE")) {
            col_value_flag = false;

        }
        if (qName.equalsIgnoreCase("ATTR-VALUE")) {
            attr_value_flag = false;
        }

        if (qName.equalsIgnoreCase("ATTRIBUTES")){
            attr = false;
            System.out.println(titles);
        }
    }

    @Override
    public void characters(char ch[],int start, int length)throws SAXException {
        String str = new String(ch, start, length);
        if (obj_type_flag && col_value_flag){
            if (Objects.equals(str , "journal")) {
                journals.add(Integer.parseInt(ID));

            }

        }
        if (title_flag && col_value_flag) {
            if (journals.contains(Integer.parseInt(ID))) {
                titles.add(str);
            }
        }
    }
}