package com.trigram.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.trigram.model.Trigram;

public class TrigramDao {
	private Random generator = new Random();
	private BufferedReader bReader = null;
	private final Map<String, Trigram> trigramMap = new HashMap<String, Trigram>();
	private static final Logger LOG = Logger.getLogger(TrigramDao.class);

	public TrigramDao(final String outputPath) {
		LOG.debug("##### Running : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		String filename = outputPath + "MR/part-r-00000";

		try {

			BufferedReader bReader = null;
			bReader = new BufferedReader(new FileReader(filename));

			if (bReader.ready()) {
				String line = null;
				Trigram trigram = null;
				while ((line = bReader.readLine()) != null) {
					try {
						String trigramValues[] = line.split("\\t");
						trigram = new Trigram(trigramValues[0],
								trigramValues[1]);
						trigramMap.put(trigram.getKey(), trigram);

					} catch (Exception e) {
						System.err.println("#### Error loading trigram : "
								+ line + " - " + e.getMessage());
						LOG.error("#### Error loading trigram : " + line
								+ " - " + e.getMessage());
					}

				}
				bReader.close();
			}
		} catch (Exception io) {
			if (bReader == null) {
				LOG.error("#### Unable to find trigrams file: " + filename
						+ " - " + io.getMessage());
				System.err.println("#### Unable to find trigrams file: "
						+ filename);
			}
			io.printStackTrace();
		} finally {
			if (bReader != null) {
				try {
					bReader.close();
				} catch (IOException e) {
					LOG.error("#### Unable to close input file: " + filename
							+ " - " + e.getMessage());
				}
			}
		}

	}

	public String getTrigramByKey(final String key) {
		LOG.debug("##### Running : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName()
				+ " Key: " + key);
		String result = null;
		if (trigramMap.containsKey(key)) {
			Trigram trigram = ((Trigram) trigramMap.get(key));
			result = trigram.getValue();
			trigram.incrementOffset();
		} else {
			// end of the game
			LOG.warn("There are no values for given key:" + key);
		}
		return result;
	}

	public String getKeyRandomly() {
		LOG.debug("##### Running : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		Object[] values = trigramMap.values().toArray();
		Object randomValue = values[generator.nextInt(values.length)];
		String randomKey = ((Trigram) randomValue).getKey();

		LOG.debug("##### Random key : " + randomKey);
		return randomKey;

	}
	
	public boolean hasData() {
		LOG.debug("##### Running : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		boolean hasData = !this.trigramMap.isEmpty();
		LOG.debug("##### hasData : " + hasData);
		return hasData;
	}

}