package com.telegrambot;

import com.core.Constants;
import com.core.RequestHandler;
import com.fsm.State;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.ParseException;
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
    private final InlineKeyboardMarkup addDelDoneMenuMarkup = new InlineKeyboardMarkup();
    private final InlineKeyboardMarkup shortMainMenuMarkup = new InlineKeyboardMarkup();
    private final InlineKeyboardButton addButton = new InlineKeyboardButton();
    private final InlineKeyboardButton delButton = new InlineKeyboardButton();
    private final InlineKeyboardButton doneButton = new InlineKeyboardButton();
    private final InlineKeyboardButton showToDoButton = new InlineKeyboardButton();
    private final InlineKeyboardButton showDoneButton = new InlineKeyboardButton();
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
        initAddDelDoneMenuMarkup();
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
        doneButton.setText(Constants.COMPLETED_TASK_BUTTON);
        doneButton.setCallbackData(Constants.COMPLETE_TASK_COMMAND);
        showToDoButton.setText(Constants.SHOW_TASKS_BUTTON);
        showToDoButton.setCallbackData(Constants.SHOW_TODO_TASKS_COMMAND);
        showDoneButton.setText(Constants.SHOW_COMPLETED_TASKS_BUTTON);
        showDoneButton.setCallbackData(Constants.SHOW_COMPLETED_TASKS_COMMAND);
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
        row.add(doneButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(row);
        shortMainMenuMarkup.setKeyboard(rowList);
    }

    /**
     * Настройка разметки основной клавиатуры (Add, Del, Show)
     */
    private void initMainMenuKeyboardMarkup(){
        List<InlineKeyboardButton> upperRow = new ArrayList<>();
        upperRow.add(addButton);
        upperRow.add(delButton);
        upperRow.add(doneButton);
        List<InlineKeyboardButton> downRow = new ArrayList<>();
        downRow.add(showToDoButton);
        downRow.add(showDoneButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(upperRow);
        rowList.add(downRow);

        mainMenuMarkup.setKeyboard(rowList);
    }

    /**
     * Настрйока дополнительной клавиатуры (Cancel)
     */
    private void initAddDelDoneMenuMarkup(){
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(cancelButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(row);
        addDelDoneMenuMarkup.setKeyboard(rowList);
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
            try {
                answer = handleMessage(update);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (update.hasCallbackQuery()){
            try {
                answer = handleCallback(update);
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
    private SendMessage handleMessage(Update update) throws ParseException {
        String message = update.getMessage().getText();

        var answer = new SendMessage();

        answer.setChatId(update.getMessage().getChatId());

        String uid = update.getMessage().getFrom().getId().toString();

        String result = requestHandler.handle(uid, update.getMessage().getChatId().toString(), message, this::Print);

        if ((requestHandler.getFSMState() == State.START) || (requestHandler.getFSMState() == State.LISTEN)){
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
    private SendMessage handleCallback(Update update) throws ParseException {
        SendMessage answer = new SendMessage();
        answer.setChatId(update.getCallbackQuery().getMessage().getChatId());

        String message = update.getCallbackQuery().getData();

        String uid = update.getCallbackQuery().getFrom().getId().toString();

        //String result = requestHandler.handle(uid, message);
        String result = requestHandler.handle(uid, update.getMessage().getChatId().toString(), message, this::Print);

        if((requestHandler.getFSMState() == State.ADD) || (requestHandler.getFSMState() == State.DEL) || (requestHandler.getFSMState() == State.DONE)){
            answer.setReplyMarkup(addDelDoneMenuMarkup);
        }
        if ((requestHandler.getFSMState() == State.SHOW_TODO) || (requestHandler.getFSMState() == State.SHOW_COMPLETED)){
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

    public void Print(String chatId, String message){
        System.out.println("Call");
        var answer = new SendMessage().setText(message).setChatId(chatId);

        try{
            execute(answer);
        }
        catch (TelegramApiException a){
            a.printStackTrace();
        }
    }
}