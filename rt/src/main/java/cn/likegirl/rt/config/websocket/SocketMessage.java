package cn.likegirl.rt.config.websocket;

public class SocketMessage {

    private String message;

    private String date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "message='" + message + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}