package com.codepoets.websimple.http.handler;

import android.webkit.MimeTypeMap;
import com.codepoets.websimple.filesystem.FileSystem;
import com.codepoets.websimple.filesystem.FileSystemFile;
import com.codepoets.websimple.http.HttpVerbs;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class DefaultFileHandler extends BaseHttpRequestHandler {
	private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultFileHandler.class);
	private FileSystem fileSystem;

	public DefaultFileHandler(FileSystem fileSystem) {
		super(new String[] { HttpVerbs.GET });
		this.fileSystem = fileSystem;
	}

	@Override
	public void handleGet(String path, HttpRequest request, HttpResponse response, HttpContext context) {
		logger.info("GET {}", path);

		FileSystemFile file = fileSystem.getFile(path);
		logger.debug("Found file  {}", file);
		try {
			long length = file.length();
			logger.debug("{} is {} bytes", file.getPath(), length);
			InputStreamEntity entity = new InputStreamEntity(fileSystem.open(path), length);
			String ext = FilenameUtils.getExtension(file.getPath());
			logger.debug("Mapping {} ext to mimetype", ext);
			if (MimeTypeMap.getSingleton().hasExtension(ext)) {
				String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
				logger.debug("Setting mimeType to {}", mimeType);
				entity.setContentType(mimeType);
			} else {
				entity.setContentType("application/octet-stream");
			}
			response.setEntity(entity);
		} catch (IOException e) {
			logger.error("IOException reading " + path, e);
			response.setStatusCode(HttpStatus.SC_FORBIDDEN);
		}

		response.setStatusCode(HttpStatus.SC_OK);
	}
}
