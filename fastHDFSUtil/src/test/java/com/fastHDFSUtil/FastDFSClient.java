package com.fastHDFSUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.connection.AppException;
import com.connection.ConnectionPool;

public class FastDFSClient {
	@Test
	public void upload() {
		try {// 初始化全局配置。加载一个配置文件。
			ClientGlobal.init("F:\\eclipseworkpace\\fastHDFSUtil\\src\\main\\resources\\fdfs_client.conf");
			// 创建一个TrackerClient对象。
			TrackerClient trackerClient = new TrackerClient();
			// 创建一个TrackerServer对象。
			TrackerServer trackerServer = trackerClient.getConnection();
			// 声明一个StorageServer对象，null。
			StorageServer storageServer = null;
			// 获得StorageClient对象。
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			// 直接调用StorageClient对象方法上传文件即可。
			String[] strings;
			strings = storageClient.upload_file("C:\\Users\\DELL\\Desktop\\images\\2.png", "png", null);
			for (String string : strings) {
				System.out.println(string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void download() {
		try {
			// 初始化全局配置。加载一个配置文件
			ClientGlobal.init("F:\\eclipseworkpace\\fastHDFSUtil\\src\\main\\resources\\fdfs_client.conf");
			// 创建一个TrackerClient对象
			TrackerClient tracker = new TrackerClient();
			// 创建一个TrackerServer对象。
			TrackerServer trackerServer = tracker.getConnection();
			// 声明一个StorageServer对象，
			StorageServer storageServer = null;
			// 获得StorageClient对象
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			byte[] b = storageClient.download_file("group1", "M00/00/00/CpqoRFlZ922AYi7NAAQP4yy1msE750.png");
			System.out.println(b);
			// 将下载的文件流保存
			IOUtils.write(b, new FileOutputStream("D:/" + UUID.randomUUID().toString() + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConnectionPool() throws AppException, IOException, MyException {
		/** 连接池默认最小连接数 */
		long minPoolSize = 10;
		/** 连接池默认最大连接数 */
		long maxPoolSize = 30;
		ConnectionPool connection = new ConnectionPool(minPoolSize, maxPoolSize, 200);
		TrackerServer trackerServer = connection.checkout(UUID.randomUUID().toString());
		StorageServer storageServer = null;
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 直接调用StorageClient对象方法上传文件即可。
		String[] strings;
		strings = storageClient.upload_file("C:\\Users\\DELL\\Desktop\\images\\3.png", "png", null);
		for (String string : strings) {
			System.out.println(string);
		}
		//释放连接
		connection.checkin(trackerServer, UUID.randomUUID().toString());
	}
}