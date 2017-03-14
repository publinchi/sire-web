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

    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(InvArticulo invArticulo1, InvArticulo invArticulo2) {
        try {
            Object value1 = InvArticulo.class.getField(this.sortField).get(invArticulo1);
            Object value2 = InvArticulo.class.getField(this.sortField).get(invArticulo2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
    
}
