package com.core;

/**
 * @author Dmitry
 * Исключение, возникающее при попытке удаления Task с несуществующим Id
 */
class NotExistingTaskIndexException extends Exception {
    String message;

    public NotExistingTaskIndexException(String index) {
        message = String.format("There is no task with id: %s", index);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

