package com.example.unmarshalling.service;

import com.example.unmarshalling.Note;
import com.example.unmarshalling.config.AppProperties;
import com.example.unmarshalling.xsdxmlutils.XsdXmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.example.unmarshalling.marshall.XmlMarshaller.unmarshall;

@Component
public class XmlMarshallerComponent {

    private static final Logger LOG = LoggerFactory.getLogger(XmlMarshallerComponent.class);

    @Autowired
    private AppProperties appProperties;

    @PostConstruct
    private void init() throws Exception{
        LOG.info("Unmarshalling, converting XML to Object...");
        XsdXmlUtil xsdXmlUtil = new XsdXmlUtil();

        Note note = unmarshall(Note.class, xsdXmlUtil.locateXML(XsdXmlUtil.XSD_LOCATION.NOTE_XML_DATA.getPath()),
                               xsdXmlUtil.locateSchema(XsdXmlUtil.XSD_LOCATION.NOTE_XSD_DATA.getPath()));
        LOG.info(note.toString());
        LOG.info("To: {}", note.getTo());
        LOG.info("Body: {}", note.getBody());
        LOG.info("From: {}", note.getFrom());
        LOG.info("Heading: {}", note.getHeading());
    }
}
