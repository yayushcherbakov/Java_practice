package com.sc.game.repository;

/**
 * Класс, содержаший константные строки.
 */
public final class Constants {
    /**
     * Название базы данных.
     */
    public static final String DATABASE_NAME = "jigsawDB";

    /**
     * Название таблицы с результатами.
     */
    public static final String RESULTS_TABLE_NAME = "RESULT_LIST";

    /**
     * Строка для создания таблицы с результатами.
     */
    public static final String CREATE_TABLE_RESULT_LIST = "CREATE TABLE RESULT_LIST  "
            + "(RESULT_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT RESULT_PK PRIMARY KEY, "
            + " FINISH_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
            + " LOGIN VARCHAR(32) NOT NULL,"
            + " STEP_COUNT INT NOT NULL,"
            + " TIME_SPENT BIGINT NOT NULL)";

    /**
     * Строка для вставки данных таблицу результатов.
     */
    public static final String INSERT_DATA_INTO_RESULT_LIST = "INSERT INTO RESULT_LIST"
            + "(FINISH_DATE, LOGIN, STEP_COUNT, TIME_SPENT) "
            + "VALUES (?, ?, ?, ?)";

    /**
     * Строка для фильтрации ТОP10.
     */
    public static final String SELECT_TOP_10_FROM_RESULT_LIST = "SELECT LOGIN, FINISH_DATE, STEP_COUNT, TIME_SPENT "
            + "FROM RESULT_LIST ORDER BY STEP_COUNT DESC, TIME_SPENT, FINISH_DATE DESC FETCH FIRST 10 ROWS ONLY";

    /**
     * Строка для сворачивания БД.
     */
    public static final String SHUTDOWN_DERBY = "jdbc:derby:;shutdown=true";

    /**
     * Строка-паттерн для даты и времени.
     */
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
}