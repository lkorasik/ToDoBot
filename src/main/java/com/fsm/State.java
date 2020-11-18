package com.fsm;

import com.core.Constants;

/**
 * Все возможные состояния конечного автомата
 *
 * @author Lev
 */
public enum State {
    START(Constants.START_MSG),
    EP(Constants.EP_MSG),
    LISTEN(Constants.BOT_WAITING_COMMANDS),
    ADD(Constants.TASK_DESCRIPTION_MSG),
    DEL(Constants.TASK_ID_MSG),
    CLEAR(null),
    SHOW(null),
    HELP(Constants.HELP_MSG);

    private final String state;

    State(String state) {
        this.state = state;
    }

    public String getState(){
        return state;
    }
}
