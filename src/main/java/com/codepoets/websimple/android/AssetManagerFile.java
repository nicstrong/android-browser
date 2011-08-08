package com.codepoets.websimple.android;

import org.apache.commons.io.FilenameUtils;

import java.util.List;

public class AssetManagerFile {
    private String root;
    private String path;
    private boolean directory;
    private List<AssetManagerFile> files;

    public AssetManagerFile(String root, String path) {
        this(root, path, false);
    }

    public AssetManagerFile(String root, String path, boolean directory) {
        this.root = root;
        this.path = path;
        this.directory = directory;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public String getFilePath() {
        if (path == null) {
            return root;
        }
        return FilenameUtils.concat(root, path);
    }

    public String getPath() {
        if (path == null) {
            return "/";
        }
        return getFilePath().substring(AssetManagerFileSystem.WEB_ROOT.length());
    }
}
