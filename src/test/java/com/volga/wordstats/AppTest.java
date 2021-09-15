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
		
		String s = "клиент 3\n"
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
			
			new WordFileReader(canpath).read(con, ps);
			con.commit();
			
			new WordCacheReader(canpath).read(con, ps);
			
			assertEquals(baos.toString(), s);
			System.out.println("Word frequencies from file1.html");

		} catch (SQLException e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}
}
