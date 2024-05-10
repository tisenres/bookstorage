package com.tisenres.bookstorage.features.book.model;

public class Author {
    private String authorName;
    private int symbolCount;

    public Author(String authorName, int symbolCount) {
        this.authorName = authorName;
        this.symbolCount = symbolCount;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getSymbolCount() {
        return symbolCount;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setSymbolCount(int symbolCount) {
        this.symbolCount = symbolCount;
    }
}
