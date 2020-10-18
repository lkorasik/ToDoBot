package com.core;

/**
 * @author Dmitry
 * Исключение, возникающее при попытке удаления Task с несуществующим Id
 */
class NotExistingTaskIndexException extends Exception {
    private String message;

    public NotExistingTaskIndexException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

