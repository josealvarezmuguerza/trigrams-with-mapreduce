package com.trigram.mapred;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

public class TrigramDriver {

	private static final Logger LOG = Logger.getLogger(TrigramDriver.class);

	public void runMapReduce(final String inputPath, final String outPath) throws Exception {
		LOG.debug("##### Running : " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Configuration conf = new Configuration(true);
		conf.setInt("io.file.buffer.size",  64 * 1024 * 1024);
	
		Job job = Job.getInstance(conf);
		job.setInputFormatClass(BookInputFormat.class);
		job.setJarByClass(com.trigram.mapred.TrigramDriver.class);

		// specify a mapper
		job.setMapperClass(TrigramMapper.class);

		// specify a reducer
		job.setReducerClass(TrigramReducer.class);

		// specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		// specify input and output DIRECTORIES (not files)
		FileInputFormat.setInputPaths(job, new Path(inputPath));
		Path outputDir = new Path( outPath + "MR" );
		outputDir.getFileSystem( conf ).delete(outputDir, true );
	    FileOutputFormat.setOutputPath(job, outputDir);

		if (!job.waitForCompletion(true))
			return;
	}
}
