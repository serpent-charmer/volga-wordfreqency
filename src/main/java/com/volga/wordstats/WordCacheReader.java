package com.volga.wordstats;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WordCacheReader extends WordReader {

	WordCacheReader(String path) {
		super(path);
	}

	public void read(Connection con, PrintStream printer) throws SQLException {

		Statement statement = con.createStatement();
		statement.setQueryTimeout(30);

		String filehash = Utils.getMd5Hash(this.path);
		PreparedStatement query = con
				.prepareStatement("select * from wordstat where filehash = ? order by frequency desc;");

		query.setString(1, filehash);

		try (ResultSet rs = query.executeQuery()) {
			while (rs.next()) {
				printer.printf("%s %s\n", rs.getString("word"), rs.getInt("frequency"));
				LoggerInstance.logger.info(rs.getString("word") + " " + rs.getInt("frequency"));
			}
		}

	}
}
