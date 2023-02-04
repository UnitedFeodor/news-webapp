package by.htp.ex.dao.connection_pool;

public final class DBConstants {
    private DBConstants(){}

    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://127.0.0.1/news-db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "BebraBebra";
    public static final int DB_POLL_SIZE = 5;
}
