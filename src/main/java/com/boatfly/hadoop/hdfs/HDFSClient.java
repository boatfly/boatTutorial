package com.boatfly.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

/**
 * 操作HDFS
 */
public class HDFSClient {
    FileSystem fs;

    @Before
    public void before() throws IOException {
        fs = FileSystem.get(URI.create("hdfs://localhost:9000"), new Configuration());
    }

    @Test
    public void put() throws IOException {
        fs.copyFromLocalFile(new Path("in/word.txt"), new Path("/"));
    }

    @After
    public void after() throws IOException {
        fs.close();
    }
}
