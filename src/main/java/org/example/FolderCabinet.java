package org.example;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class FolderCabinet implements Cabinet {

    private List<Folder> folders;

    @Override
    public Optional<Folder> findFolderByName(String name) {
        return folders.stream()
                .flatMap(this::collectAllFolders)
                .filter(folder -> name.equals(folder.getName()))
                .findAny();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return folders.stream()
                .flatMap(this::collectAllFolders)
                .filter(folder -> size.equals(folder.getSize()))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return (int) folders.stream()
                .flatMap(this::collectAllFolders)
                .count();
    }

    private Stream<Folder> collectAllFolders(Folder folder) {
        return Stream.concat(
                Stream.of(folder),
                Optional.ofNullable(folder)
                        .filter(MultiFolder.class::isInstance)
                        .map(MultiFolder.class::cast)
                        .stream()
                        .flatMap(multiFolder -> multiFolder.getFolders().stream().flatMap(this::collectAllFolders))
        );
    }
}