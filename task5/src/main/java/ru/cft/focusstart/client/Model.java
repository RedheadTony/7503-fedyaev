package ru.cft.focusstart.client;

public class Model {
    private String message = "";
    private StringBuffer chatContent = new StringBuffer();
    private ChangeListener changeListener;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatContent() {
        return chatContent.toString();
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public void sendMessage() {
        if(message.equals("")) {
            return;
        }
        System.out.println("was changed: " + message);
        chatContent.append("my_nickname: ").append(message).append("\n\n");
        changeListener.resetInput();
        changeListener.onChatContentChange();
        message = "";
    }
}
