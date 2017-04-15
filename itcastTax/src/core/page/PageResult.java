package core.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类，封装分页所需的参数和操作
 * @author LBJ
 *
 */
public class PageResult {
	//总记录数
	private long totalCount;
	//总页数
	private int totalPageCount;
	//当前页
	private int pageNo;
	//每页显示行数
	private int pageSize;
	//每页显示内容(存放搜索结果)
	private List items;
	
	
	
	public PageResult(long totalCount, int pageNo, int pageSize, List items) {
		this.totalCount = totalCount;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.items = items==null?new ArrayList():items;
		/*totalPageCount = (int) totalCount/pageSize;
		totalPageCount += totalCount % pageSize != 0?1:0;*/
		if(totalCount != 0){
			//计算总页数
			int tem = (int)totalCount/pageSize;
			this.totalPageCount = (totalCount%pageSize==0)?tem:(tem+1);
			this.pageNo = pageNo;
		} else {
			this.pageNo = 0;
		}
	}
	
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getTptalPageCount() {
		return totalPageCount;
	}
	public void setTptalPageCount(int tptalPageCount) {
		this.totalPageCount = tptalPageCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
}
