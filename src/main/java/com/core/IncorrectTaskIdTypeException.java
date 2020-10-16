package com.core;

/**
 * @author Dmitry
 * Исключение, возникающее при указании некорректного Id при удалении Task
 * (Некорректный Id - это любой тип, который нельзя привести к int)
 */
class IncorrectTaskIdTypeException extends Exception {
    private String message;

    public IncorrectTaskIdTypeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}