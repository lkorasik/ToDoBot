package com.core;

/**
 * @author Dmitry
 * Исключение, возникающее при попытке удаления Task с несуществующим Id
 */
class NotExistingTaskIndexException extends Exception {

    public NotExistingTaskIndexException(String message) {
        super(message);
    }
}
