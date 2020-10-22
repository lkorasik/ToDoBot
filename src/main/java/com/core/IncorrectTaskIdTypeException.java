package com.core;

/**
 * @author Dmitry
 * Исключение, возникающее при указании некорректного Id при удалении Task
 * (Некорректный Id - это любой тип, который нельзя привести к int)
 */
class IncorrectTaskIdTypeException extends Exception {

    public IncorrectTaskIdTypeException(String message) {
        super(message);
    }
}