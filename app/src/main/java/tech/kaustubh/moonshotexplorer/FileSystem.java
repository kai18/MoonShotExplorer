package tech.kaustubh.moonshotexplorer;

import java.util.ArrayList;

/**
 * Created by kaustubh on 18/12/16.
 */

public interface FileSystem {

    public abstract String getPath(String partialPath);

    public abstract String[] cd(String dir);

    public abstract ArrayList<String> ls(String dir);

    public abstract int mkdir(String path);

    public abstract int opn(String path);

    public abstract int setRoot(String path);

    public String getRoot(String dir);

    public abstract String getPwd(String path);

    public abstract void setPwd(String path);

    public abstract int cpy(String source, String destination);

}
