package by.htp.ex.controller.listeners;

import by.htp.ex.dao.connection_pool.ConnectionPoolException;
import by.htp.ex.dao.connection_pool.ConnectionPoolProvider;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import static by.htp.ex.constants.ViewConstants.ERROR_MESSAGE;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            ConnectionPoolProvider.getInstance().initPoolData();
        } catch (ConnectionPoolException e) {

            //session.setAttribute(ERROR_MESSAGE,"no command passed in request");
            e.printStackTrace();
            throw new RuntimeException(e); //TODO handle
        }
        ServletContextListener.super.contextInitialized(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPoolProvider.getInstance().dispose();
        ServletContextListener.super.contextDestroyed(sce);

    }
}
