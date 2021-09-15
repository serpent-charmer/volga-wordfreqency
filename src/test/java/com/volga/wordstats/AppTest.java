package com.volga.wordstats;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Rigorous Test :-)
	 * @return 
	 */
	
	public void testWith(String filepath, String s) {
		String canpath = null;
		try {
			canpath = new File(filepath).getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (Connection con = DriverManager.getConnection("jdbc:sqlite::memory:")) {

			con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			con.setAutoCommit(false);

			Statement statement = con.createStatement();
			statement.executeUpdate(
					"create table wordstat(id integer, filepath string, filehash string, word string, frequency integer, primary key (id));");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			PrintStream ps = new PrintStream(baos);
			
			new WordFileReader(canpath).read(con, ps);
			con.commit();
			
			new WordCacheReader(canpath).read(con, ps);
			
			assertEquals(baos.toString(), s);
//			System.out.println(baos.toString());

		} catch (SQLException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	@Test
	public void shouldAssertTrueFirstHTMLFile() {
		
		String fs = "клиент 3\n"
				+ "сервер 3\n"
				+ "по 3\n"
				+ "на 3\n"
				+ "часто 2\n"
				+ "с 2\n"
				+ "помощью 2\n"
				+ "преимущества 2\n"
				+ "к 2\n"
				+ "архитектура 1\n"
				+ "современное 1\n"
				+ "программное 1\n"
				+ "обеспечение 1\n"
				+ "реализовано 1\n"
				+ "архитектурной 1\n"
				+ "модели 1\n"
				+ "такое 1\n"
				+ "представлено 1\n"
				+ "разлчиными 1\n"
				+ "компонентами 1\n"
				+ "размещенными 1\n"
				+ "разных 1\n"
				+ "вычислительных 1\n"
				+ "машинах 1\n"
				+ "которые 1\n"
				+ "объединены 1\n"
				+ "общей 1\n"
				+ "вычислительной 1\n"
				+ "сетью 1\n"
				+ "функционирующей 1\n"
				+ "различных 1\n"
				+ "протоколов 1\n"
				+ "говоря 1\n"
				+ "об 1\n"
				+ "архитектуре 1\n"
				+ "выделяют 1\n"
				+ "следующие 1\n"
				+ "и 1\n"
				+ "недостатки 1\n"
				+ "недо 1\n"
				+ "снижение 1\n"
				+ "требований 1\n"
				+ "вычислительным 1\n"
				+ "машинам 1\n"
				+ "которых 1\n"
				+ "функционируют 1\n"
				+ "клиенты 1\n"
				+ "возрастают 1\n"
				+ "требования 1\n"
				+ "надежности 1\n"
				+ "комплексу 1\n"
				+ "вычислетельной 1\n"
				+ "сети 1\n"
				+ "более 1\n"
				+ "высокий 1\n"
				+ "уровень 1\n"
				+ "защищенности 1\n"
				+ "данны 1\n"
				+ "требуются 1\n"
				+ "дополнительные 1\n"
				+ "затраты 1\n"
				+ "обслуживание 1\n"
				+ "комплекса 1\n";

		
		
		
		
		
		testWith("./resources/file1.html", fs);

	}
	
	
	@Test
	public void shouldAssertTrueSecondHTMLFile() {
		
		String fs = "осуществляется 2\n"
				+ "с 2\n"
				+ "помощью 2\n"
				+ "архитектура 1\n"
				+ "клиент 1\n"
				+ "сервер 1\n"
				+ "клиентом 1\n"
				+ "как 1\n"
				+ "правило 1\n"
				+ "называют 1\n"
				+ "компонент 1\n"
				+ "посылающий 1\n"
				+ "запросы 1\n"
				+ "серверу 1\n"
				+ "по 1\n"
				+ "сети 1\n"
				+ "отправка 1\n"
				+ "запросов 1\n"
				+ "различных 1\n"
				+ "протоколов 1\n"
				+ "наиболее 1\n"
				+ "популярным 1\n"
				+ "протоколом 1\n"
				+ "служит 1\n"
				+ "http 1\n"
				+ "https 1\n"
				+ "изначально 1\n"
				+ "он 1\n"
				+ "был 1\n"
				+ "предназначен 1\n"
				+ "для 1\n"
				+ "передачи 1\n"
				+ "гипертекстовых 1\n"
				+ "документов 1\n"
				+ "в 1\n"
				+ "формате 1\n"
				+ "html 1\n"
				+ "но 1\n"
				+ "сейчас 1\n"
				+ "данного 1\n"
				+ "протокола 1\n"
				+ "обмен 1\n"
				+ "произвольными 1\n"
				+ "данными 1\n";
		
		testWith("./resources/file2.html", fs);
	}
	
	@Test
	public void shouldAssertTrueThirdHTMLFile() {
		
		String fs = "компонентов 6\n"
				+ "системы 5\n"
				+ "rest 4\n"
				+ "к 3\n"
				+ "на 3\n"
				+ "для 3\n"
				+ "representational 2\n"
				+ "state 2\n"
				+ "transfer 2\n"
				+ "архитектуры 2\n"
				+ "и 2\n"
				+ "с 2\n"
				+ "масштабируемость 2\n"
				+ "при 2\n"
				+ "это 1\n"
				+ "современный 1\n"
				+ "подход 1\n"
				+ "разработке 1\n"
				+ "клиент 1\n"
				+ "серверных 1\n"
				+ "приложений 1\n"
				+ "свойства 1\n"
				+ "которые 1\n"
				+ "зависят 1\n"
				+ "от 1\n"
				+ "ограничений 1\n"
				+ "наложенных 1\n"
				+ "производительность 1\n"
				+ "взаимодействие 1\n"
				+ "может 1\n"
				+ "являться 1\n"
				+ "доминирующим 1\n"
				+ "фактором 1\n"
				+ "производительности 1\n"
				+ "эффективности 1\n"
				+ "сети 1\n"
				+ "точки 1\n"
				+ "зрения 1\n"
				+ "пользователя 1\n"
				+ "обеспечения 1\n"
				+ "большого 1\n"
				+ "числа 1\n"
				+ "взаимодействий 1\n"
				+ "рой 1\n"
				+ "филдинг 1\n"
				+ "один 1\n"
				+ "из 1\n"
				+ "главных 1\n"
				+ "авторов 1\n"
				+ "спецификации 1\n"
				+ "протокола 1\n"
				+ "http 1\n"
				+ "описывает 1\n"
				+ "влияние 1\n"
				+ "следующим 1\n"
				+ "образом 1\n"
				+ "простота 1\n"
				+ "унифицированного 1\n"
				+ "интерфейса 1\n"
				+ "открытость 1\n"
				+ "возможным 1\n"
				+ "изменениям 1\n"
				+ "удовлетворения 1\n"
				+ "изменяющихся 1\n"
				+ "потребностей 1\n"
				+ "даже 1\n"
				+ "работающем 1\n"
				+ "приложении 1\n"
				+ "прозрачность 1\n"
				+ "связей 1\n"
				+ "между 1\n"
				+ "компонентами 1\n"
				+ "сервисных 1\n"
				+ "служб 1\n"
				+ "переносимость 1\n"
				+ "путем 1\n"
				+ "перемещения 1\n"
				+ "программного 1\n"
				+ "кода 1\n"
				+ "вместе 1\n"
				+ "данными 1\n"
				+ "над 1\n"
				+ "жность 1\n"
				+ "выражающаяся 1\n"
				+ "в 1\n"
				+ "устойчивости 1\n"
				+ "отказам 1\n"
				+ "уровне 1\n"
				+ "наличии 1\n"
				+ "отказов 1\n"
				+ "отдельных 1\n"
				+ "соединений 1\n"
				+ "или 1\n"
				+ "данных 1\n"
				+ "материалы 1\n"
				+ "взяты 1\n"
				+ "chapter 1\n"
				+ "of 1\n"
				+ "roy 1\n"
				+ "fielding’s 1\n"
				+ "dissertation 1\n";
		
		testWith("./resources/file3.html", fs);
	}
}
