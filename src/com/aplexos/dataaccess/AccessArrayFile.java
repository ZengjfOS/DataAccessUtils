package com.aplexos.dataaccess;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import android.app.Activity;
import android.util.Log;


/**
 * 在一些对数据实时性要求比较高的场合，如随时可能断电的场合下，同时需要将数据写入文件中，
 * 这个时候，我们不希望数据在内存中呆太久，最好能够做到同步，这是我们的需求。<br>
 * 第一种方案：<br>
 * 	1. RandomAccessFile<br>
 * 	2. public RandomAccessFile(File file, String mode) throws FileNotFoundException<br>
 *      Constructs a new RandomAccessFile based on file and opens it according to the access string in mode. <br>
 * 	3. mode may have one of following values: <br>
 *      1. "r" The file is opened in read-only mode. An IOException is thrown if any of the write methods is called. <br>
 * 		2. "rw" The file is opened for reading and writing. If the file does not exist, it will be created. <br>
 * 		3. "rws" The file is opened for reading and writing. Every change of the file's content or metadata must be written synchronously to the target device. <br>
 * 		4. "rwd" The file is opened for reading and writing. Every change of the file's content must be written synchronously to the target device. <br>
 * 	4. 由于我们需要其中的数据同步功能，所以我们选择使用包装RandomAccessFile类，实现要求。<br>
 * 第二种方案:<br>
 *  1. FileDescriptor中有sync()方法<br>
        Ensures that data which is buffered within the underlying implementation is written out to the appropriate device before returning.<br>
 *  2. FileOutputStream中的 getFD()方法<br>
        Returns a FileDescriptor which represents the lowest level representation of an operating system stream resource. <br>
 *	3. 使用起来感觉没有RandomAccessFile方便，放弃时使用<br>
 */

public class AccessArrayFile {
	/**
	 * 将整形数组写入文件
	 * 
	 * @param filePath		 文件路径
	 * @param data			 整形数组
	 * @throws IOException
	 */
	static public void writeIntArray(String filePath, int[] data) throws IOException {
		if (null == filePath || null == data) 
			return ;
		
		if (filePath.trim().equals("")) 
			return ;
		
		File file = new File(filePath);
		if (!file.exists()) 
			file.createNewFile();
			
		// write data
		RandomAccessFile raf = new RandomAccessFile(file, "rws");
		for (int i = 0; i < data.length; i++) 
			raf.writeInt(data[i]);
		
		raf.close();
	}

	/**
	 * 将整形数组写入文件，文件目录被指定，作为使用者可以不用关心
	 * 
	 * @param activity		 调用这个函数的Activity
	 * @param data			 要保存的的整形数组
	 * @throws IOException
	 */
	static public void writeIntArray(Activity activity, int[] data) throws IOException {
		if (null == activity || null == data) 
			return ;

		String filePath = activity.getApplicationContext().getFilesDir().getAbsoluteFile() + "/aplexosData.txt";
		writeIntArray(filePath, data);
	}
	
	/**
	 * 从文件中读出长度为length的整形数组
	 * 
	 * @param filePath		文件路径
	 * @param length		数组长度
	 * @return				返回数组，如果出错，返回null
	 * @throws IOException
	 */
	static public int[] readIntArray(String filePath, int length) throws IOException {
		
		if (null == filePath || length <= 0) 
			return null;
		
		if (filePath.trim().equals("")) 
			return null;
		
		File file = new File(filePath);	

		int[] data = new int[length]; 	// for return data

		// if file not exist in first time and file length less than data size, 
		// just create file and make data for it
		if (!file.exists() || (file.length() < (4 * length))) {
			for (int i = 0; i < data.length; i++) 
				data[i] = 0;

			writeIntArray(filePath, data);
			return data;
		}
		
		//get data
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		for (int i = 0; i < length; i++) 
			data[i] = raf.readInt();
		
		raf.close();
		
		return data;
	}

	/**
	 * 从文件中读取整形数组，文件位置、名已经被指定，作为使用者可以不关心
	 * 
	 * @param activity		调用这个函数的Activity
	 * @param length		数组的长度
	 * @return				返回数组，如果出错，返回null
	 * @throws IOException
	 */
	static public int[] readIntArray(Activity activity, int length) throws IOException {
		if (null == activity || 0 == length) 
			return null;

		String filePath = activity.getApplicationContext().getFilesDir().getAbsoluteFile() + "/aplexosData.txt";
		return readIntArray(filePath, length);
	}
	
	/**
	 * 往文件中写入原始整形数组，其实就是填充整形0
	 * 
	 * @param filePath		文件路径
	 * @param length		数组大小
	 * @throws IOException
	 */
	static public void writeRawIntArray(String filePath, int length) throws IOException {

		if (null == filePath || length <= 0) 
			return ;
		
		if (filePath.trim().equals("")) 
			return ;

		File file = new File(filePath);	
		int[] data = new int[length]; 	// for return data

		// if file not exist in first time, just create file and make data for it
		if (file.exists()) {
			for (int i = 0; i < data.length; i++) 
				data[i] = 0;

			writeIntArray(filePath, data);
		}
	}
	
	/**
	 * 
	 * 往文件中写入值为0的整形数组，文件位置、名已经被指定，作为使用者可以不关心
	 * 
	 * @param activity		调用这个函数的Activity
	 * @param length		写入数组的长度
	 * @throws IOException
	 */
	static public void writeRawIntArray(Activity activity, int length) throws IOException{
		if (null == activity || 0 == length) 
			return ;

		String filePath = activity.getApplicationContext().getFilesDir().getAbsoluteFile() + "/aplexosData.txt";
		writeRawIntArray(filePath, length);
	}
	
	/**
	 * 测试用的的Demo
	 * @param activity 	调用这个函数的Activity
	 */
	static public void testDemo(Activity activity) {
		int[] data = {1, 2, 3, 4, 5, 6};
		try {
			writeIntArray(activity, data);
			int[] redata = readIntArray(activity, 6);
			Log.e("aplexos utils", Arrays.toString(redata));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
