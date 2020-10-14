package com.core;


class incorrectTaskIdTypeException extends Exception{

    @Override
    public String toString(){
        return "Please enter tasks id, not description";
    }
}

