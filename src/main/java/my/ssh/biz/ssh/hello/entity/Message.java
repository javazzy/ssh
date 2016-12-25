package my.ssh.biz.ssh.hello.entity;

/**
 * Created by admin on 2016/10/12.
 */
public class Message {

    //信息类型（message:信息，file:附件）
    private String type;
    //发送者
    private String from;
    //接受者，当to为空时表示所有者
    private String to;
    //文本信息内容
    private String content;
    //发送服务器的时间
    private Long time;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
