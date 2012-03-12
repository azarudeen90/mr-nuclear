package com.imaginea.rm.mapreduce.example.dn.persist;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

@PersistenceCapable(table = "WORDCOUNT", identityType = IdentityType.DATASTORE)
public class Frequency implements WritableComparable<Frequency> {

  @PrimaryKey
  @Persistent
  private String word;

  @Persistent
  private int frequency;

  public Frequency() {
  }

  public void setWord(String word) {
    this.word = word;
  }

  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeUTF(word);
    out.writeInt(frequency);
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    word = in.readUTF();
    frequency = in.readInt();
  }

  @Override
  public int compareTo(Frequency that) {
    return this.word.compareTo(that.word);
  }

  @Override
  public int hashCode() {
    return this.word.hashCode();
  }

  private static class FrequncyComparator extends WritableComparator {

    protected FrequncyComparator() {
      super(Frequency.class);
    }

    @Override
    public int compare(byte[] f1, int s1, int l1, byte[] f2, int s2, int l2) {
      return super.compare(f1, s1, l1 - 4, f2, s2, l2 - 4);
    }
  }

  static { // register this comparator
    WritableComparator.define(FrequncyComparator.class,
        new FrequncyComparator());
  }

}
