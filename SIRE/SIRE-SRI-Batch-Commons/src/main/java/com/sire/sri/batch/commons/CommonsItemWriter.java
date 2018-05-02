package com.sire.sri.batch.commons;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.sun.xml.bind.marshaller.DataWriter;
import com.sire.sri.batch.util.JaxbCharacterEscapeHandler;

public abstract class CommonsItemWriter extends AbstractItemWriter {

    protected String object2xmlUnicode(Object item, Class clazz, String pck, String name) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(item.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new JaxbCharacterEscapeHandler());

        if(clazz != null && pck != null && name != null){
            QName qName = new QName(pck, name);
            JAXBElement root = new JAXBElement<>(qName, clazz, item);
            jaxbMarshaller.marshal(root, dataWriter);
        } else
            jaxbMarshaller.marshal(item, dataWriter);

        return stringWriter.toString();
    }

}
