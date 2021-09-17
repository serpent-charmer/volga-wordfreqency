package com.volga.wordstats;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.JournalMode;
import org.sqlite.SQLiteConfig.TempStore;

/**
 * Hello world!
 *
 */
public class App {

	public static void cli(String[] args) {
		
		Options options = new Options();

		Option file = Option.builder().argName("file").hasArg().required(true).longOpt("file")
				.desc("specify file to count frequencies").build();

		options.addOption(file);

		HelpFormatter formatter = new HelpFormatter();

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;
		try {
			cmd = parser.parse(options, args);

			String f = cmd.getOptionValue("file");
			if (f == null) {
				formatter.printHelp("wordfreq", options);
				System.exit(0);
			}

			String canpath = null;
			try {
				File fc = new File(f);
				if(!fc.exists())
					throw new IOException();
				canpath = fc.getCanonicalPath();
			} catch (IOException e) {
				System.out.println("File " + f + " is not found");
				System.exit(1);
			}

			File dbs = new File("sample.db");

			boolean fromCache = false;

			if (!dbs.exists()) {

				try (Connection con = DriverManager.getConnection("jdbc:sqlite:sample.db")) {

					Statement statement = con.createStatement();

					statement.executeUpdate(
							"create table wordstat(id integer, filepath string, filehash string, word string, frequency integer, primary key (id));");

					statement.close();
					
				} catch (SQLException e) {
					LoggerInstance.logger.error("Error while setting up db");
					LoggerInstance.logger.catching(e);
					System.out.println("Error while setting up db");
					System.exit(1);
				}
			}

			
			SQLiteConfig config = new SQLiteConfig();
			config.setTempStore(TempStore.FILE);
			config.setJournalMode(JournalMode.WAL);
			
			try (Connection con = DriverManager.getConnection("jdbc:sqlite:sample.db", config.toProperties())) {

				PreparedStatement stmt = con
						.prepareStatement("select distinct filehash from wordstat where filepath = ?");

				stmt.setString(1, canpath);

				try(ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {

						String fhash = rs.getString("filehash");
						String other_fhash = Utils.getMd5Hash(canpath);
						if (fhash.equals(other_fhash)) {
							fromCache = true;
						}
						
						System.out.println(fhash + " " + other_fhash + " " + fromCache);
						
					}
				} finally  {
					stmt.close();
				}
				
				if(!fromCache) {
					PreparedStatement del = con.prepareStatement("delete from wordstat where filepath = ?");
					del.setString(1, canpath);
					del.executeUpdate();
					del.close();
					LoggerInstance.logger.info("Reading from file");
					new WordFileReader(canpath).read(con, System.out);
					new WordCacheReader(canpath).read(con, System.out);
				} else {
					LoggerInstance.logger.info("Reading from cache");
					new WordCacheReader(canpath).read(con, System.out);
				}

			} catch (SQLException e) {
				LoggerInstance.logger.error("Error while setting up db");
				LoggerInstance.logger.catching(e);
				System.out.println("Error while getting file hash");
				System.exit(1);
			}

		} catch (ParseException e) {
			formatter.printHelp("wordfreq", options);
		}

	}

	public static void main(String[] args) {
		
		try {
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new InternalError("VM does not support mandatory encoding UTF-8");
		}
		
		cli(args);
	}
}
