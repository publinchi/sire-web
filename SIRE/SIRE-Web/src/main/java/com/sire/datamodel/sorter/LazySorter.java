/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.datamodel.sorter;

import com.sire.entities.InvArticulo;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author root
 */
public class LazySorter implements Comparator<InvArticulo> {

    public LazySorter(String sortField, SortOrder sortOrder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compare(InvArticulo t, InvArticulo t1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
