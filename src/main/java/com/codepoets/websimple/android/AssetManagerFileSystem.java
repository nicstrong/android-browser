package com.codepoets.websimple.android;

import android.content.res.AssetManager;
import com.codepoets.websimple.filesystem.FileSystem;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class AssetManagerFileSystem implements FileSystem {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AssetManagerFileSystem.class);
    public static final String WEB_ROOT = "web_root";

    private AssetManager assetManager;

    public AssetManagerFileSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public List<String> getFiles() throws IOException {
        List<AssetManagerFile> files = new ArrayList<AssetManagerFile>();
        AssetManagerFile root = new AssetManagerFile(WEB_ROOT, null, true);
        files.add(root);
        buildFiles(root, files);

        List<String> fileList = new ArrayList<String>();
        for (AssetManagerFile file: files) {
            logger.debug("FilePath: {}", file.getFilePath());
            logger.debug("Path: {} isDir: {}", file.getPath(), file.isDirectory());
            fileList.add(file.getPath());
        }
        return fileList;
    }

    public void buildFiles(AssetManagerFile baseDir, List<AssetManagerFile> files) throws IOException {
        String[] fileList = assetManager.list(baseDir.getFilePath());

        if (fileList == null || fileList.length == 0) {
            return;
        }

        baseDir.setDirectory(true);
        for (String file: fileList) {
            AssetManagerFile assetManagerFile = new AssetManagerFile(baseDir.getFilePath(), file);
            files.add(assetManagerFile);
            buildFiles(assetManagerFile, files);
        }
    }

//    public File getFiles(String path) {
//
//    }

    public String buildPath(String path) {
        return FilenameUtils.concat(WEB_ROOT, path);
    }


}
