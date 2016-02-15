package com.gmail.mosoft521.jmtpdp.ch13pipeline.example;

public class Config {

    public static int MAX_RECORDS_PER_FILE = 5000;

    /**
     * RECORD_SAVE_CHUNK_SIZE should be less than RECORD_JOIN_SIZE
     */
    public static int RECORD_SAVE_CHUNK_SIZE = 350;

    public static int WRITER_BUFFER_SIZE = 8192 * 10;

    /**
     * RECORD_JOIN_SIZE should be about WRITER_BUFFER_SIZE/103
     */
    public static int RECORD_JOIN_SIZE = 700;// 此值不能过小，过小会导致内存消耗过大 =8196/101

	/*
     * CPU<40%
	 * 
	 * public static int RECORD_SAVE_CHUNK_SIZE=100;
	 * 
	 * public static int RECORD_JOIN_SIZE=300;
	 */
}