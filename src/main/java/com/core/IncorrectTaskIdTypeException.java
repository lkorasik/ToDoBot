package com.core;

/**
 * @author Dmitry
 * Исключение, возникающее при указании некорректного Id при удалении Task
 * (Некорректный Id - это любой тип, который нельзя привести к int)
 */
class IncorrectTaskIdTypeException extends Exception {
    String message;

    public IncorrectTaskIdTypeException() {
        message = "Please enter tasks id, not description";
    }

    @Override
    public String getMessage() {
        return message;
    }
}