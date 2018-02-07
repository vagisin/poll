package com.polling.file.poll;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
/**
 * Hello world!
 *
 */
public class App 
{
   
	 public static void listFilesForFolder(final File folder) {
		    for (final File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		            listFilesForFolder(fileEntry);
		        } else {
		            System.out.println(fileEntry.getName());
		        }
		    }
		}
	public static void main( String[] args )
    {
    	 Path myDir = Paths.get("F:/data"); 
    	

    		final File folder = new File("F:/data");
    		listFilesForFolder(folder);
    		
    	/*	This try statement make sure that all the Stream closes no matter what. Yet to resolve this.
    	 * 
    	 * try (Stream<Path> paths = Files.walk(Paths.get("F:/data"))) {
    		    paths
    		        .filter(Files::isRegularFile)
    		        .forEach(System.out::println);
    		}*/
         try {
        	 
        	while(true) {
        		 WatchService watcher = myDir.getFileSystem().newWatchService();
                 myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, 
                 StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

                 WatchKey watckKey = watcher.take();

                 List<WatchEvent<?>> events = watckKey.pollEvents();
                 for (WatchEvent event : events) {
                      if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                          System.out.println("Created: " + event.context().toString());
                      }
                      if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                          System.out.println("Delete: " + event.context().toString());
                      }
                      if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                          System.out.println("Modify: " + event.context().toString());
                      }
                  }
                 
                 Thread.sleep(1000);        
                 /*would wait for 10 seconds
                  * It can record utmost 1  action at a time.
                  */
        	}
           
        	
            
         } catch (Exception e) {
             System.out.println("Error: " + e.toString());
         }

 	}
    }

