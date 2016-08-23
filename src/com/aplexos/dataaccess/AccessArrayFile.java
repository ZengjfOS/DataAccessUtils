package com.aplexos.dataaccess;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import android.app.Activity;
import android.util.Log;


/**
 * ��һЩ������ʵʱ��Ҫ��Ƚϸߵĳ��ϣ�����ʱ���ܶϵ�ĳ����£�ͬʱ��Ҫ������д���ļ��У�
 * ���ʱ�����ǲ�ϣ���������ڴ��д�̫�ã�����ܹ�����ͬ�����������ǵ�����<br>
 * ��һ�ַ�����<br>
 * 	1. RandomAccessFile<br>
 * 	2. public RandomAccessFile(File file, String mode) throws FileNotFoundException<br>
 *      Constructs a new RandomAccessFile based on file and opens it according to the access string in mode. <br>
 * 	3. mode may have one of following values: <br>
 *      1. "r" The file is opened in read-only mode. An IOException is thrown if any of the write methods is called. <br>
 * 		2. "rw" The file is opened for reading and writing. If the file does not exist, it will be created. <br>
 * 		3. "rws" The file is opened for reading and writing. Every change of the file's content or metadata must be written synchronously to the target device. <br>
 * 		4. "rwd" The file is opened for reading and writing. Every change of the file's content must be written synchronously to the target device. <br>
 * 	4. ����������Ҫ���е�����ͬ�����ܣ���������ѡ��ʹ�ð�װRandomAccessFile�࣬ʵ��Ҫ��<br>
 * �ڶ��ַ���:<br>
 *  1. FileDescriptor����sync()����<br>
        Ensures that data which is buffered within the underlying implementation is written out to the appropriate device before returning.<br>
 *  2. FileOutputStream�е� getFD()����<br>
        Returns a FileDescriptor which represents the lowest level representation of an operating system stream resource. <br>
 *	3. ʹ�������о�û��RandomAccessFile���㣬����ʱʹ��<br>
 */

public class AccessArrayFile {
	/**
	 * ����������д���ļ�
	 * 
	 * @param filePath		 �ļ�·��
	 * @param data			 ��������
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
	 * ����������д���ļ����ļ�Ŀ¼��ָ������Ϊʹ���߿��Բ��ù���
	 * 
	 * @param activity		 �������������Activity
	 * @param data			 Ҫ����ĵ���������
	 * @throws IOException
	 */
	static public void writeIntArray(Activity activity, int[] data) throws IOException {
		if (null == activity || null == data) 
			return ;

		String filePath = activity.getApplicationContext().getFilesDir().getAbsoluteFile() + "/aplexosData.txt";
		writeIntArray(filePath, data);
	}
	
	/**
	 * ���ļ��ж�������Ϊlength����������
	 * 
	 * @param filePath		�ļ�·��
	 * @param length		���鳤��
	 * @return				�������飬�����������null
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
	 * ���ļ��ж�ȡ�������飬�ļ�λ�á����Ѿ���ָ������Ϊʹ���߿��Բ�����
	 * 
	 * @param activity		�������������Activity
	 * @param length		����ĳ���
	 * @return				�������飬�����������null
	 * @throws IOException
	 */
	static public int[] readIntArray(Activity activity, int length) throws IOException {
		if (null == activity || 0 == length) 
			return null;

		String filePath = activity.getApplicationContext().getFilesDir().getAbsoluteFile() + "/aplexosData.txt";
		return readIntArray(filePath, length);
	}
	
	/**
	 * ���ļ���д��ԭʼ�������飬��ʵ�����������0
	 * 
	 * @param filePath		�ļ�·��
	 * @param length		�����С
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
	 * ���ļ���д��ֵΪ0���������飬�ļ�λ�á����Ѿ���ָ������Ϊʹ���߿��Բ�����
	 * 
	 * @param activity		�������������Activity
	 * @param length		д������ĳ���
	 * @throws IOException
	 */
	static public void writeRawIntArray(Activity activity, int length) throws IOException{
		if (null == activity || 0 == length) 
			return ;

		String filePath = activity.getApplicationContext().getFilesDir().getAbsoluteFile() + "/aplexosData.txt";
		writeRawIntArray(filePath, length);
	}
	
	/**
	 * �����õĵ�Demo
	 * @param activity 	�������������Activity
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
