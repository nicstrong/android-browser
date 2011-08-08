package com.codepoets.websimple.android;

import android.content.res.AssetManager;
import com.codepoets.websimple.filesystem.FileSystem;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;

public class AssetManagerFileSystem implements FileSystem {
    private static final String WEB_ROOT = "web_root";

    private AssetManager assetManager;

    public AssetManagerFileSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public String[] getFiles() throws IOException {
        return assetManager.list(WEB_ROOT);
    }

//    public File getFiles(String path) {
//
//    }

    public String buildPath(String path) {
        return FilenameUtils.concat(WEB_ROOT, path);
    }


}
