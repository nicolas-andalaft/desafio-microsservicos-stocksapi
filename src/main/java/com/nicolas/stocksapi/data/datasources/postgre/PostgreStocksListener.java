package com.nicolas.stocksapi.data.datasources.postgre;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.nicolas.stocksapi.data.datasources.IStocksListener;
import com.nicolas.stocksapi.data.datasources.websocket.IStocksWebsocket;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

public class PostgreStocksListener extends PostgreDatasource implements IStocksListener {
    private Logger logger;
    private ListenerThread listener;
    private IStocksWebsocket stocksWebsocket;
    
    public PostgreStocksListener(IStocksWebsocket stocksWebsocket) {
        super("stocks");
        
        DataSource datasource = super.dataSource();
        listener = new ListenerThread(datasource);
        this.stocksWebsocket = stocksWebsocket;

        logger = Logger.getLogger("Logger");
    }

    @Override
    public void listenToNotifications() {
        this.listener.start();
    }

    @Override
    public void onNotification(String payload) {
        stocksWebsocket.notifyClients();
    }  

    private class ListenerThread extends Thread {
        private DataSource dataSource;
        private int refreshRate = 5000;

        public ListenerThread(DataSource datasource) {
            this.dataSource = datasource;
        }

        @Override
        public void run() {
            PGConnection pgconn = null;

            // Get connection
            try(var conn = dataSource.getConnection(); var statement = conn.createStatement();) {
                
                statement.execute("LISTEN stocks_update");

                pgconn = conn.unwrap(PGConnection.class);
            }
            catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage());
            }

            if (pgconn != null)
                listen(pgconn);            
        }

        private void listen(PGConnection pgconn) {
            PGNotification[] notifications; 

            try {
                while (true) {
                    notifications = pgconn.getNotifications();

                    if (notifications != null) {
                        for (PGNotification notification : notifications)
                            onNotification(notification.getParameter());
                    }

                    Thread.sleep(refreshRate);
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "LISTEN THREAD INTERRUPTED");
            }
            catch (Exception e) {
                logger.log(Level.WARNING, e.getMessage());
            }
        }
    }
}
