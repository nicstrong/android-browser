package com.codepoets.websimple.android;

import android.content.res.AssetManager;
import com.codepoets.websimple.filesystem.FileSystem;
import com.codepoets.websimple.filesystem.FileSystemFile;

import java.io.IOException;

public class AssetManagerFileSystem implements FileSystem {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AssetManagerFileSystem.class);
    public static final String WEB_ROOT = "web_root";

    private AssetManager assetManager;

    public AssetManagerFileSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public FileSystemFile root() throws IOException {
        AssetManagerFile root = new AssetManagerFile(WEB_ROOT, null, null, true);
        buildFiles(root);
        return root;
    }

    public void buildFiles(AssetManagerFile baseDir) throws IOException {
        String[] fileList = assetManager.list(baseDir.getInternalPath());

        if (fileList == null || fileList.length == 0) {
            return;
        }

        baseDir.setDirectory(true);
        for (String file: fileList) {
            AssetManagerFile assetManagerFile = new AssetManagerFile(WEB_ROOT, baseDir.getPath(), file);
	        baseDir.addEntry(assetManagerFile);
            buildFiles(assetManagerFile);
        }
    }
}
