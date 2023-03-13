package by.htp.ex.controller.listeners;

import by.htp.ex.dao.connection_pool.ConnectionPoolException;
import by.htp.ex.dao.connection_pool.ConnectionPoolProvider;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            ConnectionPoolProvider.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("error initialising the connection pool",e);
        }
        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPoolProvider.getInstance().dispose();
        } catch (ConnectionPoolException e) {
            throw new RuntimeException("error disposing of the connection pool",e);
        }
        ServletContextListener.super.contextDestroyed(sce);

    }
}
