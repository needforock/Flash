package ve.needforock.flash.models;

/**
 * Created by Soporte on 11-Sep-17.
 */

public class Message {

    private String content, owner;

    public Message() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
