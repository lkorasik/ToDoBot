package com.core;

class notExistingTaskIndexException extends Exception {
    String index;

    public notExistingTaskIndexException(String index){
        this.index = index;
    }

    @Override
    public String toString() {
        return String.format("There is no task with id: %s", index);
    }
}

