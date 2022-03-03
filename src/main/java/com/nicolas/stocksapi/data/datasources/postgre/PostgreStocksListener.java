package com.nicolas.stocksapi.data.datasources.postgre;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.nicolas.stocksapi.data.datasources.IStocksListener;
import com.nicolas.stocksapi.data.datasources.websocket.IStocksWebsocket;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

import io.vavr.control.Either;

public class PostgreStocksListener extends PostgreDatasource implements IStocksListener {
    private ListenerThread listener;
    private IStocksWebsocket stocksWebsocket;
    
    public PostgreStocksListener(IStocksWebsocket stocksWebsocket) {
        super("stocks");
        
        DataSource datasource = super.dataSource();
        listener = new ListenerThread(datasource);
        this.stocksWebsocket = stocksWebsocket;
    }

    @Override
    public void listenToNotifications() {
        this.listener.start();
    }

    @Override
    public void onNotification(String payload) {
        stocksWebsocket.notifySockets();
    }  

    private class ListenerThread extends Thread {
        private DataSource dataSource;
        private int refreshRate = 5000;

        public ListenerThread(DataSource datasource) {
            this.dataSource = datasource;
        }

        public void run() {
            var getPgconn = openConnection();
                if (getPgconn.isLeft()) {
                    System.out.println("COULD NOT LISTEN TO STOCKS");
                    return;
                }

            PGConnection pgconn = getPgconn.get();
            PGNotification notifications[]; 

            try {
                while (true) {
                    notifications = pgconn.getNotifications();

                    if (notifications != null) {
                        for (PGNotification notification : notifications)
                            onNotification(notification.getParameter());

                        notifications = null;
                    }

                    Thread.sleep(refreshRate);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private Either<Exception, PGConnection> openConnection() {
            try {
                var conn = dataSource.getConnection();
                var statement = conn.createStatement();
                statement.execute("LISTEN stocks");
                statement.close();

                var pgconn = conn.unwrap(PGConnection.class);
                return Either.right(pgconn);
            }
            catch (Exception e) {
                e.printStackTrace();
                return Either.left(e);
            }
        }
    }

      
}
