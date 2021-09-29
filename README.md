# volga-wordfrequency

[![CircleCI](https://circleci.com/gh/serpent-charmer/volga-wordfrequency/tree/master.svg?style=shield)](https://circleci.com/gh/serpent-charmer/volga-wordfrequency/?branch=master)

[Текст задания](https://www.volga-it.org/wp-content/plugins/wp-olymp/files/6dc231a7cf2dbaa94ed5b5a40590d025.pdf)

Архитектура проекта состоит из двух классов, WordFileReader и WordCacheReader, c иерархией вида WordReader->IWordReader.  
Присутствует примитивный Inversion of Control: классы для подсчета слов и чтения кэша не создают зависимости сами,  
а получают их извне в методе read.

```
public interface IWordReader {
	public void read(Connection con, PrintStream printer) throws SQLException;
}
```

<img src="resources/hierarchy.png"></img>

Ниже представлены ссылки на тесты и CI, содержащий собранный JAR-файл.  
Для запуска программы требуется Java 16.

[Код тестов](https://github.com/serpent-charmer/volga-wordfrequency/blob/master/src/test/java/com/volga/wordstats/AppTest.java)  
[Jar](https://github.com/serpent-charmer/volga-wordfrequency/releases/download/alpha/wordfreq.jar)

Запуск тестов:  
`mvn test`

Сборка JAR-файла:  
`mvn clean compile assembly:single`

Использование:
`java -jar wordfreq.jar --file=путь_до_файла`  

Перед использованием в консоли рекомендуется поменять кодировку на UTF-8:  
`chcp 65001` в cmd  
`[Console]::OutputEncoding = [Text.UTF8Encoding]::UTF8` в pshell  

Алгоритм работы программы:  
1. Подсчет частоты слов и запись в кэш(SQLite)  
2. Eсли файл не изменился, то вывод набора слов и их частоты в порядке убывания
