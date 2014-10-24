package com.trigram.mapred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import com.trigram.main.Kata14;

/**
 * 
 * @author jose
 *
 */
public class TrigramMapper extends Mapper<LongWritable, Text, Text, Text> {

	private Text key = new Text();
	private Text value = new Text();

	private static final Logger LOG = Logger.getLogger(TrigramMapper.class);

	public void map(LongWritable offset, Text line, Context context)
			throws IOException, InterruptedException {

		LOG.debug("##### Running : "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());
		ArrayList<String> wordsList = new ArrayList<String>();
		StringBuilder stringBuilder = new StringBuilder();

		System.out.println(line);

		// remove all non word or number
		String lineString = line.toString().replaceAll(
				Kata14.properties.getProperty("trigram.mr.cleansing.pattern"), "");
		StringTokenizer lineTokenized = new StringTokenizer(lineString);

		while (lineTokenized.hasMoreElements()) {
			wordsList.add(lineTokenized.nextToken());
		}

		for (int i = 0; i < wordsList.size() - 2; i++) {
			// clear stringBuilder content if any
			stringBuilder.setLength(0);
			// creates the key
			stringBuilder.append(wordsList.get(i)).append(Kata14.SPACE)
					.append(wordsList.get(i + 1));
			key.set(stringBuilder.toString());
			value.set(wordsList.get(i + 2));
			context.write(key, value);

		}

	}

}
