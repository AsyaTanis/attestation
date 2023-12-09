package ru.skypro.socks_app.exception;

public class LessThanZeroException extends RuntimeException{
    @Override
    public String getMessage() {
        return "На складе нет такого колличества носков!";
    }
}
