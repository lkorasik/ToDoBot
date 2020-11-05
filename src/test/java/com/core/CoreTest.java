package com.core;

import com.authentication.Authenticator;
import org.junit.*;
import java.io.*;
import java.util.Scanner;

/**
 * Тестирование класса Core
 * @author Dmitry
 */
public class CoreTest {
    private Core core;
    private String user;
    private final String test_filename = "testfile";

    @Before
    public void setUp(){
        core = new Core(test_filename);
        Authenticator auth = new Authenticator();
        auth.authenticate("user");
        user = auth.getUserId();
    }


    /**
     * Удаление файла после прохождения каждого теста
     */
    @After
    public void tearDown(){
        File testFile = new File(test_filename);
        if (testFile.exists()){
            testFile.delete();
        }
        else {
            System.out.printf("Cannot to delete file `%s%`n", test_filename);
        }
    }

    /**
     * Проверка, что файл с данными пользователей создаеться и имеет в себе данные
     */
    @Test
    public void fileWithUsersIsCreatingTest() throws FileNotFoundException {
        core.addTask(user, "DO");
        File f = new File(test_filename);
        Assert.assertTrue(f.isFile());
        Scanner scanner = new Scanner(f);
        String user_data = scanner.nextLine();
        Assert.assertEquals("{\"user\":[{\"description\":\"DO\"}]}", user_data) ;
    }


    /**
     * Проверка десереализцаии с JSON файла
     */
    @Test
    public void uploadTasksFromJsonTest() {
        try {
            File file = new File(test_filename);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(String.format("{\"%s\":[{\"description\": \"Task1\"}]}", user).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        core = new Core(test_filename);
        String result = core.getTasks(user);
        Assert.assertEquals("Id\tОписание\n0\tTask1", result);
    }

    /**
     * Попытка удаления существующей задачи
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test
    public void deleteExistingTaskTest() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.addTask(user,"First");
        core.deleteTask(user,"0");
        String result = core.getTasks(user);
        Assert.assertEquals("Congratulations! You don't have any tasks yet", result);
    }

    /**
     * Попытка удаления задачи, Id которой не записан в списке задач
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test(expected = NotExistingTaskIndexException.class)
    public void deleteNotExistingTaskTest() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.addTask(user, "Do");
        core.deleteTask(user,"2");
    }

    /**
     * Попытка удаления задачи с указанием некорректного Id
     * @throws IncorrectTaskIdTypeException при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    @Test(expected = IncorrectTaskIdTypeException.class)
    public void deleteTaskWithIncorrectArgumentTest() throws IncorrectTaskIdTypeException, NotExistingTaskIndexException {
        core.deleteTask(user,"do");
    }

    /**
     * Проверка списка задач без добавления в него чего-либо
     */
    @Test
    public void showEmptyTaskListTest(){
        String result = core.getTasks(user);
        Assert.assertEquals("Congratulations! You don't have any tasks yet", result);
    }

    /**
     * Проверка списка задач после добавления одной задачи
     */
    @Test
    public void showNotEmptyTaskListTest(){
        core.addTask(user,"Do something");
        String result = core.getTasks(user);
        Assert.assertEquals("Id\tОписание\n0\tDo something", result);
    }

    /**
     * Проверка того, что у каждого пользователя свои и только свои задачи
     */
    @Test
    public void separatedTasksByUsersTest(){
        core.addTask(user, "Do work");
        core.addTask("newUser", "Do another work");
        String firstUserResult = core.getTasks(user);
        String secondUserResult = core.getTasks("newUser");
        Assert.assertEquals("Id\tОписание\n0\tDo work", firstUserResult);
        Assert.assertEquals("Id\tОписание\n0\tDo another work", secondUserResult);
    }
}
