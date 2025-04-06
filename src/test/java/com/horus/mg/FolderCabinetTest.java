package com.horus.mg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FolderCabinetTest {

    private FolderCabinet folderCabinet;

    @BeforeEach
    void setUp() {
        List<Folder> sampleFolders = createSampleFolders();
        folderCabinet = new FolderCabinet(sampleFolders);
    }

    @Test
    void findFolderByName() {
        // when
        Optional<Folder> folderByName = folderCabinet.findFolderByName("windows");

        // then
        if (folderByName.isEmpty()) {
            throw new IllegalStateException("Should contain 'windows' folder on the list");
        }
        Folder folder = folderByName.get();
        assertEquals("windows", folder.name());
    }

    @Test
    void findFolderByNameNoMatching() {
        // when
        Optional<Folder> folderByName = folderCabinet.findFolderByName("Linux");

        // then
        assertTrue(folderByName.isEmpty(), "The 'Linux' folder should not be present");
    }

    @Test
    void findMultiFolderByName() {
        // when
        Optional<Folder> folderByName = folderCabinet.findFolderByName("windows");

        // then
        assertTrue(folderByName.isPresent(), "The windows folder is present");
        Folder folder = folderByName.get();
        assertEquals("windows", folder.name());
    }

    @Test
    void findMultiFolderByNameLowerLevel() {
        // when
        Optional<Folder> folderByName = folderCabinet.findFolderByName("Temp");

        // then
        assertTrue(folderByName.isPresent(), "The temp folder is present");
        Folder folder = folderByName.get();
        assertEquals("Temp", folder.name());
    }

    @Test
    void findFoldersBySizeShouldReturnSmallFolders() {
        // when
        List<Folder> smallFolders = folderCabinet.findFoldersBySize("SMALL");

        // then
        assertEquals(3, smallFolders.size(), "Should return 3 folder with SMALL size");
        smallFolders.forEach(f -> assertTrue(List.of("Program Files", "Config", "Temp").contains(f.name())));
    }

    @Test
    void findFoldersBySizeShouldReturnMediumFolders() {
        // when
        List<Folder> mediumFolders = folderCabinet.findFoldersBySize("MEDIUM");

        // then
        assertEquals(1, mediumFolders.size(), "Should return 1 folder with MEDIUM size");
        assertEquals("Users", mediumFolders.getFirst().name(), "The folder should be 'Users'");
    }

    @Test
    void findFoldersBySizeShouldReturnLargeFolders() {
        // when
        List<Folder> largeFolders = folderCabinet.findFoldersBySize("LARGE");

        // then
        assertEquals(3, largeFolders.size(), "Should return 3 folder with LARGE size");
        largeFolders.forEach(f -> assertTrue(List.of("Microsoft", "System32", "windows").contains(f.name())));
    }

    @Test
    void countAllFolders() {
        // when
        int totalCount = folderCabinet.count();

        // then
        assertEquals(7, totalCount, "Should count all folders including nested ones");
    }

    private List<Folder> createSampleFolders() {
        List<Folder> folderList = new ArrayList<>();
        folderList.add(new FolderImpl("Users", "MEDIUM"));
        folderList.add(new FolderImpl("Program Files", "SMALL"));

        List<Folder> system32SubFolders = new ArrayList<>();
        system32SubFolders.add(new FolderImpl("Microsoft", "LARGE"));
        system32SubFolders.add(new FolderImpl("Config", "SMALL"));

        List<Folder> windowsSubfolders = new ArrayList<>();
        windowsSubfolders.add(new MultiFolderImpl("System32", "LARGE", system32SubFolders));
        windowsSubfolders.add(new FolderImpl("Temp", "SMALL"));

        folderList.add(new MultiFolderImpl("windows", "LARGE", windowsSubfolders));

        return folderList;
    }
}
