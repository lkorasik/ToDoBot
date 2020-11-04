package com.telegrambot;

import com.core.Constants;
import com.core.RequestHandler;
import com.fsm.States;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lev
 *
 * Описание бота (методы обработки сообщений)
 */
public class Bot extends TelegramLongPollingBot{
    private final String Token;
    private final String BotUserName;

    private final InlineKeyboardMarkup mainMenuMarkup = new InlineKeyboardMarkup();
    private final InlineKeyboardMarkup addDelMenuMarkup = new InlineKeyboardMarkup();
    private final InlineKeyboardMarkup shortMainMenuMarkup = new InlineKeyboardMarkup();
    private final InlineKeyboardButton addButton = new InlineKeyboardButton();
    private final InlineKeyboardButton delButton = new InlineKeyboardButton();
    private final InlineKeyboardButton showButton = new InlineKeyboardButton();
    private final InlineKeyboardButton cancelButton = new InlineKeyboardButton();

    private final RequestHandler requestHandler = new RequestHandler();

    /**
     * Создать бота
     */
    public Bot(){
        TokenLoader loader = new TokenLoader();

        Token = loader.getToken();
        BotUserName = loader.getName();

        initButtons();
        initMainMenuKeyboardMarkup();
        initAddDelMenuMarkup();
        initShortMainMenuMarkup();
    }

    /**
     * Настройка кнопок для клавиатуры
     */
    private void initButtons(){
        addButton.setText(Constants.ADD_TASK_BUTTON);
        addButton.setCallbackData(Constants.ADD_TASK_COMMAND);
        delButton.setText(Constants.DEL_TASK_BUTTON);
        delButton.setCallbackData(Constants.DELETE_TASK_COMMAND);
        showButton.setText(Constants.SHOW_TASKS_BUTTON);
        showButton.setCallbackData(Constants.SHOW_TASKS_COMMAND);
        cancelButton.setText(Constants.CANCEL_BUTTON);
        cancelButton.setCallbackData(Constants.CANCEL_COMMAND);
    }

    /**
     * Настройка разметки сокращенной основной клавиатуры (Add, Del)
     */
    private void initShortMainMenuMarkup(){
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(addButton);
        row.add(delButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(row);
        shortMainMenuMarkup.setKeyboard(rowList);
    }

    /**
     * Настройка разметки основной клавиатуры (Add, Del, Show)
     */
    private void initMainMenuKeyboardMarkup(){
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(addButton);
        row.add(delButton);
        row.add(showButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(row);
        mainMenuMarkup.setKeyboard(rowList);
    }

    /**
     * Настрйока дополнительной клавиатуры (Cancel)
     */
    private void initAddDelMenuMarkup(){
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(cancelButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(row);
        addDelMenuMarkup.setKeyboard(rowList);
    }

    /**
     * Этот метод вызывается, когда приходит сообщение боту в тг
     * @param update - объект, в ктором есть само сообщение
     *               (ну и еще немного полезной инфы)
     */
    @Override
    public void onUpdateReceived(Update update) {
        var answer = new SendMessage();

        if(update.hasMessage()){
            answer = handleMessage(update);
        }
        if (update.hasCallbackQuery()){
            answer = handleCallback(update);
        }

        try {
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обработка сообщения от пользователя
     * @param update Объект с сообщением
     * @return Ответ пользователю, его надо только отправить
     */
    private SendMessage handleMessage(Update update){
        String message = update.getMessage().getText();

        var answer = new SendMessage();

        answer.setChatId(update.getMessage().getChatId());

        String uid = update.getMessage().getFrom().getId().toString();

        String result = requestHandler.handle(uid, message);

        if ((requestHandler.getFSMState() == States.START) || (requestHandler.getFSMState() == States.LISTEN)){
            answer.setReplyMarkup(mainMenuMarkup);
        }

        answer.setText(result);

        return answer;
    }

    /**
     * Обработка нажатий на клавиатуру
     * @param update
     * @return
     */
    private SendMessage handleCallback(Update update){
        SendMessage answer = new SendMessage();
        answer.setChatId(update.getCallbackQuery().getMessage().getChatId());

        String message = update.getCallbackQuery().getData();

        String uid = update.getCallbackQuery().getFrom().getId().toString();

        String result = requestHandler.handle(uid, message);

        if((requestHandler.getFSMState() == States.ADD) || (requestHandler.getFSMState() == States.DEL)){
            answer.setReplyMarkup(addDelMenuMarkup);
        }
        if (requestHandler.getFSMState() == States.SHOW){
            answer.setReplyMarkup(shortMainMenuMarkup);
        }

        answer.setText(result);

        return answer;
    }

    /**
     * Возвращает имя бота
     * @return Bot's name
     */
    @Override
    public String getBotUsername() {
        return BotUserName;
    }

    /**
     * Возвращает токен доступа к телеграму
     * @return Token
     */
    @Override
    public String getBotToken() {
        return Token;
    }
}