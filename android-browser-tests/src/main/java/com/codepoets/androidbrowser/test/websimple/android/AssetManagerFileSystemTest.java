package com.codepoets.androidbrowser.test.websimple.android;

import android.test.AndroidTestCase;
import android.test.MoreAsserts;
import com.codepoets.websimple.android.AssetManagerFileSystem;
import com.codepoets.websimple.filesystem.FileSystemFile;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public class AssetManagerFileSystemTest extends AndroidTestCase {
	private AssetManagerFileSystem fileSystem;

	@Override
	protected void setUp() throws Exception {
		fileSystem = new AssetManagerFileSystem(getContext().getAssets());
	}

	public void test_can_create_root() throws IOException {
		assertNotNull(fileSystem.root());
	}

	public void test_root_is_correct_path() throws IOException {
		assertEquals(fileSystem.root().getPath(), "/");
	}

	public void test_root_paths_correct() throws IOException {
		assertPathMatches(fileSystem.root().getEntries(), "/images", "/index.html", "/test.txt");
	}

	public void test_root_dir_flags_correct() throws IOException {
		assertDirFlagMatches(fileSystem.root().getEntries(), true, false, false);
	}

	private void assertPathMatches(Collection<? extends FileSystemFile> entries, String... expectedValues) {
		assertMatches(entries, new Function<FileSystemFile, String>() {
			@Override
			public String apply(FileSystemFile file) {
				return file.getPath();
			}
		}, expectedValues);
	}

	private void assertDirFlagMatches(Collection<? extends FileSystemFile> entries, Boolean... expectedValues) {
		assertMatches(entries, new Function<FileSystemFile, Boolean>() {
			@Override
			public Boolean apply(FileSystemFile file) {
				return file.isDirectory();
			}
		}, expectedValues);
	}


	private <T> void assertMatches(Collection<? extends FileSystemFile> entries, Function<FileSystemFile, T> trans, T... expectedValues) {
		HashSet<T> actual = Sets.newHashSet(Collections2.transform(entries,trans));
		HashSet<T> expected = Sets.newHashSet(expectedValues);
		MoreAsserts.assertEquals(expected, actual);
	}

}
