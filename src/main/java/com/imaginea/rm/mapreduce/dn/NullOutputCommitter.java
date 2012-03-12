package com.imaginea.rm.mapreduce.dn;

import java.io.IOException;

import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class NullOutputCommitter extends OutputCommitter {

  @Override
  public void abortTask(TaskAttemptContext tac) throws IOException {
  }

  @Override
  public void commitTask(TaskAttemptContext tac) throws IOException {
  }

  @Override
  public boolean needsTaskCommit(TaskAttemptContext tac) throws IOException {
    return false;
  }

  @Override
  public void setupJob(JobContext jc) throws IOException {
  }

  @Override
  public void setupTask(TaskAttemptContext tac) throws IOException {
  }

}
