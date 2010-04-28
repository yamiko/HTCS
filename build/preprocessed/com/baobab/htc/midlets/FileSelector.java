/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baobab.htc.midlets;

/**
 * @author yamiko
 */
import java.io.*;
import java.util.*;
import javax.microedition.io.*;
import javax.microedition.io.file.*;
import javax.microedition.lcdui.*;

// Simple file selector class.
// It navigates the file system and shows images currently available
class FileSelector {
       /* extends List
        implements CommandListener, FileSystemListener {

    private final static Image ROOT_IMAGE =
            ImageViewerMidlet.makeImage("/Reen-Mary.jpg");
    private final static Image FOLDER_IMAGE =
            ImageViewerMidlet.makeImage("/Reen-Mary.jpg");
    private final static Image FILE_IMAGE =
            ImageViewerMidlet.makeImage("/Reen-Mary.jpg");
    private final OperationsQueue queue = new OperationsQueue();
    private final static String FILE_SEPARATOR =
            (System.getProperty("file.separator") != null)
            ? System.getProperty("file.separator")
            : "/";
    private final static String UPPER_DIR = "..";
    private final ImageViewerMidlet midlet;
    private final Command openCommand =
            new Command("Open", Command.ITEM, 1);
    private final Command createDirCommand =
            new Command("Create new directory", Command.ITEM, 2);
    private final Command deleteCommand =
            new Command("Delete", Command.ITEM, 3);
    private final Command renameCommand =
            new Command("Rename", Command.ITEM, 4);
    private final Command exitCommand =
            new Command("Exit", Command.EXIT, 1);
    private final static int RENAME_OP = 0;
    private final static int MKDIR_OP = 1;
    private final static int INIT_OP = 2;
    private final static int OPEN_OP = 3;
    private final static int DELETE_OP = 4;
    private Vector rootsList = new Vector();
// Stores the current root, if null we are showing all the roots
    private FileConnection currentRoot = null;
// Stores a suggested title in case it is available
    private String suggestedTitle = null;

    FileSelector(ImageViewerMidlet midlet) {
        super("Image Viewer", List.IMPLICIT);
        this.midlet = midlet;
        addCommand(openCommand);
        addCommand(createDirCommand);
        addCommand(deleteCommand);
        addCommand(renameCommand);
        addCommand(exitCommand);
        setSelectCommand(openCommand);
        setCommandListener(this);
        queue.enqueueOperation(new ImageViewerOperations(INIT_OP));
        FileSystemRegistry.addFileSystemListener(FileSelector.this);
    }

    void stop() {
        if (currentRoot != null) {
            try {
                currentRoot.close();

            } catch (IOException e) {
            }
        }
        queue.abort();
        FileSystemRegistry.removeFileSystemListener(this);
    }

    void inputReceived(String input, int code) {
        switch (code) {
            case RENAME_OP:
                queue.enqueueOperation(new ImageViewerOperations(
                        input,
                        RENAME_OP));
                break;
            case MKDIR_OP:
                queue.enqueueOperation(new ImageViewerOperations(
                        input,
                        MKDIR_OP));
                break;
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == openCommand) {
            queue.enqueueOperation(new ImageViewerOperations(OPEN_OP));
        } else if (c == renameCommand) {
            queue.enqueueOperation(new ImageViewerOperations(RENAME_OP));
        } else if (c == deleteCommand) {
            queue.enqueueOperation(new ImageViewerOperations(DELETE_OP));
        } else if (c == createDirCommand) {
            queue.enqueueOperation(new ImageViewerOperations(MKDIR_OP));
        } else if (c == exitCommand) {
            midlet.fileSelectorExit();
        }
    }
// Listen for changes in the roots

    public void rootChanged(int state, String rootName) {
        queue.enqueueOperation(new ImageViewerOperations(INIT_OP));
    }

    private void displayAllRoots() {
        setTitle("Image Viewer - [Roots]");
        deleteAll();
        Enumeration roots = rootsList.elements();
        while (roots.hasMoreElements()) {
            String root = (String) roots.nextElement();
            append(root.substring(1), ROOT_IMAGE);
        }
        currentRoot = null;
    }

    private void createNewDir() {
        if (currentRoot == null) {
            midlet.showMsg("Is not possible to create a new root");
        } else {
            midlet.requestInput("New dir name", "", MKDIR_OP);
        }
    }

    private void createNewDir(String newDirURL) {
        if (currentRoot != null) {
            try {
                FileConnection newDir =
                        (FileConnection) Connector.open(
                        currentRoot.getURL() + newDirURL,
                        Connector.WRITE);
                newDir.mkdir();
                newDir.close();
            } catch (IOException e) {
                midlet.showError(e);
            } catch (SecurityException e) {
                midlet.showMsg(e.getMessage());
            }
            displayCurrentRoot();
        }
    }

    private void loadRoots() {
        if (!rootsList.isEmpty()) {
            rootsList.removeAllElements();
        }
        try {
            Enumeration roots = FileSystemRegistry.listRoots();
            midlet.showMsg("Before getting elements on root");
            int elements = 0;
            String elementNames  = "";
            while (roots.hasMoreElements()) {
            midlet.showMsg("Adding element from root");
                String elementName = (String) roots.nextElement();
                rootsList.addElement(FILE_SEPARATOR
                        + elementName);
                elements++;
                elementNames+=elementName;
            }
            midlet.showMsg("After getting elements on root " + elements + " " + elementNames);
        } catch (SecurityException e) {
            midlet.showMsg(e.getMessage());
        }
    }

    private void deleteCurrent() {
        if (currentRoot == null) {
            midlet.showMsg("Is not possible to delete a root");
        } else {
            int selectedIndex = getSelectedIndex();
            if (selectedIndex >= 0) {
                String selectedFile = getString(selectedIndex);
                if (selectedFile.equals(UPPER_DIR)) {
                    midlet.showMsg("Is not possible to delete an upper dir");
                } else {
                    try {
                        FileConnection fileToDelete =
                                (FileConnection) Connector.open(
                                currentRoot.getURL() + selectedFile,
                                Connector.WRITE);
                        if (fileToDelete.exists()) {
                            fileToDelete.delete();
                        } else {
                            midlet.showMsg("File "
                                    + fileToDelete.getName() + " does not exists");
                        }
                        fileToDelete.close();
                    } catch (IOException e) {
                        midlet.showError(e);
                    } catch (SecurityException e) {
                        midlet.showError(e);
                    }
                    displayCurrentRoot();
                }
            }
        }
    }

    private void renameCurrent() {
        if (currentRoot == null) {
            midlet.showMsg("Is not possible to rename a root");
        } else {
            int selectedIndex = getSelectedIndex();
            if (selectedIndex >= 0) {
                String selectedFile = getString(selectedIndex);
                if (selectedFile.equals(UPPER_DIR)) {
                    midlet.showMsg("Is not possible to rename the upper dir");
                } else {
                    midlet.requestInput("New name", selectedFile, RENAME_OP);
                }
            }
        }
    }

    private void renameCurrent(String newName) {
        if (currentRoot == null) {
            midlet.showMsg("Is not possible to rename a root");
        } else {
            int selectedIndex = getSelectedIndex();
            if (selectedIndex >= 0) {
                String selectedFile = getString(selectedIndex);
                if (selectedFile.equals(UPPER_DIR)) {
                    midlet.showMsg("Is not possible to rename the upper dir");
                } else {
                    try {
                        FileConnection fileToRename =
                                (FileConnection) Connector.open(
                                currentRoot.getURL() + selectedFile,
                                Connector.WRITE);
                        if (fileToRename.exists()) {
                            fileToRename.rename(newName);
                        } else {
                            midlet.showMsg("File "
                                    + fileToRename.getName() + " does not exists");
                        }
                        fileToRename.close();
                    } catch (IOException e) {
                        midlet.showError(e);
                    } catch (SecurityException e) {
                        midlet.showError(e);
                    }
                    displayCurrentRoot();
                }
            }
        }
    }

    private void openSelected() {
        int selectedIndex = getSelectedIndex();
        if (selectedIndex >= 0) {
            String selectedFile = getString(selectedIndex);
            if (selectedFile.endsWith(FILE_SEPARATOR)) {
                try {
                    if (currentRoot == null) {
                        currentRoot = (FileConnection) Connector.open(
                                "file:///" + selectedFile, Connector.READ);
                    } else {
                        currentRoot.setFileConnection(selectedFile);
                    }
                    displayCurrentRoot();
                } catch (IOException e) {
                    midlet.showError(e);
                } catch (SecurityException e) {
                    midlet.showError(e);
                }
            } else if (selectedFile.equals(UPPER_DIR)) {
                if (rootsList.contains(currentRoot.getPath()
                        + currentRoot.getName())) {
                    displayAllRoots();
                } else {
                    try {
                        currentRoot.setFileConnection(UPPER_DIR);
                        displayCurrentRoot();
                    } catch (IOException e) {
                        midlet.showError(e);
                    } catch (SecurityException e) {
                        midlet.showMsg(e.getMessage());
                    }
                }
            } else {
                String url = currentRoot.getURL() + selectedFile;
                midlet.displayImage(url);
            }
        }
    }

    private void displayCurrentRoot() {
        try {
            setTitle("Image Viewer - ["
                    + ((suggestedTitle != null) ? suggestedTitle : currentRoot.getURL())
                    + "]");
// open the root
            deleteAll();
            append(UPPER_DIR, FOLDER_IMAGE);
// list all dirs
            Enumeration listOfDirs = currentRoot.list("*", false);
            while (listOfDirs.hasMoreElements()) {
                String currentDir = (String) listOfDirs.nextElement();
                if (currentDir.endsWith(FILE_SEPARATOR)) {
                    append(currentDir, FOLDER_IMAGE);
                }
            }
// list all png files and don't show hidden files
            Enumeration listOfFiles = currentRoot.list("*.png", false);
            while (listOfFiles.hasMoreElements()) {
                String currentFile = (String) listOfFiles.nextElement();
                if (currentFile.endsWith(FILE_SEPARATOR)) {
                    append(currentFile, FOLDER_IMAGE);
                } else {
                    append(currentFile, FILE_IMAGE);
                }
            }
// also list the jpg files
            listOfFiles = currentRoot.list("*.jpg", false);
            while (listOfFiles.hasMoreElements()) {
                String currentFile = (String) listOfFiles.nextElement();
                if (currentFile.endsWith(FILE_SEPARATOR)) {
                    append(currentFile, FOLDER_IMAGE);
                } else {
                    append(currentFile, FILE_IMAGE);
                }
            }
        } catch (IOException e) {
            midlet.showError(e);
        } catch (SecurityException e) {
            midlet.showError(e);
        }
    }

    private class ImageViewerOperations implements Operation {

        private final String parameter;
        private final int operationCode;

        ImageViewerOperations(int operationCode) {
            this.parameter = null;
            this.operationCode = operationCode;
        }

        ImageViewerOperations(String parameter, int operationCode) {
            this.parameter = parameter;
            this.operationCode = operationCode;
        }

        public void execute() {
            switch (operationCode) {
                case INIT_OP:
                    String initDir = System.getProperty("fileconn.dir.photos");
                    //String initDir = "file:///c:/";//System.getProperty("fileconn.dir.photos");
                    suggestedTitle = System.getProperty("fileconn.dir.photos.name");
                    //suggestedTitle = "Roots"; //System.getProperty("fileconn.dir.photos.name");
                    loadRoots();
                    if (initDir != null) {
                        try {
                            currentRoot =
                                    (FileConnection) Connector.open(
                                    initDir,
                                    Connector.READ);
                            displayCurrentRoot();
                        } catch (IOException e) {
                            midlet.showError(e);
                            displayAllRoots();
                        } catch (SecurityException e) {
                            midlet.showError(e);
                        }
                    } else {
                        displayAllRoots();
                    }
                    break;
                case OPEN_OP:
                    openSelected();
                    break;
                case DELETE_OP:
                    deleteCurrent();
                    break;
                case RENAME_OP:
                    if (parameter != null) {
                        renameCurrent(parameter);
                    } else {
                        renameCurrent();
                    }
                    break;
                case MKDIR_OP:
                    if (parameter != null) {
                        createNewDir(parameter);
                    } else {
                        createNewDir();
                    }
            }
        }
    }*/
}
