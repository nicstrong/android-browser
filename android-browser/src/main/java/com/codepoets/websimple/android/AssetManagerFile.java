package com.codepoets.websimple.android;

import com.codepoets.websimple.filesystem.FileSystemFile;

import java.util.*;

import static com.codepoets.websimple.filesystem.FileSystemUtils.join;
import static org.apache.commons.io.FilenameUtils.concat;

public class AssetManagerFile implements FileSystemFile {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AssetManagerFile.class);
    private String root;
	private String basePath;
	private String path;
    private boolean directory;
    private Map<String, AssetManagerFile> entries;

    public AssetManagerFile(String root, String basePath, String path) {
        this(root, basePath, path, false);
    }

    public AssetManagerFile(String root, String basePath, String path, boolean directory) {
        this.root = root;
	    this.basePath = basePath;
	    this.path = path;
        this.directory = directory;
	    this.entries = new HashMap<String, AssetManagerFile>();
    }

    @Override
    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

	public void addEntry(AssetManagerFile file) {
		entries.put(file.path, file);
	}

    public String getInternalPath() {
	    if (basePath == null) {
		    return root;
	    }
        if (path == null) {
	        return concat(root, basePath);
        }
        return join(join(root, basePath), path);
    }

    @Override
    public String getPath() {
        if (basePath == null) {
            return "/";
        }
        return join(basePath, path);
    }

	@Override
	public Collection<? extends FileSystemFile> getEntries() {
		return entries.values();
	}

	public AssetManagerFile find(String child) {
		if (entries.containsKey(child)) {
			return entries.get(child);
		}
		return null;
	}

	@Override
	public String toString() {
		return "AssetManagerFile{" +
				"root='" + root + '\'' +
				", basePath='" + basePath + '\'' +
				", path='" + path + '\'' +
				", num_entries=" + entries.size() +
				", directory=" + directory +
				'}';
	}
}
