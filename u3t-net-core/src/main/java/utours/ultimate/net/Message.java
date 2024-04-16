package utours.ultimate.net;

public interface Message {

    Object content();

    String address();
    
    boolean isSuccess();

}
