/**
 * 欢迎浏览和修改代码，有任何想法可以email我
 */
package com.github.xincao9.rabbitmq.util;

import com.github.xincao9.rabbitmq.model.Rabbitmq;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 *
 * @author 510655387@qq.com
 */
public class RabbitmqTemplate {

    private ObjectPool<Connection> connectionPool;

    public RabbitmqTemplate(Rabbitmq rabbitmq) throws IOException, TimeoutException {
        this.connectionPool = new GenericObjectPool<Connection>(new RabbitmqPooledConnectionFactory(rabbitmq));
    }

    public void sender () throws Exception {
        Connection connection = connectionPool.borrowObject();
        Channel channel = connection.createChannel();
    }

    public static class RabbitmqPooledConnectionFactory extends BasePooledObjectFactory<Connection> {

        private final ConnectionFactory connectionFactory;

        public RabbitmqPooledConnectionFactory(Rabbitmq rabbitmq) throws IOException, TimeoutException {
            this.connectionFactory = new ConnectionFactory();
            this.connectionFactory.setHost(rabbitmq.getHost());
            this.connectionFactory.setPort(rabbitmq.getPort());
            this.connectionFactory.setUsername(rabbitmq.getUsername());
            this.connectionFactory.setPassword(rabbitmq.getPassword());
            this.connectionFactory.setVirtualHost(rabbitmq.getVirtualHost());
        }

        @Override
        public Connection create() throws Exception {
            return connectionFactory.newConnection();
        }

        @Override
        public PooledObject<Connection> wrap(Connection connection) {
            return new DefaultPooledObject<Connection>(connection);
        }

        /**
         * 链接使用后的初始化操作
         *
         * @param pooledObject
         */
        @Override
        public void passivateObject(PooledObject<Connection> pooledObject) {
        }
    }

}
