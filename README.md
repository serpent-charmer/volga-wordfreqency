# volga-wordfrequency

[![CircleCI](https://circleci.com/gh/serpent-charmer/volga-wordfrequency/tree/master.svg?style=shield)](https://circleci.com/gh/serpent-charmer/volga-wordfrequency/?branch=master)

Ниже представлены ссылки на тест и на CI, содержащий собранный JAR-файл.  
Для запуска программы требуется Java 16.

[Код теста](https://github.com/serpent-charmer/volga-wordfrequency/blob/master/src/test/java/com/volga/wordstats/AppTest.java)  
[Jar](https://25-406640303-gh.circle-artifacts.com/0/target/volga-1.0-SNAPSHOT-jar-with-dependencies.jar)

Запуск тестов: `mvn test`

Сборка JAR-файла: `mvn clean compile assembly:single`

Использование:

`java -jar volga-1.0-SNAPSHOT-jar-with-dependencies.jar --file=путь_до_файла`  


Перед использованием в консоли рекомендуется поменять кодировку на UTF-8:

`chcp 65001` в cmd  
`[Console]::OutputEncoding = [Text.UTF8Encoding]::UTF8` в pshell  

При первом запуске программа подсчитает частоту слов и запишет их в кэш(SQLite).  
В следующий раз, если файл тот же самый и он не изменился, то выведется набор слов и их частота в порядке убывания.
