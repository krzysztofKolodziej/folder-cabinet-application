package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FolderCabinetTest {

    private static final String FOLDER_NAME_A = "A";
    private static final String FOLDER_NAME_B = "B";
    private static final String FOLDER_NAME_C = "C";
    private static final String FOLDER_SIZE_SMALL = "SMALL";
    private static final String FOLDER_SIZE_MEDIUM = "MEDIUM";
    private static final String FOLDER_SIZE_LARGE = "LARGE";
    private static final String NON_EXISTING_FOLDER = "NonExisting";

    @Test
    void testFindFolderByName() {
        // Given
        Folder folderA = mock(Folder.class);
        when(folderA.getName()).thenReturn(FOLDER_NAME_A);
        when(folderA.getSize()).thenReturn(FOLDER_SIZE_SMALL);

        MultiFolder folderC = mock(MultiFolder.class);
        when(folderC.getName()).thenReturn(FOLDER_NAME_C);
        when(folderC.getSize()).thenReturn(FOLDER_SIZE_MEDIUM);
        when(folderC.getFolders()).thenReturn(List.of(folderA));

        FolderCabinet cabinet = new FolderCabinet(List.of(folderC));

        // When
        Optional<Folder> foundFolder = cabinet.findFolderByName(FOLDER_NAME_A);

        // Then
        assertTrue(foundFolder.isPresent());
        assertEquals(FOLDER_NAME_A, foundFolder.get().getName());
    }

    @Test
    void testFindFoldersBySize() {
        // Given
        Folder folderA = mock(Folder.class);
        when(folderA.getName()).thenReturn(FOLDER_NAME_A);
        when(folderA.getSize()).thenReturn(FOLDER_SIZE_SMALL);

        Folder folderB = mock(Folder.class);
        when(folderB.getName()).thenReturn(FOLDER_NAME_B);
        when(folderB.getSize()).thenReturn(FOLDER_SIZE_LARGE);

        MultiFolder folderC = mock(MultiFolder.class);
        when(folderC.getName()).thenReturn(FOLDER_NAME_C);
        when(folderC.getSize()).thenReturn(FOLDER_SIZE_MEDIUM);
        when(folderC.getFolders()).thenReturn(List.of(folderA, folderB));

        FolderCabinet cabinet = new FolderCabinet(List.of(folderC));

        // When
        List<Folder> smallFolders = cabinet.findFoldersBySize(FOLDER_SIZE_SMALL);

        // Then
        assertEquals(1, smallFolders.size());
        assertEquals(folderA, smallFolders.get(0));
    }

    @Test
    void testCount() {
        // Given
        Folder folderA = mock(Folder.class);
        Folder folderB = mock(Folder.class);

        MultiFolder folderC = mock(MultiFolder.class);
        when(folderC.getFolders()).thenReturn(List.of(folderA, folderB));

        FolderCabinet cabinet = new FolderCabinet(List.of(folderC));

        // When
        int totalFolders = cabinet.count();

        // Then
        assertEquals(3, totalFolders);
    }

    @Test
    void testFindFolderByName_NotFound() {
        // Given
        Folder folderA = mock(Folder.class);
        when(folderA.getName()).thenReturn(FOLDER_NAME_A);

        FolderCabinet cabinet = new FolderCabinet(List.of(folderA));

        // When
        Optional<Folder> foundFolder = cabinet.findFolderByName(NON_EXISTING_FOLDER);

        // Then
        assertFalse(foundFolder.isPresent());
    }
}