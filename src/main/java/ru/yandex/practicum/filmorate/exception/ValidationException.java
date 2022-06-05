package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends RuntimeException{
    private String path;

    public ValidationException(final String message){
        super(message);
    }

    public ValidationException(final String message,String path) {
        super(message);
        this.path=path;
    }

    public String getPath(){
        return getMessage() + " = " + path;
    }
}
