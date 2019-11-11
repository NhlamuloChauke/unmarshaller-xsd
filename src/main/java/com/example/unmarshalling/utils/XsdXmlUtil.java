package com.example.unmarshalling.utils;

import com.example.unmarshalling.exception.SystemException;

import java.io.InputStream;
import java.net.URL;

public class XsdXmlUtil {

    public URL locateSchema(String schemaPath) {
        URL schemaUrl = getClass().getClassLoader().getResource(schemaPath);

        if (schemaUrl != null) {
            return schemaUrl;
        } else {
            throw new SystemException("Failed to load XSD from " + schemaPath);
        }
    }

    public InputStream locateXML(String schemaPath) {
        InputStream stream = getClass().getResourceAsStream(schemaPath);

        if (stream != null) {
            return stream;
        } else {
            throw new SystemException("Failed to load XML from " + schemaPath);
        }
    }

    public enum XSD_LOCATION {
        NOTE_XSD_DATA("xsd/example.xsd"),
        NOTE_XML_DATA("/xml/example.xml");


        private String path;

        XSD_LOCATION(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

}
