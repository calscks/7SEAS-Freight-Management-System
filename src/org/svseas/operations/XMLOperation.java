package org.svseas.operations;

import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Coded by Seong Chee Ken on 11/01/2017, 23:35. This operation class uses JAXB as the medium between objects and XML.
 */
public class XMLOperation {
    private JAXBContext jaxbContext;

    public XMLOperation(Class... classes) {
        try {
            jaxbContext = JAXBContext.newInstance(classes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void write(DataFile dataFile, ObjectList objectList) {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            QName qName = new QName(dataFile.getData_name());
            JAXBElement<ObjectList> jaxbElement = new JAXBElement<>(qName, ObjectList.class, objectList);
            marshaller.marshal(jaxbElement, new File(dataFile.getData_path()));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Object read(DataFile dataFile) {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StreamSource source = new StreamSource(dataFile.getData_path());
            return unmarshaller.unmarshal(source, ObjectList.class).getValue();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
