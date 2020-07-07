package com.medcaptain.utils;

import java.util.Objects;

public class TopicUtil {

    /**
     * topic 不可存在的符号
     */
    static final String TOPIC_PERCENTAGE_NUMBER = "%";
    /**
     * topic 位分割符
     */
    static final String TOPIC_SEGMENTATION = "/";

    /**
     * topic
     */
    static final String TOPIC_WILDCARD = "$";

    /**
     * 解析 topic
     * <p>
     * 将 topic /TOPIC_WILDCARD{}/  替换成 /replace/
     * 例：
     * /sys/key1/${dev}/thing/event/${id}/post     %
     * 解析为：
     * /sys/key1/%/thing/event/%/post
     * ¬
     *
     * @param topic   topic
     * @param replace 替换参数  确保 topic 中不包含 % 否则 返回 null
     * @param dollar  ${dollar}  里面的内容 替换为 replace
     * @return 解析后的topic
     */
    public static String analysisReplaceTopic(String topic, String dollar, String replace) {
        String[] topicArray = topic.split(TOPIC_SEGMENTATION);
        StringBuilder returnString = new StringBuilder();
        for (String topicString : topicArray) {
            if (topicString.length() > 0) {
                if (Objects.equals(TOPIC_WILDCARD, topicString.substring(0, 1))) {
                    if (dollar != null) {
                        if (Objects.equals(dollar, topicString.substring(2, topicString.length() - 1))) {
                            returnString.append(TOPIC_SEGMENTATION);
                            returnString.append(replace);
                        } else {
                            returnString.append(TOPIC_SEGMENTATION);
                            returnString.append(topicString);
                        }
                    } else {
                        returnString.append(TOPIC_SEGMENTATION);
                        returnString.append(replace);
                    }
                } else {
                    returnString.append(TOPIC_SEGMENTATION);
                    returnString.append(topicString);
                }
            }
        }
        return returnString.toString();
    }

    /**
     * /sys/key1/${deviceName}/thing/event/${id}/post
     * 将全部 ${} 替换为 %
     *
     * @param topic topic
     * @return /sys/key1/%/thing/event/%/post
     */
    public static String analysisNotPercentTopic(String topic, String replace) {
        if (!Objects.equals(topic.indexOf(TOPIC_PERCENTAGE_NUMBER), -1)) {
            return null;
        }
        return analysisReplaceTopic(topic, null, replace);
    }

    /**
     * /sys/key1/${deviceName}/thing/event/${id}/post
     * 替换后
     * /sys/key1/%/thing/event/${id}/post
     *
     * @param topic   topic
     * @param name    ${name}  替换为 content 的内容
     * @param content ${name}  替换为 content 的内容
     * @return 替换后的内容
     */
    public static String replaceTopicWildcardDeviceName(String topic, String name, String content) {
        return analysisReplaceTopic(topic, name, content);
    }

    public static String replaceTopicProductKeyAndDeviceName(String topic, String productKey, String deviceName) {
        topic = topic.replace("${deviceName}", deviceName);
        topic = topic.replace("${productKey}", productKey);
        return topic;
    }
}
