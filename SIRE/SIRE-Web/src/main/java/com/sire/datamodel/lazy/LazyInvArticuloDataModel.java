/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.datamodel.lazy;

import com.sire.entities.InvArticulo;
import java.util.List;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author root
 */
public class LazyInvArticuloDataModel extends LazyDataModel<InvArticulo> {

    private List<InvArticulo> datasource;

    public LazyInvArticuloDataModel(List<InvArticulo> datasource) {
        this.datasource = datasource;
    }

    @Override
    public InvArticulo getRowData(String rowKey) {
        for (InvArticulo invArticulo : datasource) {
            if (String.valueOf(invArticulo.getInvArticuloPK().getCodArticulo()).equals(rowKey)) {
                return invArticulo;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(InvArticulo invArticulo) {
        return invArticulo.getInvArticuloPK().getCodArticulo();
    }
    
   
}
