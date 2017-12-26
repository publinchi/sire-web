package com.sire.sri.batch.recepcion;

//import com.sire.sri.entities.Factura;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import java.util.HashMap;
import java.util.Map;
import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

@Named
public class F1_C1_Processor1 implements ItemProcessor {

    @Override
    public Object processItem(Object item) throws Exception {
        System.out.println("F1_C1_Processor1");
        System.out.println("object in -> " + ((Factura) item).getInfoTributaria().getClaveAcceso());

        Map<String, Object> map = new HashMap();
        map.put("factura", item);
        return map;
    }
}
