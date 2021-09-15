package com.volga.wordstats;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

public interface IWordReader {
	public void read(Connection con, PrintStream printer) throws SQLException;
}
