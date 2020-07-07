package com.medcaptain.pushsocketservice.socket;

public class Greeting {

    /**
     * 标志
     */
    private Integer sign;

    /**
     * 标题
     */
    private String title;
    /**
     * 标题
     */
    private String content;

    /**
     * 创建时间
     */
    private String create_time;

    public Greeting() {
    }

    public Greeting(Integer sign, String title, String content, String create_time) {
        this.sign = sign;
        this.title = title;
        this.content = content;
        this.create_time = create_time;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "sign='" + sign + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
