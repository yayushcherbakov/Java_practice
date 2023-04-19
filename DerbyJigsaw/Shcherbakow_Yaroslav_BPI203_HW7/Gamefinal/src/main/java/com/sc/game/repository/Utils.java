package com.sc.game.repository;

import java.sql.*;

public class Utils {
    /**
     * Проверяет наличие таблицы и выдает соответствующее сообщение об ошибке при её отсутствии.
     */
    public static boolean checkForTable(Connection connection, String tableName) throws SQLException {
        boolean result = tableExists(connection, tableName);
        System.out.println("Table \"RESULT_LIST\" exists: " + result);

        try (var state = connection.createStatement()){
            state.execute("UPDATE RESULT_LIST SET FINISH_DATE = CURRENT_TIMESTAMP, LOGIN = 'TEST' WHERE 1=3");
        } catch (SQLException sqle) {
            String theError = (sqle).getSQLState();
            /** If table exists will get -  WARNING 02000: No row was found **/
            if (theError.equals("42X05"))   // Table does not exist
            {
                return false;
            } else if (theError.equals("42X14") || theError.equals("42821")) {
                System.out.println("Incorrect table definition. Drop table RESULT_LIST and rerun this program");
                throw sqle;
            } else {
                System.out.println("Unhandled SQLException");
                throw sqle;
            }
        }
        System.out.println("Just got the warning - table exists OK ");
        return true;
    }

    /**
     * Проверяет существование таблицы.
     */
    static boolean tableExists(Connection connection, String tableName) throws SQLException {
        var meta = connection.getMetaData();
        var resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
        return resultSet.next();
    }
}
