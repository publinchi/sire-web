package com.sire.sri.batch.commons;

import com.sire.logger.LogManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class CommonsItem {

    private static final Logger log = LogManager.getLogger(CommonsItem.class);

    public static void closeConnections(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet,
                                        CallableStatement callableStatement, Statement statement) {
        if(resultSet != null)
            try{
                resultSet.close();
            }catch (SQLException e){
                log.log(Level.ERROR, e);
            }
        if(preparedStatement != null)
            try{
                preparedStatement.close();
            }catch (SQLException e){
                log.log(Level.ERROR, e);
            }
        if(callableStatement != null)
            try{
                callableStatement.close();
            }catch (SQLException e){
                log.log(Level.ERROR, e);
            }
        if(statement != null)
            try{
                statement.close();
            }catch (SQLException e){
                log.log(Level.ERROR, e);
            }
        if(connection != null)
            try{
                connection.close();
            }catch (SQLException e){
                log.log(Level.ERROR, e);
            }
    }
}
