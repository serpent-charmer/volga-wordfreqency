package com.volga.wordstats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.stream.Stream;

import org.jsoup.Jsoup;

public class WordFileReader extends WordReader {

	WordFileReader(String path) {
		super(path);
	}

	public void read(Connection con) throws SQLException {

		Statement statement = con.createStatement();
		statement.setQueryTimeout(30);

		PreparedStatement insrt = con.prepareStatement("insert into wordstat values(NULL, ?, ?, ?, ?);");
		PreparedStatement upd = con.prepareStatement("update wordstat set frequency = frequency+1 where id = ?;");
		PreparedStatement check = con.prepareStatement("select id from wordstat where word = ?;");

		String filehash = Utils.getMd5Hash(this.path);

		try (Stream<String> stream = Files.lines(Paths.get(this.path))) {
			stream.map(s -> {
				String rs = Jsoup.parse(s).text().toLowerCase();
				Stream<String> strm = Arrays.stream(rs.replaceAll("[^a-zA-Zа-яА-Я’]", " ").split(" "));
				return strm.filter(s1 -> s1.length() > 0);
			}).flatMap(s -> s).forEach((v) -> {

				try {
					check.setString(1, v);
					try (ResultSet rs = check.executeQuery()) {
						if (rs.next()) {
							upd.setInt(1, rs.getInt("id"));
							upd.executeUpdate();
						} else {
							insrt.setString(1, this.path);
							insrt.setString(2, filehash);
							insrt.setString(3, v);
							insrt.setInt(4, 1);
							insrt.executeUpdate();
						}
					}

				} catch (SQLException e) {
					LoggerInstance.logger.catching(e);
				}
			});

		} catch (IOException e) {
			LoggerInstance.logger.catching(e);
		}
	}
}
