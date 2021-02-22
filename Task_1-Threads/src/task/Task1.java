package task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task1 {
	
	public static void main(String args[])  {
		if(args.length != 2 && args.length != 3 && args.length != 5) {
			throw new IllegalArgumentException("Inapproppriate number of files");
		}
		System.out.println("Sequentially");
		long start = System.currentTimeMillis();
		for(String s : args) {
			FileThread thr = new FileThread(s);
			thr.readRow(s);
		}
		long end = System.currentTimeMillis();
		System.out.println("--------------------");
		System.out.println("Multithraded");
		long startTwo = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(args.length);
        for (int i = 0; i < args.length; i++) {
            Runnable worker = new FileThread(args[i]);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated());
		long endTwo = System.currentTimeMillis();
		System.out.println("Sequentially: " + (end - start));
		System.out.println("Multithraded: " + (endTwo - startTwo));
	}
	
}
	class FileThread extends Thread{
		private String fileName;
		
		public FileThread(String fileName){
			this.fileName = fileName;
		}
		
		public void readRow(String fileName) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(fileName));
				int lines = 0;
				while(reader.readLine() != null) lines++;
				System.out.println("File "+fileName + "has that number of lines:" + lines);
			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
				e.printStackTrace();
			}finally {
				try {
					if(reader != null) {
						reader.close();
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void run() {
			readRow(fileName);
		}
}