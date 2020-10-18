package com.core;

/**
 * @author Dmitry
 * Исключение, возникающее при попытке удаления Task с несуществующим Id
 */
class NotExistingTaskIndexException extends Exception {
    private String message;

    public NotExistingTaskIndexException(String index) {
        message = String.format(Constants.NOT_EXISTING_TASK_ID_EXCEPTION_MSG, index);
    }

    @Override
    public String getMessage() {
        return message;
    }
}

