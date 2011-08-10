package com.codepoets.websimple.android;

import android.content.res.AssetManager;
import android.text.TextUtils;
import com.codepoets.websimple.filesystem.FileSystem;
import com.codepoets.websimple.filesystem.FileSystemFile;
import com.codepoets.websimple.filesystem.FileSystemUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.channels.OverlappingFileLockException;
import java.util.Arrays;

public class AssetManagerFileSystem implements FileSystem {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AssetManagerFileSystem.class);
    public static final String WEB_ROOT = "web_root";
    private AssetManagerFile root;

    public AssetManagerFileSystem(AssetManager assetManager) {
	    root = new AssetManagerFile(WEB_ROOT, null, null, true);
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
		String[] parts = TextUtils.split(path, FileSystemUtils.PATH_SEPARATOR);
		logger.debug("exists({}) split path {}", path, Arrays.toString(parts));
		AssetManagerFile curr = root;
		for (String part: parts) {
			curr = curr.find(part);
			if (curr == null) {
				return false;
			}
		}
		return true;
	}


	public void buildFiles(AssetManager assetManager, AssetManagerFile baseDir) throws IOException {
        String[] fileList = assetManager.list(baseDir.getInternalPath());

        if (fileList == null || fileList.length == 0) {
	        // baseDir must be a file
	        baseDir.setDirectory(false);
            return;
        }

        baseDir.setDirectory(true);
        for (String file: fileList) {
            AssetManagerFile assetManagerFile = new AssetManagerFile(WEB_ROOT, baseDir.getPath(), file);
	        baseDir.addEntry(assetManagerFile);
            buildFiles(assetManager, assetManagerFile);
        }
    }
}
