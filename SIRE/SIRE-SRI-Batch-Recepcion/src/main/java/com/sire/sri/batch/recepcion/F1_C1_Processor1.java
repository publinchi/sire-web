package com.sire.sri.batch.recepcion;

import java.util.HashMap;
import java.util.Map;
import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

@Named
public class F1_C1_Processor1 implements ItemProcessor {

    @Override
    public Object processItem(Object item) throws Exception {
        Map<String, Object> map = new HashMap();
        map.put("comprobante", item);
        return map;
    }
}
