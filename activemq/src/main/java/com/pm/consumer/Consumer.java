package com.pm.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {

    private static final String USER_NAME = "admin";

    private static final String PASSWORD = "ADMIN";

    private static final String URL = "tcp://127.0.0.1:61616";

    private static final String QUEUE_NAME = "queue";

    public static void receive() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(USER_NAME, PASSWORD, URL);
        Connection conn = null;
        Session session = null;
        try {
            conn = activeMQConnectionFactory.createConnection();
            conn.start();
            //第一个参数是否开启事务 开启事务以后需要提交事务；第二个参数 自动签收
            session = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue(QUEUE_NAME);
            MessageConsumer consumer = session.createConsumer(queue);
            while (true) {
                TextMessage message = (TextMessage) consumer.receive();
                if (message != null) {
                    System.out.println("接收到消息： " + message.getText());
                    //应答服务器我已签收
                    message.acknowledge();
//                    session.commit();
                }
            }
        } catch (JMSException e) {
            System.out.println("创建连接失败...");
            e.printStackTrace();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (JMSException e) {
                System.out.println("关闭连接失败...");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        receive();
    }
}
