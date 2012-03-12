package com.imaginea.rm.mapreduce.dn;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * A DNOutputFormat persists the java domain objects into any kind of persistent
 * store. Application using this output format should write the object to be
 * persisted from their map/reduce implementation.
 * 
 * Write expects the key to be the object to be persisted. Value type should
 * be NullWritable.
 * 
 */
public class DNOutputFormat<K, V extends NullWritable> extends
    OutputFormat<K, V> {

  @Override
  public void checkOutputSpecs(JobContext context) throws IOException,
      InterruptedException {
  }

  @Override
  public OutputCommitter getOutputCommitter(TaskAttemptContext context)
      throws IOException, InterruptedException {
    return new NullOutputCommitter();
  }

  private class DBRecordWriter extends RecordWriter<K, V> {

    private PersistenceManager pm;
    private Transaction tx;

    public DBRecordWriter(PersistenceManagerFactory pmf) {
      pm = pmf.getPersistenceManager();
      tx = pm.currentTransaction();
      tx.begin();
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException,
        InterruptedException {
      commitTransaction();
      pm.close();
    }

    @Override
    public void write(K obj, V nullObj) throws IOException,
        InterruptedException {
      pm.makePersistent(obj);
    }

    private void commitTransaction() {
      try {
        tx.commit();
      } finally {
        if (tx != null && tx.isActive()) {
          tx.rollback();
        }
      }
    }
  }

  @Override
  public RecordWriter<K, V> getRecordWriter(TaskAttemptContext context)
      throws IOException, InterruptedException {
    DNConfiguration dnConf = new DNConfiguration();
    PersistenceManagerFactory pmf = dnConf.getPersistentManagerFactory();
    return new DBRecordWriter(pmf);
  }
}