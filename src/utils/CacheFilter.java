package utils;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * Classe filtre d'exclusion des fichiers de cache
 */
public class CacheFilter implements IOFileFilter {

    String cache = ".cache";

    @Override
    public boolean accept(File file) {

        return !file.getName().contains(cache);
    }

    @Override
    public boolean accept(File dir, String name) {

        throw new UnsupportedOperationException();
    }
}