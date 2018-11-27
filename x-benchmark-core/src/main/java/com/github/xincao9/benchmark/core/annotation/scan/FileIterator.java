package com.github.xincao9.benchmark.core.annotation.scan;

import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 *
 * @author 510655387@qq.com
 */
public final class FileIterator {

    private final Deque<File> stack = new LinkedList();
    private int rootCount;
    private File current;

    public FileIterator(File[] filesOrDirectories) {
        addReverse(filesOrDirectories);
        this.rootCount = this.stack.size();
    }

    public File getFile() {
        return this.current;
    }

    public boolean isRootFile() {
        if (this.current == null) {
            throw new NoSuchElementException();
        }
        return this.stack.size() < this.rootCount;
    }

    public File next() throws IOException {
        if (this.stack.isEmpty()) {
            this.current = null;
            return null;
        }
        this.current = ((File) this.stack.removeLast());
        if (this.current.isDirectory()) {
            if (this.stack.size() < this.rootCount) {
                this.rootCount = this.stack.size();
            }
            addReverse(this.current.listFiles());
            return next();
        }
        return this.current;
    }

    private void addReverse(File[] files) {
        for (int i = files.length - 1; i >= 0; i--) {
            this.stack.add(files[i]);
        }
    }
}
