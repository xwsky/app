package com.platform.component.service.ext.grid;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.platform.core.utils.TextUtil;

public class GridTransModel {
	
	Logger logger = Logger.getLogger(GridTransModel.class);

	private int limit;
	
	private String sortColumn;
	
	private int pageIndex;
	
	private int startIndex;
	
	private String direction;
	
	private boolean pager = true;	//默认为分页

	public GridTransModel(HttpServletRequest request) {
		
		String limit = null;
		String pageIndex = null;
		String startIndex = null;
		String sortColumn = null;
		String direction = null;
		
		//不分页
		Object obj = request.getAttribute("PAGER");
		if(obj != null){
			this.pager = (boolean) obj;
		}
		
		//获取导出属性
		/*Object INSEXPORT_LIMIT = request.getAttribute("INSEXPORT_LIMIT");
		Object INSEXPORT_PAGE = request.getAttribute("INSEXPORT_PAGE");
		Object INSEXPORT_START = request.getAttribute("INSEXPORT_START");
		
		if (INSEXPORT_LIMIT != null
				&& INSEXPORT_PAGE != null
				&& INSEXPORT_START != null) {
			limit = INSEXPORT_LIMIT + "";
			pageIndex = INSEXPORT_PAGE + "";
			startIndex = INSEXPORT_START + "";
		}else{
			limit = request.getParameter("limit");
			pageIndex = request.getParameter("page");
			startIndex = request.getParameter("start");
			sortColumn = request.getParameter("sort");
			direction = request.getParameter("dir");
		}*/
		
		limit = request.getParameter("limit");
		pageIndex = request.getParameter("page");
		startIndex = request.getParameter("start");
		sortColumn = request.getParameter("sort");
		direction = request.getParameter("dir");
		
		if(TextUtil.isNotEmpty(limit)){
			this.limit = Integer.parseInt(limit);
		}else{	
			//如果没取到，从attribute中取
			if(pager == true){
				try{
					this.limit = (Integer)request.getAttribute("limit");
				}catch(Exception ex){
					logger.error("If paging is required, it must contain 'limit'", ex);
				}				
			}			
		}
		if(TextUtil.isNotEmpty(pageIndex)) {
			try{
				this.pageIndex = Integer.parseInt(pageIndex);
			}catch(Exception ex){
				this.pageIndex = 1;
			}			
		}else {
			this.pageIndex = 1;
		}
		if (TextUtil.isNotEmpty(startIndex)) {
			this.startIndex = Integer.parseInt(startIndex);
		}else {
			this.startIndex = 0;
		}
		this.sortColumn = sortColumn;
		this.direction = direction;			
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public boolean getPager() {
		return pager;
	}

	public void setPager(boolean pager) {
		this.pager = pager;
	}

	@Override
	public String toString() {
		return "GridTransModel [limit=" + limit + ", sortColumn=" + sortColumn
				+ ", pageIndex=" + pageIndex + ", startIndex=" + startIndex
				+ ", direction=" + direction + "]";
	}
	
}
