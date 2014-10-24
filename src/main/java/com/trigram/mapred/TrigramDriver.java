package com.trigram.mapred;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;
/**
 * 
 * @author jose
 *
 */
public class TrigramDriver {

	private static final Logger LOG = Logger.getLogger(TrigramDriver.class);

	public void runMapReduce(final String inputPath, final String outPath) throws Exception {
		LOG.debug("##### Running : " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Configuration conf = new Configuration(true);
		conf.setInt("io.file.buffer.size", 64 * 1024 * 16);
	
		Job job = Job.getInstance(conf);
		job.setInputFormatClass(BookInputFormat.class);
		job.setJarByClass(com.trigram.mapred.TrigramDriver.class);

		job.setMapperClass(TrigramMapper.class);
		job.setReducerClass(TrigramReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path(inputPath));
		Path outputDir = new Path( outPath + "/MR" );
		outputDir.getFileSystem( conf ).delete(outputDir, true );
	    FileOutputFormat.setOutputPath(job, outputDir);

		if (!job.waitForCompletion(true))
			return;
	}
}
