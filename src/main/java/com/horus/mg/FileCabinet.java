package com.horus.mg;

import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet {

    private List<Folder> folders;

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return List.of();
    }

    @Override
    public int count() {
        return 0;
    }
}
