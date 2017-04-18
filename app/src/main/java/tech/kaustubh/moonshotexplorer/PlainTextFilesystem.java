package tech.kaustubh.moonshotexplorer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by kaustubh on 18/12/16.
 */

public class PlainTextFilesystem implements FileSystem {

    boolean goingBack = false;
    private String rootName;
    private File root;
    private String pwd = null;
    private static PlainTextFilesystem plainTextFilesystem = null;
    private Stack <String> dirStack;

    private PlainTextFilesystem(String rootName)
    {
        this.rootName = rootName;
        root = new File(rootName);
        pwd = rootName;
        dirStack = new Stack<>();
    }

    public static PlainTextFilesystem getFilesystemHandler()
    {
        if(plainTextFilesystem == null)
            plainTextFilesystem = new PlainTextFilesystem(Environment.getExternalStorageDirectory()
                    .getAbsolutePath());
        return plainTextFilesystem;
    }

    /*public plainTextFilesystem(File root)
    {
     this.root = root;
    }*/

    @Override
    public String getPath(String partialPath) {

        return null;
    }

    private ArrayList<String> getNames(File directories[])
    {
        ArrayList<String> directoryNames = new ArrayList (directories.length);
        for (File file: directories)
            directoryNames.add(file.getName());

        return directoryNames;
    }


    @Override
    public String[] cd(String dir) {

        return new String[0];
    }

    @Override
    public ArrayList<String> ls(String dir) {
        ArrayList <String> dirList = null;
        if(dir.equals(".."))
        {
            if(!dirStack.empty())
            {
                pwd = dirStack.peek();
                Log.d("Popping", dirStack.pop());
            }
            else
                Log.d("Stack","Empty");
        }
        else if(pwd != null)
            dirStack.push(pwd);

        if(!dir.equals(rootName) && !dir.equals("..") && pwd!=null)
            pwd = pwd + dir;
        if(dir.equals(rootName))
            pwd = rootName;

        if (pwd == null)
            pwd = dir;

        Log.d("Going", pwd);
        File pwdDir = new File(pwd);
        if(pwdDir != null)
            dirList = this.getNames(pwdDir.listFiles());

        return dirList;
    }

    @Override
    public int mkdir(String path) {
        try {
            File file = new File(pwd + path);
            if (file.createNewFile())
                return 1;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Intent opn(String path, Context context)
    {
        String filePath = getPwd()+"/"+path;
        Log.d("Path", filePath);
        File file = new File(filePath);
        if(file.isFile())
        {
            Intent fileIntent = new Intent(Intent.ACTION_VIEW);
            MimeTypeMap map = MimeTypeMap.getSingleton();
            String extension = map.getFileExtensionFromUrl(file.getName());
            String type = map.getMimeTypeFromExtension(extension);

//            Log.d("Type", type);

            Uri fileUri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName()+".provider", file);

            Log.d("Address", fileUri.toString());
            fileIntent.setDataAndType(fileUri, type);
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return  fileIntent;
        }
        return null;
    }

    @Override
    public String  getRoot(String dir) {
        return rootName;
    }

    @Override
    public int setRoot(String path) {
        this.rootName = path;
        return 0;
    }

    @Override
    public String getPwd() {
        return pwd;
    }

    @Override
    public void setPwd(String path) {

        this.pwd = path;
    }

    @Override
    public int cpy(String source, String destination) {
        return 0;
    }

}
