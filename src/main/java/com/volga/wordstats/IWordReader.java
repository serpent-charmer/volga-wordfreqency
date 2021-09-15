package com.volga.wordstats;

import java.sql.Connection;
import java.sql.SQLException;

public interface IWordReader {
	public void read(Connection con) throws SQLException;
}
