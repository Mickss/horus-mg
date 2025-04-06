package com.horus.mg;

import java.util.List;

public record MultiFolderImpl(String name, String size, List<Folder> folders) implements MultiFolder {
}
