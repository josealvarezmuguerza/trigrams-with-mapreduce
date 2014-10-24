package com.trigram.mapred;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import com.trigram.main.Kata14;

/**
 * 
 * @author jose
 *
 */
public class TrigramReducer extends Reducer<Text, Text, Text, Text> {

	private Text value =  new Text();
	private static final Logger LOG = Logger.getLogger(TrigramReducer.class);

	
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		LOG.debug("##### Running : " + Thread.currentThread().getStackTrace()[1].getMethodName());

		StringBuilder valueList = new StringBuilder();
		for (Text value : values) {
			valueList.append(value.toString()).append(Kata14.SPACE);
		}
		
		value.set(valueList.toString().trim());
		context.write(key, value);
	}

}
