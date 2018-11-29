package com.daphnis.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class Logdemo {
	public static Logger logger = LoggerFactory.getLogger(Logdemo.class);
	
	public static void printMsg() {
        logger.debug("this is debug..");
        logger.info("this is info..");
        logger.warn("this is warn..");
        logger.error("this is error..");
	}

	public static void main(String[] args) {
		printMsg();
	}
}
