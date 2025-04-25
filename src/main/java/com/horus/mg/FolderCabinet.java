package com.horus.mg;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FolderCabinet implements Cabinet {
    private final List<Folder> folders;

    public FolderCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        List<Folder> flattenFolders = (iterateFolderList(folders));
        return flattenFolders.stream().filter(f -> name.equals(f.name())).findAny();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        List<Folder> flattenFolders = (iterateFolderList(folders));
        return flattenFolders.stream().filter(f -> f.size().equals(size)).toList();
    }

    private List<Folder> iterateFolderList(List<Folder> toIterate) {
        List<Folder> folders = new ArrayList<>();
        for (Folder folder : toIterate) {
            if (folder instanceof MultiFolder) {
                List<Folder> subFolders = ((MultiFolder) folder).folders();
                folders.addAll(iterateFolderList(subFolders));
                folders.add(folder);
            } else {
                folders.add(folder);
            }
        }
        return folders;
    }

    @Override
    public int count() {
        return countFolders(folders);
    }

    private int countFolders(List<Folder> foldersToCount) {
        int count = 0;
        for (Folder folder : foldersToCount) {
            count++;
            if (folder instanceof MultiFolder multiFolder) {
                count += countFolders(multiFolder.folders());
            }
        }
        return count;
    }
}
