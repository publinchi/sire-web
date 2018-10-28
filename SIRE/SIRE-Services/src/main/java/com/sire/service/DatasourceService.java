/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author publio
 */
@Singleton
@Startup
public class DatasourceService implements IDatasourceService {

    private String databaseProductName;

    @Override
    public Connection getConnection() throws SQLException, NamingException {
        return _getConnection(-1);
    }

    @Override
    public Connection getConnection(int timeout) throws SQLException, NamingException {
        return _getConnection(timeout);
    }

    @Override
    public String getDatabaseProductName() throws SQLException, NamingException {
        if(databaseProductName == null){
            try(Connection connection = getConnection()) {
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                databaseProductName = databaseMetaData.getDatabaseProductName();
            }
        }
        return databaseProductName;
    }

    private Connection _getConnection(int timeout) throws SQLException, NamingException {
        Context initContext = new InitialContext();
        String datasource = System.getProperty("sire.datasource");
        if (datasource == null) {
            datasource = "jdbc/sire";
        }
        javax.sql.DataSource ds = (javax.sql.DataSource) initContext.lookup(datasource);
        if(timeout !=-1)
            ds.setLoginTimeout(timeout);
        return ds.getConnection();
    }
}