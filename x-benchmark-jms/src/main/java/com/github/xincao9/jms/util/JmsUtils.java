/**
 * 欢迎浏览和修改代码，有任何想法可以email我
 */
package com.github.xincao9.jms.util;

import com.github.xincao9.benchmark.core.util.Logger;
import com.github.xincao9.jms.model.Jms;
import java.util.Map;
import java.util.Map.Entry;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 *
 * @author 510655387@qq.com
 */
public class JmsUtils {

    private JmsTemplate jmsTemplate;
    private PooledConnectionFactory connectionFactory;
    private static JmsUtils jmsUtils;
    private static Object lock = new Object();

    public static JmsUtils getInstance(Jms jms) {
        if (jmsUtils == null) {
            synchronized (lock) {
                if (jmsUtils == null) {
                    if (jms.getAccount() == null || jms.getAccount().isEmpty()) {
                        jmsUtils = new JmsUtils(jms.getAddress());
                    } else {
                        jmsUtils = new JmsUtils(jms.getAddress(), jms.getAccount(), jms.getPasswd());
                    }
                }
            }
        }
        return jmsUtils;
    }

    private JmsUtils(String address, String account, String passwd) {
        try {
            org.apache.activemq.ActiveMQConnectionFactory activeMQConnectionFactory = new org.apache.activemq.ActiveMQConnectionFactory(account, passwd, address);
            activeMQConnectionFactory.setUseAsyncSend(true);
            activeMQConnectionFactory.setSendTimeout(2000);
            connectionFactory = new PooledConnectionFactory(activeMQConnectionFactory);
            connectionFactory.setMaxConnections(32);
            connectionFactory.start();
            jmsTemplate = new JmsTemplate(connectionFactory);
            jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
            jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        } catch (Throwable e) {
            Logger.info(e.getMessage());
        }
    }

    private JmsUtils(String address) {
        try {
            org.apache.activemq.ActiveMQConnectionFactory activeMQConnectionFactory = new org.apache.activemq.ActiveMQConnectionFactory(address);
            activeMQConnectionFactory.setUseAsyncSend(true);
            activeMQConnectionFactory.setSendTimeout(2000);
            connectionFactory = new PooledConnectionFactory(activeMQConnectionFactory);
            connectionFactory.setMaxConnections(32);
            connectionFactory.start();
            jmsTemplate = new JmsTemplate(connectionFactory);
            jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
            jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        } catch (Throwable e) {
            Logger.info(e.getMessage());
        }
    }

    public void send(String destination, final Map<String, Object> data) throws Exception {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                for (Entry<String, Object> e : data.entrySet()) {
                    message.setString(e.getKey(), String.valueOf(e.getValue()));
                }
                return message;
            }
        });
    }

    public void close() {
        this.connectionFactory.stop();
    }
}
