package main;

public interface Callback {
    void onSuccess();
    private void onError(String err) {System.out.println(err);}
}
