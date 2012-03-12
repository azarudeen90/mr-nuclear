package com.imaginea.rm.mapreduce.dn;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import com.imaginea.rm.mapreduce.dn.persist.Frequency;

public class DNLoader {
  
  public static class FrequencyLoader extends
      Mapper<LongWritable, Text, Frequency, NullWritable> {
    
    private NullWritable dummy = NullWritable.get();
    private Frequency freq;

    @Override
    protected void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
      String[] split = value.toString().split("\t");
      freq = new Frequency();
      freq.setWord(split[0]);
      int frequency = Integer.parseInt(split[1]);
      freq.setFrequency(frequency);
      context.write(freq, dummy);
    };
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args)
        .getRemainingArgs();
    if (otherArgs.length != 1) {
      System.err.println("Usage: DNLoader <input>");
      System.exit(2);
    }
    Job job = new Job(conf, "DNLOADER");
    job.setJarByClass(DNLoader.class);
    job.setMapperClass(FrequencyLoader.class);
    job.setNumReduceTasks(0);
    job.setOutputKeyClass(Frequency.class);
    job.setOutputValueClass(NullWritable.class);
    job.setOutputFormatClass(DNOutputFormat.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
