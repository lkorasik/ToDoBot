package com.fsm;

import com.core.Constants;

/**
 * Все возможные состояния конечного автомата
 *
 * @author Lev
 */
public enum States {
    START(), EP(), LISTEN(), ADD(), DEL(), SHOW(), HELP();

    /**
     * Получить сообщение для пользователя с учетом текщего состояния автомата
     * @param state Состояние, по которому надо получить сообщение
     * @return Сообщение для пользователя
     */
    public static String getMessageForState(States state){
        String res = null;

        switch (state) {
            case START:
                res = Constants.START_MSG;
                break;
            case HELP:
                res = Constants.HELP_MSG;
                break;
            case ADD:
                res = Constants.TASK_DESCRIPTION_MSG;
                break;
            case DEL:
                res = Constants.TASK_ID_MSG;
                break;
            case EP:
                res = Constants.EP_MSG;
                break;
        }

        return res;
    }
}
