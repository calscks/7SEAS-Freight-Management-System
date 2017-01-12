package org.svseas.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Codes by Seong Chee Ken on 11/01/2017, 23:35. This operation class uses JAXB as the medium between objects and XML.
 */
public class XMLOperation {
    JAXBContext jaxbContext;

    public XMLOperation(Class... classes){
        try {
            jaxbContext = JAXBContext.newInstance(classes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void writer() throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
    }

    public void reader() throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    }
}
