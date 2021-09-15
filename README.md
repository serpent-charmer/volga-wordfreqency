# volga-wordfrequency

[![CircleCI](https://circleci.com/gh/serpent-charmer/volga-wordfrequency/tree/master.svg?style=shield)](https://circleci.com/gh/serpent-charmer/volga-wordfrequency/?branch=master)

Ниже представлены ссылки на CI, содержащие успешно пройденный тест и собранный JAR-файл.  
Для запуска программы требуется Java 16.

[Тест](https://app.circleci.com/pipelines/github/serpent-charmer/volga-wordfrequency/17/workflows/5bc899f8-defa-443b-8b48-dd0399af181c/jobs/21)  
[Код теста](https://github.com/serpent-charmer/volga-wordfrequency/blob/master/src/test/java/com/volga/wordstats/AppTest.java)  
[Jar](https://app.circleci.com/pipelines/github/serpent-charmer/volga-wordfrequency/17/workflows/5bc899f8-defa-443b-8b48-dd0399af181c/jobs/20/artifacts)

Использование:

`java -jar volga-1.0-SNAPSHOT-jar-with-dependencies.jar --file=путь_до_файла`  



Перед использованием в консоли рекомендуется поменять кодировку на UTF-8:

`chcp 65001` в cmd  
`[Console]::OutputEncoding = [Text.UTF8Encoding]::UTF8` в pshell  

При первом запуске программа подсчитает частоту слов и запишет их в кэш(SQLite).  
В следующий раз, если файл тот же самый и он не изменился, то выведется набор слов и их частота в порядке убывания.
