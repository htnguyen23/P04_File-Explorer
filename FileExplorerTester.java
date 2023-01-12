//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: FileExplorerTester.java
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

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FileExplorerTester {
  public static boolean testListContents(File folder) {
    try {
      // Scenario 1
      // list the basic contents of the cs300 folder
      ArrayList<String> listContent = FileExplorer.listContents(folder);
      // expected output content
      String[] contents = new String[] {"grades", "lecture notes", "programs",
        "quizzes preparation", "reading notes", "syllabus.txt", "todo.txt"};
      List<String> expectedList = Arrays.asList(contents);
      // check the size and the contents of the output
      if (listContent.size() != 7) {
        System.out.println("Problem detected: cs300 folder must contain 7 elements.");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!listContent.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
            + " is missing from the output of the list contents of cs300 folder.");
          return false;
        }
      }
      // Scenario 2 - list the contents of the grades folder
      File f = new File(folder.getPath() + File.separator + "grades");
      listContent = FileExplorer.listContents(f);
      if (listContent.size() != 0) {
        System.out.println("Problem detected: grades folder must be empty.");
        return false;
      }
      // Scenario 3 - list the contents of the p02 folder
      f = new File(folder.getPath() + File.separator + "programs" + File.separator + "p02");
      listContent = FileExplorer.listContents(f);
      if (listContent.size() != 1 || !listContent.contains("WisconsinPrairie.java")) {
        System.out.println("Problem detected: p02 folder must contain only one file named "
          + "WisconsinPrairie.java.");
        return false;
      }
      // Scenario 4 - Try to list the contents of a file
      f = new File(folder.getPath() + File.separator + "todo.txt");
      try {
        listContent = FileExplorer.listContents(f);
        System.out.println("Problem detected: Your FileExplorer.listContents() must "
          + "throw a NotDirectoryException if it is provided an input which is not"
          + "a directory.");
        return false;
      } catch (NotDirectoryException e) { // catch only the expected exception
        // Expected behavior -- no problem detected
      }
      // Scenario 5 - Try to list the contents of not found directory/file
      f = new File(folder.getPath() + File.separator + "music.txt");
      try {
        listContent = FileExplorer.listContents(f);
        System.out.println("Problem detected: Your FileExplorer.listContents() must "
          + "throw a NotDirectoryException if the provided File does not exist.");
        return false;
      } catch (NotDirectoryException e) {
        // catch only the expected exception to be thrown -- no problem detected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.listContents() has thrown"
        + " a non expected exception.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean testDeepListBaseCase(File folder) {
    // Scenario 1 - list the basic contents of the cs300 folder
    try {
      ArrayList<String> deepListContents = FileExplorer.deepListContents(folder);
      // expected output content
      String[] contents = new String[] {"Review", "Notes", "Exam"};
      List<String> expectedList = Arrays.asList(contents);
      // check the size and the contents of the output
      if (deepListContents.size() != 3) {
        System.out.println("Problem detected: cs300 folder must contain 3 elements.");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!deepListContents.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
            + " is missing from the output of the list contents of cs300 folder.");
          return false;
        }
      }
      // Scenario 2 - Try to list the contents of not found directory/file
      File f = new File(folder.getPath() + File.separator + "music.txt");
      try {
        deepListContents = FileExplorer.deepListContents(f);
        System.out.println("Problem detected: Your FileExplorer.listContents() must "
          + "throw a NotDirectoryException if the provided File does not exist.");
        return false;
      } catch (NotDirectoryException e) {
        // catch only the expected exception to be thrown -- no problem detected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.listContents() has thrown"
        + " a non expected exception.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean testDeepListRecursiveCase(File folder) {
    // Scenario 1 - list the contents of the lecture notes folder (in cs300)
    try {
      ArrayList<String> deepListContents = FileExplorer.deepListContents(folder);
      // expected output content
      String[] contents = new String[] {"ExceptionHandling.txt", "proceduralProgramming.txt",
        "UsingObjectsAndArrayLists.txt", "AlgorithmAnalysis.txt", "Recursion.txt"};
      List<String> expectedList = Arrays.asList(contents);
      // check the size and the contents of the output
      if (deepListContents.size() != 5) {
        System.out.println("Problem detected: cs300 folder must contain 5 elements.");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!deepListContents.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
            + " is missing from the output of the list contents of lecture notes folder.");
          return false;
        }
      }
      // Scenario 2 - Try to list the contents of not found directory/file
      File f = new File(folder.getPath() + File.separator + "music.txt");
      try {
        deepListContents = FileExplorer.deepListContents(f);
        System.out.println("Problem detected: Your FileExplorer.deepListContents() must "
          + "throw a NotDirectoryException if the provided File does not exist.");
        return false;
      } catch (NotDirectoryException e) {
        // catch only the expected exception to be thrown -- no problem detected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.deepListContents() has thrown"
        + " a non expected exception.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static boolean testSearchByFileName(File folder) {
    // case 1 - check for correct path returned from existing file in CS300
    File f = new File(folder.getPath() + File.separator + "COVIDTrackerTester.java");
    String expected = f.getPath();
    String result = "";
    try {
      result = FileExplorer.searchByName(folder, "COVIDTrackerTester.java");
      if (!expected.equals(result))
        return false;
    } catch (NoSuchElementException nse) {
      System.out.println(nse.getMessage());
    }
    // case 2 - try to use null fileName, or non-existent directory
    try {
      FileExplorer.searchByName(folder, null);
    } catch (NoSuchElementException nse) {
      System.out.println(nse.getMessage());
    }
    return true;
  }

  public static boolean testSearchByKey(File folder) {
    // case 1 - check for correct array list with right amount and name of matches
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("COVIDTracker.java");
    expected.add("COVIDTrackerTester.java");
    ArrayList<String> result = new ArrayList<String>();
    try {
      result = FileExplorer.searchByKey(folder, "COVID");
      if (result.size() != 2 || !result.get(0).equals(expected.get(0))) {
        for (int i = 0; i < result.size(); i++) {
          System.out.println(result.get(i));
        }
        System.out.println("CASE 1");
        return false;
      }
    } catch (NoSuchElementException nse) {
      System.out.println(nse.getMessage());
    }
    // case 2 - check for empty array list returned for no matches
    result = FileExplorer.searchByKey(folder, "7");
    if (result.size() != 0) {
      for (int i = 0; i < result.size(); i++) {
        System.out.println(result.get(i));
      }
      System.out.println("CASE 2");
      return false;
    }
    return true;
  }

  public static boolean testSearchBySize(File folder) {
    // case 1 - check for correct array list with right amount and name of matches
    ArrayList<String> expected = new ArrayList<String>();
    expected.add("COVIDTracker.java");
    expected.add("COVIDTrackerTester.java");
    ArrayList<String> result = new ArrayList<String>();
    try {
      result = FileExplorer.searchBySize(folder, (long)6.5, (long)10.9);
      if (result.size() != 2 || !result.get(0).equals(expected.get(0))) {
        for (int i = 0; i < result.size(); i++) {
          System.out.println(result.get(i));
        }
        System.out.println("CASE 1");
        return false;
      }
    } catch (NoSuchElementException nse) {
      System.out.println(nse.getMessage());
    }
    // case 2 - check for empty array list returned for no matches
    result = FileExplorer.searchBySize(folder, 0, 0);
    if (result.size() != 0) {
      for (int i = 0; i < result.size(); i++) {
        System.out.println(result.get(i));
      }
      System.out.println("CASE 2");
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    System.out.println("testListContents: " + testListContents(new File("cs300")));
    System.out.println("testDeepListBaseCase: " + testDeepListBaseCase(new File("cs200")));
    // System.out.println("testDeepListRecursiveCase: " + testDeepListRecursiveCase(new
    // File("lecture notes")));
    System.out.println("testSearchByFileName: " + testSearchByFileName(new File("cs300")));
    System.out.println("testSearchByKey: " + testSearchByKey(new File("cs300")));
  }

}
