package com.letsstartcoding.springbootrestapiexample.utility;

import java.util.List;

public class PagedResult<T> {

	 public static final long DEFAULT_OFFSET = 0;
	    public static final int DEFAULT_MAX_NO_OF_ROWS = 100;
	    private int offset;
	    private int limit;
	    private int currentPage;
	    private Long totalPage;
	    private Long totalRecords;
	    
	    public Long getTotalRecords() {
			return totalRecords;
		}

		private boolean hasMore;
	    private int totalElements;
	    private List<T> elements;
	    
	    public PagedResult(List<T> elements, int totalElements, int offset, int limit, Long totalRecords, int currentPage) 
	    {
	        this.elements = elements;
	        this.totalElements = totalElements; 
	        this.totalRecords = (totalRecords != null ? totalRecords : 0);
	        Long totalPageResult = ((this.totalRecords + limit - 1) / limit);
	        this.currentPage = (currentPage < 0 ? 1 : currentPage);
	        this.totalPage = (totalPageResult < 0 ? 1 : totalPageResult);
	        this.hasMore = isHasMore();
	        this.offset = offset;
	        this.limit = limit;
	    }
	        
	    public boolean hasPrevious() {
	        return this.offset > 0 && this.totalElements > 0;
	    }
	    public int getTotalElements() {
	        return this.totalElements;
	    }
	    public int  getOffset() {
	        return this.offset;
	    }
	    public int getLimit() {
	        return this.limit;
	    }
	    public int getCurrentPage() {
			return this.currentPage;
		}

		public Long getTotalPage() {
			return this.totalPage;
		}

		public boolean isHasMore() {
			return this.totalRecords > this.offset + this.limit;
		}
		
		public List<T> getElements() {
	        return elements;
	    }
}
