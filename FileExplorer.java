//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: FileExplorer.java
// Course: CS 300 Fall 2020
//
// Author: Huong Nguyen
// Email: htnguyen23@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: none
// Online Sources: none
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FileExplorer {

  /**
   * Returns a list of the names of all files and directories in the the given folder. 
   * 
   * @param currentFolder the File of the directory being looked through
   * @return an array list that contains the names of all files and directories 
   * @throws NotDirectoryException with a description error message if the provided currentFolder
   *                               does not exist or if it is not a directory
   */
  public static ArrayList<String> listContents(File currentFolder) throws NotDirectoryException {
    if (!currentFolder.isDirectory()) {
      throw new NotDirectoryException(
        "WARNING! " + currentFolder.getName() + " is not a directory.");
    }
    ArrayList<String> contentArray = new ArrayList<String>();
    try {
      if (currentFolder.exists()) {
        String[] tempArray = currentFolder.list();
        for (int i = 0; i < tempArray.length; i++) {
          contentArray.add(tempArray[i]);
        }
      }
    } finally {
      return contentArray;
    }
  }

  /**
   * Recursive method that lists the names of all the files (not directories) in the given folder
   * and its sub-folders
   * 
   * @param currentFolder the File of the directory being looked through
   * @return an array list that contains the names of all files in the given folder
   * @throws NotDirectoryException with a description error message if the provided currentFolder
   *                               does not exist or if it is not a directory
   */
  public static ArrayList<String> deepListContents(File currentFolder)
    throws NotDirectoryException {
    if (!currentFolder.isDirectory()) {
      throw new NotDirectoryException(
        "WARNING! " + currentFolder.getName() + " is not a directory.");
    }
    ArrayList<String> contentArray = new ArrayList<String>();
    if (currentFolder.exists()) {
      File[] tempArray = currentFolder.listFiles();
      for (int i = 0; i < tempArray.length; i++) {
        // base case
        if (tempArray[i].isFile()) {
          contentArray.add(tempArray[i].getName());
        }
        // recursive case
        if (tempArray[i].isDirectory()) {
          contentArray.addAll(deepListContents(tempArray[i]));
        }
      }
    }
    return contentArray;
  }

  /**
   * Searches the given folder and all of its subfolders for an exact match to the provided
   * fileName.
   * 
   * @param currentFolder the File of the directory being looked through
   * @param fileName the name of the file being looked for
   * @return a path to the file it it exists
   * @throws NoSuchElementException with a descriptive error message if the search operation returns
   *                                with no results found (including the case if fileName is null or
   *                                currentFolder does not exist, or was not a directory)
   */
  public static String searchByName(File currentFolder, String fileName) {
    if (fileName == null || !currentFolder.exists() || !currentFolder.isDirectory()) {
      throw new NoSuchElementException("Error with fileName or directory given.");
    }
    File result = searchByNameHelper(currentFolder, fileName);
    if (result == null)
      throw new NoSuchElementException(
        "No results found for file: " + fileName + " in directory: " + currentFolder);
    String path = result.getPath();
    return path;
  }

  private static File searchByNameHelper(File currentFolder, String fileName) {
    File[] tempArray = currentFolder.listFiles();
    File f = null;
    for (int i = 0; i < tempArray.length; i++) {
      // base case
      if (tempArray[i].isFile()) {
        if (tempArray[i].getName().equals(fileName))
          return tempArray[i];
      }
      // recursive case
      if (tempArray[i].isDirectory()) {
        f = searchByNameHelper(tempArray[i], fileName);
        if (f != null) {
          return f;
        }
      }
    }
    return f;
  }

  /**
   * Recursive method that searches the given folder and its subfolders for all files that contain
   * the given key in part of their name.
   * 
   * @param currentFolder the File of the directory being looked through
   * @param key the string that parts of names are being looked for
   * @return an array list of all the names of files that match, and an empty arraylist when the
   *         operation returns with no results found (including the case where currentFolder is not
   *         a directory)
   */
  public static ArrayList<String> searchByKey(File currentFolder, String key) {
    ArrayList<String> matchArray = new ArrayList<String>();
    File[] tempArray = currentFolder.listFiles();
    for (int i = 0; i < tempArray.length; i++) {
      // base case
      if (tempArray[i].isFile()) {
        if (tempArray[i].getName().contains(key)) 
          matchArray.add(tempArray[i].getName());
      }
      // recursive case
      if (tempArray[i].isDirectory()) {
        matchArray.addAll(searchByKey(tempArray[i], key));
      }
    }
    return matchArray;
  }

  /**
   * Recursive method that searches the given folder and its subfolders for all files whose size is
   * within the given max and min values, inclusive.
   * 
   * @param currentFolder
   * @param sizeMin
   * @param sizeMax
   * @return an array list of the names of all files whose size are within the boundaries, and an
   *         empty array list if the search operation returns with no results found (including the case where currentFolder is not a directory)
   */
  public static ArrayList<String> searchBySize(File currentFolder, long sizeMin, long sizeMax) {
    ArrayList<String> matchArray = new ArrayList<String>();
    File[] tempArray = currentFolder.listFiles();
    for (int i = 0; i < tempArray.length; i++) {
      // base case
      if (tempArray[i].isFile()) {
        if (tempArray[i].getTotalSpace() >= sizeMin && tempArray[i].getTotalSpace() <= sizeMax) 
          matchArray.add(tempArray[i].getName());
      }
      // recursive case
      if (tempArray[i].isDirectory()) {
        matchArray.addAll(searchBySize(tempArray[i], sizeMin, sizeMax));
      }
    }
    return matchArray;
  }

}
