package com.codepoets.websimple.android;

import android.content.res.AssetManager;
import android.text.TextUtils;
import com.codepoets.websimple.filesystem.FileSystem;
import com.codepoets.websimple.filesystem.FileSystemFile;
import com.codepoets.websimple.filesystem.FileSystemUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.OverlappingFileLockException;
import java.util.Arrays;

public class AssetManagerFileSystem implements FileSystem {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AssetManagerFileSystem.class);
    public static final String WEB_ROOT = "web_root";
    private AssetManagerFile root;
	private AssetManager assetManager;

	public AssetManagerFileSystem(AssetManager assetManager) {
		this.assetManager = assetManager;
		root = new AssetManagerFile(assetManager, WEB_ROOT, null, null, true);
	    try {
		    buildFiles(assetManager, root);
	    } catch (IOException e) {
		    throw new IllegalStateException("Cannot create asset files system", e);
	    }
    }

    @Override
    public FileSystemFile root() throws IOException {
	    return root;
    }

	@Override
	public boolean exists(String path) {
		return getFile(path) != null;
	}

	@Override
	public InputStream open(String path) throws IOException {
		AssetManagerFile file = (AssetManagerFile) getFile(path);
		return assetManager.open(file.getInternalPath());
	}

	@Override
	public FileSystemFile getFile(String path) {
		if (path.startsWith(FileSystemUtils.PATH_SEPARATOR)) {
			path = path.substring(FileSystemUtils.PATH_SEPARATOR.length());
		}
		String[] parts = TextUtils.split(path, FileSystemUtils.PATH_SEPARATOR);
		AssetManagerFile curr = root;
		for (String part: parts) {
			curr = curr.find(part);
			if (curr == null) {
				return null;
			}
		}
		return curr;	}


	public void buildFiles(AssetManager assetManager, AssetManagerFile baseDir) throws IOException {
        String[] fileList = assetManager.list(baseDir.getInternalPath());

        if (fileList == null || fileList.length == 0) {
	        // baseDir must be a file
	        baseDir.setDirectory(false);
            return;
        }

        baseDir.setDirectory(true);
        for (String file: fileList) {
            AssetManagerFile assetManagerFile = new AssetManagerFile(assetManager, WEB_ROOT, baseDir.getPath(), file);
	        baseDir.addEntry(assetManagerFile);
            buildFiles(assetManager, assetManagerFile);
        }
    }
}
