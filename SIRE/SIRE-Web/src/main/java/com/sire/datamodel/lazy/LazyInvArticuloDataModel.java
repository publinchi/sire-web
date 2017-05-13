/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.datamodel.lazy;

import com.sire.datamodel.sorter.LazySorter;
import com.sire.entities.InvArticulo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author root
 */
public class LazyInvArticuloDataModel extends LazyDataModel<InvArticulo> {

    private final List<InvArticulo> datasource;

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

    @Override
    public List<InvArticulo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        List<InvArticulo> data = new ArrayList<>();

        //filter
        for (InvArticulo invArticulo : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(invArticulo.getClass().getField(filterProperty).get(invArticulo));

                        if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                        match = false;
                    }
                }
            }

            if (match) {
                data.add(invArticulo);
            }
        }

        //sort
        if (sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }

        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        //paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                try {
                    return data.subList(first, first + (dataSize % pageSize));
                } catch (IndexOutOfBoundsException ex) {
                    return null;
                }
            }
        } else {
            return data;
        }
    }
}
