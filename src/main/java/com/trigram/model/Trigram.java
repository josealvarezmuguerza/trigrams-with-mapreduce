package com.trigram.model;

import org.apache.log4j.Logger;

public class Trigram {
	private String key;
	private String[] values;
	private int offset;
	private static final Logger LOG = Logger.getLogger(Trigram.class);

	public Trigram(final String key, final String values) {
		this.key = key;
		this.values = values.split("\\s");
		this.offset = 0;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		String result = null;
		try {
			result = values[this.offset];
		} catch (ArrayIndexOutOfBoundsException outOfIndexException) {
			LOG.warn("No values tor given key:" + this.key);
		}
		return result;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public int getOffset() {
		return offset;
	}

	public void incrementOffset() {
		this.offset = (this.offset == this.values.length - 1) ? 0
				: this.offset + 1;
	}
}
