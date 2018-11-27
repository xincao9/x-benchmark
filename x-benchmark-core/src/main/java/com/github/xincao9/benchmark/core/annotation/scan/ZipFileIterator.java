package com.github.xincao9.benchmark.core.annotation.scan;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author 510655387@qq.com
 */
public final class ZipFileIterator {

    private final ZipFile zipFile;
    private final Enumeration<? extends ZipEntry> entries;
    private ZipEntry current;

    ZipFileIterator(File file) throws IOException {
        this.zipFile = new ZipFile(file);
        this.entries = this.zipFile.entries();
    }

    public ZipEntry getEntry() {
        return this.current;
    }

    public InputStream next() throws IOException {
        while (this.entries.hasMoreElements()) {
            this.current = ((ZipEntry) this.entries.nextElement());
            if (!this.current.isDirectory()) {
                return this.zipFile.getInputStream(this.current);
            }
        }
        try {
            this.zipFile.close();
        } catch (IOException ex) {
        }
        return null;
    }
}
