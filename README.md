# chat_bot

Бот для управления заданиями. Задание - это то, что Вы хотите сделать.

## Как запустить бота?

### Консольная версия

Надо запускть com/consolebot/ConsoleBot

### Версия для Телеграма

1) В корне проекта должен лежать файл с именем "Token". Это обычный текстовый файл. В первой строке надо указать токен для подключения к телеграму, а на второй - имя бота.
2) Запускать com/telegrambot/StartBot

## Все возможности бота

0) Идентификация пользователей
1) Сами задания
2) Синхронизация с календарем
3) Напоминания
5) Совместные задания (Можно создавать группы, создавать общие задачи)
6) Статистика по времени, дням, выполненным заданиям

### Первая задача

1) Добавление задач
4) Просмотр всех задач
5) Удаление задач
7) Подключение к телеграму

### Вторая задача

1) Добавить возможность работы нескольких людей однвоременно
3) Сделать работу с телеграмом более интуитивно понятной (добавить кнопки, например)

### Третья задача

1) Добавить напоминания
  - Таймер, т.е. Через n минут бот напишет пользователю.
2) Очистка списка
3) Разграничить удаление задачи и выполнение. (удалить != выполнить)

### Четвертая задача

1) Добавить категории для задач 
2) Возможность начать выполнение задачи (поддержка параллельного выполнения задач)

## Интерфейс

Для бота Telegram

/help

Выводит список команд с пояснением.

/add

- добавление задачи

/del

- Удаление задачи

/done

- Пометить задачу как выполненную

/setnotif

- Поставить напоминание

Форматы:
- dd.MM.yy или dd.MM.yyyy или dd.MM

  Установить дату, когда надо будет напомнить
- t "min"

  Установить таймер на t минут.
- h "hour"

  Установить таймер на h часов.
- h "hour" m "min"

  Установить таймер на h часов и t минут.

/showtodo

- Просмотр задач

/showdone

- Просмотр задач, которые уже выполнены

## Примеры

```
User: /add
Bot: Enter description
User: Task1
Bot: Please enter task category
User: hometasks
Bot: Added task: Task1 [hometasks]
```

```
User: /starttask
Bot: Enter id
U: 0
B: 3..2..1.. GO!
U: /wait
B: Task id
U: 0
B: Ok. Task paused.
U: /starttask
B: Enter id
U: 1
B: 3..2..1.. GO!
U: /wait
B: Task id
U: 1
B: Ok. Task paused
U: /continue
B: Task id
U: 0
B: Ok
```

```
User: /start
Bot: Hello, I'm telegram bot that can help to manage your tasks. There is all commands that you can type to operate with me:
      /add - You can add task. In next message send your task.
      /del - You can delete task. In next message send task's number
      /show - You can see all tasks
      /help - You will see this message
      /cancel - You can use this command if you wnat to cancel action such as add task or delete task.  
      +--------+--------+----------+
      |Add task|Del task|Show tasks|
      +--------+--------+----------+
U: *Click "Add task"*
B: Please enter task description
   +------+
   |Cancel|
   +------+
U: Go to cinema
B: Added task: Go to cinema
  +--------+--------+----------+
  |Add task|Del task|Show tasks|
  +--------+--------+----------+
U: *Click "Show tasks"*
B: Id Описание
    0 Task1
   +--------+--------+
   |Add task|Del task|
   +--------+--------+
```

```
00:00 User: /add
00:01 Bot: Please enter task description
00:02 U: Task1
00:03 B: Added
00:04 U: /settimer
00:05 B: Time?
00:06 U: 10h 3m
00:07 B: I will write you in 10 hours and 3 minutes
10:10 B: Have you forgotten about the task?
You wanted to do this: Task1
```
