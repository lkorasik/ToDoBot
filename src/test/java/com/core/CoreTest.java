package com.core;

import com.fsm.State;
import org.junit.*;
import java.io.*;
import java.util.Scanner;

/**
 * Тестирование класса Core
 * @author Dmitry
 */
public class CoreTest {
    private Core core;
    private String userId;
    private static final String test_filename = "testfile.json";

    @Before
    public void setUp() {
        core = new Core(test_filename);
        userId = "user";
    }


    /**
     * Удаление содержимого после каждого прохождения теста.
     *
     * Необходимо для того, чтобы результаты предудыщих тестов не накладывались на текущие.
     */
    @After
    public void tearDown() throws FileNotFoundException {
        var pw = new PrintWriter(test_filename);
        pw.close();
    }

    @AfterClass
    public static void deleteFile() {
        var testfile = new File(test_filename);
        if (testfile.exists()) {
            testfile.deleteOnExit();
        }
    }

    /**
     * Проверка, что файл с данными пользователей создаеться и в него записываются все поля объекта класса User
     */
    @Test
    public void fileWithUsersIsCreatingTest() throws FileNotFoundException {
        var firstTaskDescription = "DO";
        var secondTaskDescription = "SOMETHING";
        core.createUser(userId);
        core.addTask(userId, firstTaskDescription);
        core.addTask(userId, secondTaskDescription);
        core.setUserFSMState(userId, State.LISTEN);
        File f = new File(test_filename);
        Assert.assertTrue(f.isFile());
        Scanner scanner = new Scanner(f);
        String user_data = scanner.nextLine();
        Assert.assertEquals(String.format("{\"%s\":{\"toDoTasks\":[{\"description\":\"%s\"},{\"description\":\"%s\"}],\"completedTasks\":[],\"fsmState\":\"LISTEN\"}}",
                userId, firstTaskDescription, secondTaskDescription), user_data);
    }


    /**
     * Проверка десереализцаии всех типов заданий пользователя с JSON файла
     */
    @Test
    public void uploadTasksFromJsonTest() {
        try {
            var file = new File(test_filename);
            var toDoTaskDescription = "do something";
            var completedTaskDescription = "completed task";
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(String.format(
                    "{\"%s\":{\"toDoTasks\":[{\"description\":\"%s\"}]," +
                            "\"completedTasks\":[{\"description\":\"%s\"}]," +
                            "\"fsmState\":\"NOTIFICATION\"}}",
                    userId, toDoTaskDescription, completedTaskDescription).getBytes());
            core = new Core(test_filename);
            String formattedToDoTasks = core.getFormattedToDoTasks(userId);
            String formattedCompletedTasks = core.getFormattedCompletedTasksString(userId);
            Assert.assertEquals(String.format("Id\tОписание\n0\t%s", toDoTaskDescription), formattedToDoTasks);
            Assert.assertEquals(String.format("Id\tОписание\n0\t%s", completedTaskDescription), formattedCompletedTasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка корректной работы Core, если файл с данными пользователей отсутствует
     */
    @Test
    public void runCoreWithoutUsersFile(){
        core.createUser(userId);
        var todoTasks = core.getFormattedToDoTasks(userId);
        Assert.assertEquals("Congratulations! You don't have any tasks!", todoTasks);
        var doneTasks = core.getFormattedCompletedTasksString(userId);
        Assert.assertEquals("You haven't done any tasks yet", doneTasks);
    }

    /**
     * Попытка удаления существующей задачи
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test
    public void deleteExistingTaskTest() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.createUser(userId);
        core.addTask(userId, "test" );
        core.deleteTask(userId, "0");
        var result = core.getFormattedToDoTasks(userId);
        Assert.assertEquals("Congratulations! You don't have any tasks!", result);
    }

    /**
     * Попытка удаления несуществующей задачи
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test(expected = NotExistingTaskIndexException.class)
    public void deleteNotExistingTaskTest() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.createUser(userId);
        core.addTask(userId, "Do");
        core.deleteTask(userId,"2");
    }

    /**
     * Попытка удаления задачи с указанием некорректного Id
     *
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test(expected = IncorrectTaskIdTypeException.class)
    public void deleteTaskWithIncorrectArgumentTest() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.createUser(userId);
        core.deleteTask(userId,"do");
    }


    /**
     * Проверка, что при отметке задачи как выполненной, она удаляется из списка на выполнение и
     * добавляется в список выполненных
     *
     * @throws NotExistingTaskIndexException при попытке удаления задачи с некорректным Id
     * @throws IncorrectTaskIdTypeException при попытке удаления несуществующей задачи
     */
    @Test
    public void completeTaskTest() throws NotExistingTaskIndexException, IncorrectTaskIdTypeException {
        var taskDescription = "do something";
        core.createUser(userId);
        core.addTask(userId, taskDescription);
        core.completeTask(userId, "0");
        var todoTasks = core.getFormattedToDoTasks(userId);
        var completedTasks = core.getFormattedCompletedTasksString(userId);
        Assert.assertEquals("Congratulations! You don't have any tasks!", todoTasks);
        Assert.assertEquals(String.format("Id\tОписание\n0\t%s", taskDescription), completedTasks);
    }

    /**
     * Проверка списка задач без добавления в него чего-либо
     */
    @Test
    public void showEmptyTodoTaskListTest(){
        core.createUser(userId);
        String result = core.getFormattedToDoTasks(userId);
        Assert.assertEquals("Congratulations! You don't have any tasks!", result);
    }

    /**
     * Проверка списка выполненных задач без совершения операций над задачами
     */
    @Test
    public void showEmptyCompletedTaskList(){
        core.createUser(userId);
        var result = core.getFormattedCompletedTasksString(userId);
        Assert.assertEquals("You haven't done any tasks yet", result);
    }

    /**
     * Проверка списка задач после добавления одной задачи
     */
    @Test
    public void showNotEmptyTaskListTest(){
        var taskDescription = "Do something";
        core.createUser(userId);
        core.addTask(userId,taskDescription);
        String result = core.getFormattedToDoTasks(userId);
        Assert.assertEquals(String.format("Id\tОписание\n0\t%s", taskDescription), result);
    }


    /**
     * Проверка того, что у каждого пользователя свои и только свои задачи
     */
    @Test
    public void separatedTasksByUsersTest(){
        var firstUserName = userId;
        var secondUserName = "newUser";
        core.createUser(firstUserName);
        core.createUser(secondUserName);
        core.addTask(firstUserName, "Do work");
        core.addTask(secondUserName, "Do another work");
        var firstUserResult = core.getFormattedToDoTasks(firstUserName);
        var secondUserResult = core.getFormattedToDoTasks(secondUserName);
        Assert.assertEquals("Id\tОписание\n0\tDo work", firstUserResult);
        Assert.assertEquals("Id\tОписание\n0\tDo another work", secondUserResult);
    }

    /**
     *
     */
    @Test
    public void clearTaskListsTest() throws NotExistingTaskIndexException, IncorrectTaskIdTypeException {
        core.createUser(userId);
        var firstTaskDescription = "first task";
        var secondTaskDescription = "second task";
        core.addTask(userId, firstTaskDescription);
        core.addTask(userId, secondTaskDescription);
        core.completeTask(userId,"1");
        core.clearAllTaskLists(userId);
        var formattedTodoTasks = core.getFormattedToDoTasks(userId);
        Assert.assertEquals("Congratulations! You don't have any tasks!", formattedTodoTasks);
        var formattedCompletedTasks = core.getFormattedCompletedTasksString(userId);
        Assert.assertEquals("You haven't done any tasks yet", formattedCompletedTasks);
    }
}
