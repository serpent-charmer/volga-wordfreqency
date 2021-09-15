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
	 */
	
	
	
	
	@Test
	public void shouldAnswerWithTrue() {
		
		String s = "клиент 3\r\n"
				+ "сервер 3\r\n"
				+ "по 3\r\n"
				+ "на 3\r\n"
				+ "часто 2\r\n"
				+ "с 2\r\n"
				+ "помощью 2\r\n"
				+ "преимущества 2\r\n"
				+ "к 2\r\n"
				+ "архитектура 1\r\n"
				+ "современное 1\r\n"
				+ "программное 1\r\n"
				+ "обеспечение 1\r\n"
				+ "реализовано 1\r\n"
				+ "архитектурной 1\r\n"
				+ "модели 1\r\n"
				+ "такое 1\r\n"
				+ "представлено 1\r\n"
				+ "разлчиными 1\r\n"
				+ "компонентами 1\r\n"
				+ "размещенными 1\r\n"
				+ "разных 1\r\n"
				+ "вычислительных 1\r\n"
				+ "машинах 1\r\n"
				+ "которые 1\r\n"
				+ "объединены 1\r\n"
				+ "общей 1\r\n"
				+ "вычислительной 1\r\n"
				+ "сетью 1\r\n"
				+ "функционирующей 1\r\n"
				+ "различных 1\r\n"
				+ "протоколов 1\r\n"
				+ "говоря 1\r\n"
				+ "об 1\r\n"
				+ "архитектуре 1\r\n"
				+ "выделяют 1\r\n"
				+ "следующие 1\r\n"
				+ "и 1\r\n"
				+ "недостатки 1\r\n"
				+ "недо 1\r\n"
				+ "снижение 1\r\n"
				+ "требований 1\r\n"
				+ "вычислительным 1\r\n"
				+ "машинам 1\r\n"
				+ "которых 1\r\n"
				+ "функционируют 1\r\n"
				+ "клиенты 1\r\n"
				+ "возрастают 1\r\n"
				+ "требования 1\r\n"
				+ "надежности 1\r\n"
				+ "комплексу 1\r\n"
				+ "вычислетельной 1\r\n"
				+ "сети 1\r\n"
				+ "более 1\r\n"
				+ "высокий 1\r\n"
				+ "уровень 1\r\n"
				+ "защищенности 1\r\n"
				+ "данны 1\r\n"
				+ "требуются 1\r\n"
				+ "дополнительные 1\r\n"
				+ "затраты 1\r\n"
				+ "обслуживание 1\r\n"
				+ "комплекса 1\r\n";

		String f = "./resources/file1.html";
		String canpath = null;
		try {
			canpath = new File(f).getCanonicalPath();
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
			PrintStream old = System.out;
			System.setOut(ps);
			
			new WordFileReader(canpath).read(con);
			con.commit();
			
			new WordCacheReader(canpath).read(con);
			
			System.out.flush();
			System.setOut(old);

			assertEquals(baos.toString(), s);
			
			System.out.println("Word frequencies from file1.html");

		} catch (SQLException e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}
}
