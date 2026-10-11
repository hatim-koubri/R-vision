package com.demo.util;

import java.util.List;

public class PaginationResult<T> {
    private final List<T> elements;
    private final int pageCourante;
    private final int taillePage;
    private final long totalElements;
    private final int totalPages;

    public PaginationResult(List<T> elements, int pageCourante, int taillePage, long totalElements) {
        this.elements = elements;
        this.pageCourante = pageCourante;
        this.taillePage = taillePage;
        this.totalElements = totalElements;
        this.totalPages = taillePage == 0 ? 0 : (int) Math.ceil((double) totalElements / taillePage);
    }

    public List<T> getElements() { return elements; }
    public int getPageCourante() { return pageCourante; }
    public int getTaillePage() { return taillePage; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
}