package com.example.unmarshalling.marshall;


import com.example.unmarshalling.exception.SystemException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.URL;

public final class XmlMarshaller {

    public static <T> T unmarshall(Class <T> type, String message, URL schemaUrl) {

        return unmarshall(type, new ByteArrayInputStream(message.getBytes()), schemaUrl);
    }

    public static <T> T unmarshall(String message, Class <T> type) throws SystemException {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(type);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // documentBuilderFactory.setNamespaceAware(true);

            //noinspection unchecked
            return (T) unmarshaller.unmarshal(new StringReader(message));

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static <T> T unmarshall(Class <T> type, InputStream sourceStream, URL schemaUrl) {

        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(type).createUnmarshaller();
            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaUrl);
            unmarshaller.setSchema(schema);
            setValidationEventHandler(unmarshaller);

            //noinspection unchecked
            return (T) unmarshaller.unmarshal(sourceStream);

        } catch (JAXBException ex) {
            throw new SystemException(String.format("Failed while parsing data for type '%s', error: %s",
                    type.getName(), stackTrace(ex)));
        } catch (SAXException ex) {
            throw new SystemException(String.format("Failed while parsing data for type '%s', error: %s",
                    type.getName(), stackTrace(ex)));
        }
    }

    public static <T> String marshal(T type) {
        try {
            Marshaller marshaller = JAXBContext.newInstance(type.getClass()).createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(type, writer);
            return writer.toString();
        } catch (JAXBException ex) {
            throw new SystemException(String.format("Failed while parsing instance of type '%s', error: %s",
                    type.getClass().getName(), stackTrace(ex)));
        }
    }

    private static void setValidationEventHandler(Unmarshaller unmarshaller) throws JAXBException {
        unmarshaller.setEventHandler(new ValidationEventHandler() {
            public boolean handleEvent(ValidationEvent event) {
                throw new SystemException(String.format(
                        "\nVALIDATION ERROR " +
                                "\nSEVERITY: %s " +
                                "\nMESSAGE: %s " +
                                "\nLINKED EXCEPTION: %s " +
                                "\nLINE NUMBER: %s " +
                                "\nCOLUMN NUMBER: %s " +
                                "\nOFFSET: %s" +
                                "\nOBJECT: %s" +
                                "\nNODE: %s" +
                                "\nURL: %s",

                        event.getSeverity(),
                        event.getMessage(),
                        event.getLinkedException(),
                        event.getLocator().getLineNumber(),
                        event.getLocator().getColumnNumber(),
                        event.getLocator().getOffset(),
                        event.getLocator().getObject(),
                        event.getLocator().getNode(),
                        event.getLocator().getURL()
                ));
            }
        });
    }


    private static String stackTrace(Throwable ex) {
        StringWriter writer = new StringWriter();
        ex.printStackTrace(new PrintWriter(writer, true));
        return ":\n" + writer.toString();
    }
}