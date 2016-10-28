package com.openthinks.webscheduler.task;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.quartz.JobExecutionException;

import com.openthinks.webscheduler.help.StaticUtils;

public class DownloadFileTaskTest {

	@Test
	public void test() throws JobExecutionException, UnsupportedEncodingException {
		//SimpleDownloadFileTask task = new SimpleDownloadFileTask();
		System.out.println("Start...");
		System.out.println(UUID.randomUUID().toString());
		System.out.println(new String(DigestUtils.md5("123456")).equals(new String(DigestUtils.md5("123456"))));
		System.out.println(DigestUtils.md5Hex("123456"));
		System.out.println(DigestUtils.md5Hex("1234567"));
		String now = StaticUtils.formatNow();
		String sha1 = DigestUtils.sha1Hex(now);
		System.out.println(sha1);
	}

}
