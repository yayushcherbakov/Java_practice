package com.sc.game.repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import static com.sc.game.repository.Constants.*;

/**
 * репозиторий результатов.
 */
public class ResultsRepository {

    /**
     * Подключение.
     */
    private Connection connection;

    /**
     * Подключение к существующей БД или её создание с последующим подключением.
     */
    public void connect() {
        var connectionURL = "jdbc:derby:" + DATABASE_NAME + ";create=true";
        try {
            System.out.println("Connecting to database " + DATABASE_NAME);
            connection = DriverManager.getConnection(connectionURL);
            System.out.println("Connected to database " + DATABASE_NAME);

            var state = connection.createStatement();
            if (!Utils.checkForTable(connection, RESULTS_TABLE_NAME)) {
                System.out.println(" . . . . creating table RESULT_LIST");
                state.execute(CREATE_TABLE_RESULT_LIST);
            }

            state.close();
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
    }

    /**
     * Добавление результата в ДБ.
     */
    public void AddResult(LocalDateTime finishTime, String login, int stepCount, long timeSpent) {
        try (var psInsert = connection.prepareStatement(INSERT_DATA_INTO_RESULT_LIST)) {
            psInsert.setString(1, finishTime.format(DateTimeFormatter.ofPattern(DATETIME_PATTERN)));
            psInsert.setString(2, login);
            psInsert.setString(3, Integer.toString(stepCount));
            psInsert.setString(4, Long.toString(timeSpent));
            psInsert.executeUpdate();
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown when insert result");
            e.printStackTrace(System.out);
        }
    }

    /**
     * Получение результатов TOP10 из БД.
     * @return
     */
    public Vector<ResultResponseModel> GetTopResults() {
        var results = new Vector<ResultResponseModel>();
        try (var state = connection.createStatement();
             var topResults = state.executeQuery(SELECT_TOP_10_FROM_RESULT_LIST);
        ) {
            while (topResults.next()) {
                var result = new ResultResponseModel();
                result.setLogin(topResults.getString("LOGIN"));
                result.setFinishGameTime(topResults.getTimestamp("FINISH_DATE").toLocalDateTime());
                result.setStepNumber(topResults.getInt("STEP_COUNT"));
                result.setStepTime(topResults.getLong("TIME_SPENT"));
                results.add(result);
            }
        } catch (Throwable e) {
            System.out.println(" . . . exception thrown when get results");
            e.printStackTrace(System.out);
        }
        return results;
    }

    /**
     * Закрытие БД.
     */
    public void close() {
        try {
            connection.close();
            boolean gotSQLExc = false;
            try {
                DriverManager.getConnection(SHUTDOWN_DERBY);
            } catch (SQLException se) {
                if (se.getSQLState().equals("XJ015")) {
                    gotSQLExc = true;
                }
            }
            if (!gotSQLExc) {
                System.out.println("Database did not shut down normally");
            } else {
                System.out.println("Database shut down normally");
            }

        } catch (Throwable e) {
            System.out.println(" . . . exception thrown:");
            e.printStackTrace(System.out);
        }
    }
}
