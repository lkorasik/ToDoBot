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

## Интерфейс

Для бота Telegram

/help

Выводит список команд с пояснением.

/add

- добавление задачи

/del

- Удаление задачи

/deln 

- Удаление напоминания (Не входит в первую задачу)

/show

- Просмотр задач

/shown

- Просмотр напоминаний (Не входит в первую задачу)

## Примеры

```
User: /addt Task1
Bot: Added Task1
U: /addt Task2
B: Added Task2
U: /showt
B: \ 0) Task1
1) Task2
U: /delt 0
B: Deleted Task1
```

```
User: /addt time=2 Task1
Bot: Added Task1
U: /showt
B: 0) Task1
U: /shown
B: 0) 2 min
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
