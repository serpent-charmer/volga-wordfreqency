package com.volga.wordstats;

public abstract class WordReader implements IWordReader {
	protected String path;

	WordReader(String path) {
		this.path = path;
	}
}
