package com.trigram.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.trigram.dao.TrigramDao;
import com.trigram.mapred.TrigramDriver;

public class Kata14 {

	private static final String OUTPUT_FILE_NAME = "new_book.txt";
	private static final Logger LOG = Logger.getLogger(Kata14.class);
	private TrigramDao dao = null;

	public final static String SPACE = " ";

	public static void main(String[] args) throws Exception {
		LOG.debug("##### Running : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		if (0 == args.length) {
			System.out.println("Arguments:");
			System.out.println();
			System.out.println("  -i: input_folder -o output_folder");
			System.out.println();
			System.out
					.println("         Note: Input folder must contain 1 or more sample books. output folder could be removed. Please DO NOT SPECIFY CRITICAL FOLDERS AS OUTPUT!! '");
			System.out.println();

		} else {
			long startTime;
			long endTime;
			startTime = System.currentTimeMillis();

			String inputPath = "";
			String outputPath = "";

			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-o")) {
					outputPath = args[i + 1];
				} else if (args[i].equals("-i")) {
					inputPath = args[i + 1];
				}
			}

			final Kata14 kata14 = new Kata14();

			kata14.generateTrigrams(inputPath, outputPath);
			kata14.loadTrigrams(outputPath);
			final StringBuilder generatedPhrase = kata14.generateNewBook();
			kata14.writeFile(generatedPhrase, outputPath);

			endTime = System.currentTimeMillis();
			System.out.println("******* END!!! ********");
			System.out.println(endTime - startTime + " milisecs");
		}
	}

	private void loadTrigrams(final String outputPath) {
		LOG.debug("##### Running : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		dao = new TrigramDao(outputPath);

	}

	private void generateTrigrams(final String inputPath, final String outPath)
			throws Exception {
		LOG.debug("##### Running : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		final TrigramDriver driver = new TrigramDriver();
		driver.runMapReduce(inputPath, outPath);
	}

	private StringBuilder generateNewBook() {
		LOG.debug("##### Running : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		StringBuilder builderPhrase = new StringBuilder();

		if (dao.hasData()) {
			StringBuilder builderNewKey = new StringBuilder();

			String seed = dao.getKeyRandomly();
			String value = null;
			builderPhrase.append(seed).append(SPACE);

			while ((value = dao.getTrigramByKey(seed)) != null) {
				builderPhrase.append(value).append(SPACE);
				builderNewKey.setLength(0);
				seed = builderNewKey.append(seed.split("\\s")[1]).append(SPACE)
						.append(value).toString();
			}

		} else {
			System.out.println("There are not enough trigrams to play");
		}
		return builderPhrase;
	}

	private void writeFile(final StringBuilder fileSB, final String outputPath) {

		File file = new File(outputPath + "/" + OUTPUT_FILE_NAME);
		BufferedWriter writer = null;
		try {
			try {
				writer = new BufferedWriter(new FileWriter(file));
				writer.write(fileSB.toString());
			} catch (IOException e) {
				LOG.error("#### File cannot be written - " + e.getMessage());
			}
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					LOG.error("#### File cannot be written - " + e.getMessage());
				}
			}
		}
	}

}
