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
    DONE(Constants.TASK_ID_MSG),
    CLEAR(null),
    SHOW_COMPLETED(null),
    SHOW_TODO(null),
    HELP(Constants.HELP_MSG);

    private final String state;

    State(String state) {
        this.state = state;
    }

    public String getStateMessage(){
        return state;
    }
}
