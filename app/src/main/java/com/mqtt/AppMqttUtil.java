package com.mqtt;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppMqttUtil {

    private Context mContext;
    private MqttAndroidClient mqttAndroidClient;
    private String clientId;//自定义

    private MqttConnectOptions mqttConnectOptions;

    private ScheduledExecutorService reconnectPool;//重连线程池

    public AppMqttUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void buildClient() {

        closeMQTT();//先关闭上一个连接

        buildMQTTClient();
    }

    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {
        @Override
        public void onSuccess(IMqttToken asyncActionToken) {

            closeReconnectTask();
            subscribeToTopic();
        }

        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            //connect-onFailure-MqttException (0) - java.net.UnknownHostException

            startReconnectTask();
        }
    };

    private MqttCallback mqttCallback = new MqttCallback() {
        @Override
        public void connectionLost(Throwable cause) {
            //close-connectionLost-等待来自服务器的响应时超时 (32000)
            //close-connectionLost-已断开连接 (32109)

            if (cause != null) {//null表示被关闭
                startReconnectTask();
            }
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String body = new String(message.getPayload());

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    };

    private void buildMQTTClient(){
        mqttAndroidClient = new MqttAndroidClient(mContext, "", clientId);
        mqttAndroidClient.setCallback(mqttCallback);

        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setConnectionTimeout(10);
        mqttConnectOptions.setKeepAliveInterval(20);
        mqttConnectOptions.setCleanSession(true);

        doClientConnection();
    }

    private synchronized void startReconnectTask(){
        if (reconnectPool != null)return;
        reconnectPool = Executors.newScheduledThreadPool(1);
        reconnectPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                doClientConnection();
            }
        } , 0 , 5*1000 , TimeUnit.MILLISECONDS);
    }

    private synchronized void closeReconnectTask(){
        if (reconnectPool != null) {
            reconnectPool.shutdownNow();
            reconnectPool = null;
        }
    }

    /**
     * 连接MQTT服务器
     */
    private synchronized void doClientConnection() {
        if (!mqttAndroidClient.isConnected()) {
            try {
                mqttAndroidClient.connect(mqttConnectOptions, null, iMqttActionListener);

            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    private void subscribeToTopic() {//订阅之前会取消订阅，避免重连导致重复订阅
        try {
            String registerTopic = "";//自定义
            String controlTopic = "";//自定义
            String[] topicFilter=new String[]{registerTopic , controlTopic };
            int[] qos={0,0};
            mqttAndroidClient.unsubscribe(topicFilter, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
            mqttAndroidClient.subscribe(topicFilter, qos, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {//订阅成功

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    startReconnectTask();

                }
            });

        } catch (MqttException ex) {
        }
    }

    public void sendMQTT(String topicSep, String msg) {
        try {
            if (mqttAndroidClient == null)return;
            MqttMessage message = new MqttMessage();
            message.setPayload(msg.getBytes());
            String topic = "";//自定义
            mqttAndroidClient.publish(topic, message, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    startReconnectTask();

                }
            });
        } catch (MqttException e) {

        }
    }

    public void closeMQTT(){

        closeReconnectTask();

        if (mqttAndroidClient != null){
            try {
                mqttAndroidClient.unregisterResources();
                mqttAndroidClient.disconnect();

                mqttAndroidClient = null;
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

}