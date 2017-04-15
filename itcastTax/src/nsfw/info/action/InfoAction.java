package nsfw.info.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import nsfw.info.entity.Info;
import nsfw.info.service.InfoService;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import core.Action.BaseAction;
import core.page.PageResult;
import core.util.QueryHelper;

public class InfoAction extends BaseAction {
	@Resource
	private InfoService infoService;
	//获取不到值一般是get和set方法的问题
	private List<Info> infoList;
	private Info info;
	//专门保存查询条件值，避免覆盖
	private String strTitle;
	//抽到baseAction 
	/*private PageResult pageResult;
	private int pageNo;
	private int pageSize;*/
	
	//列表页面
	public String listUI() throws Exception{
		//加载类型集合
		ActionContext.getContext().getContextMap().put("typeMap", info.INFO_TYPE_MAP);
		//创建queryHelper对象，设置queryHelper参数（组装出一个查询语句）
		QueryHelper qh = new QueryHelper(Info.class, "i");
		try {
			if(info != null){
				if(StringUtils.isNotBlank(info.getTitle())){
					info.setTitle(URLDecoder.decode(info.getTitle(), "utf-8"));//解决回显时乱码问题
					qh.addCondition("i.title like ?", "%" + info.getTitle() + "%");
				}
			}
			//根据创建时间降序排序
			qh.addOrderByProperty("i.createTime", QueryHelper.ORDER_BY_DESC);
			//传入queryHelper参数查询
			//infoList = infoService.findObjects(qh);
			//infoList = infoService.findObjects();
			//查询分页数据，填充到pageResult中（当前页和页大小为手动设置）
			pageResult = infoService.getPageResult(qh, getPageNo(), getPageSize());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "listUI";
	}
	//跳转到新增页面
	public String addUI(){
		//加载权限集合
		ActionContext.getContext().getContextMap().put("typeMap", info.INFO_TYPE_MAP);
		//设置时间
		info = new Info();
		info.setCreateTime(new Timestamp(new Date().getTime()));
		return "addUI";
	}
	//保存新增
	public String add(){
		try {
			if(info != null){
				infoService.save(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//跳转到编辑页面
	public String editUI(){
		//加载权限集合
		ActionContext.getContext().getContextMap().put("typeMap", info.INFO_TYPE_MAP);
		if (info != null && info.getInfoId() != null) {
			//保存查询条件值
			strTitle = info.getTitle();
			//如果用info.title保存查询条件值，这里会覆盖掉
			info = infoService.findObjectById(info.getInfoId());
		}
		return "editUI";
	}
	//保存编辑
	public String edit(){
		try {
			if(info != null){
				infoService.update(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	//删除
	public String delete(){
		if(info != null && info.getInfoId() != null){
			infoService.delete(info.getInfoId());
		}
		return "list";
	}
	//批量删除
	public String deleteSelected(){
		if(selectedRow != null){
			for(String id: selectedRow){
				infoService.delete(id);
			}
		}
		return "list";
	}
	//异步发布信息
	public void publicInfo(){
		try {
			if(info != null){
				//1、更新信息状态
				Info tem = infoService.findObjectById(info.getInfoId());
				tem.setState(info.getState());
				infoService.update(tem);
				
				//2、输出更新结果
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("更新状态成功".getBytes("utf-8"));
				outputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Info> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	
}
