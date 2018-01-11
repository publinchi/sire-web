/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sire.service;

import java.sql.Connection;
import java.sql.SQLException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.sire.service.IDatasourceService;

/**
 *
 * @author publio
 */
@Singleton
@Startup
public class DatasourceService implements IDatasourceService {

    private Connection connection;

    @Override
    public Connection getConnection() throws SQLException, NamingException {
        if (connection == null || (connection != null && connection.isClosed())) {
            Context initContext = new InitialContext();
            String datasource = System.getProperty("sire.datasource");
            if (datasource == null) {
                datasource = "jdbc/sire";
            }
            javax.sql.DataSource ds = (javax.sql.DataSource) initContext.lookup(datasource);
            connection = ds.getConnection();
        }
        return connection;
    }
}
