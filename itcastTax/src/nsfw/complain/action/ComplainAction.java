package nsfw.complain.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import nsfw.complain.entity.Complain;
import nsfw.complain.entity.ComplainReply;
import nsfw.complain.service.ComplainService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.opensymphony.xwork2.ActionContext;

import core.Action.BaseAction;
import core.util.QueryHelper;

public class ComplainAction extends BaseAction{
	
	@Resource
	private ComplainService complainService;
	private Complain complain;
	private QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
	private String startTime;
	private String endTime;
	private ComplainReply reply;


	public String listUI(){
		try {
			ActionContext.getContext().getContextMap().put("complainStateMap", complain.COMPLAIN_STATE_MAP);
			if(complain != null){
				if(StringUtils.isNotBlank(startTime)){
					startTime = URLDecoder.decode(startTime,"utf-8");
					queryHelper.addCondition("c.compTime >= ?", DateUtils.parseDate(startTime, "yyyy-MM-dd HH:mm"));
				}
				if(StringUtils.isNotBlank(endTime)){
					startTime = URLDecoder.decode(endTime,"utf-8");
					queryHelper.addCondition("c.compTime <= ?", DateUtils.parseDate(endTime, "yyyy-MM-dd HH:mm"));
				}
				if(StringUtils.isNotBlank(complain.getCompTitle())){
					queryHelper.addCondition("c.compTitle like ?", "%"+complain.getCompTitle()+"%");
				}
				if(StringUtils.isNotBlank(complain.getState())){
					queryHelper.addCondition("c.state = ?", complain.getState());
				}
			}
			queryHelper.addOrderByProperty("c.state", QueryHelper.ORDER_BY_ASC);
			queryHelper.addOrderByProperty("c.compTime", QueryHelper.ORDER_BY_ASC);
			
			pageResult = complainService.getPageResult(queryHelper, getPageNo(), getPageSize());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listUI";
	}
	

	//跳转到受理页面
	public String dealUI(){
		//加载状态集合
		ActionContext.getContext().getContextMap().put("complainStateMap", Complain.COMPLAIN_STATE_MAP);
		if(complain != null){
			complain = complainService.findObjectById(complain.getCompId());
		}
		return "dealUI";
	}

	public String deal(){
		if(complain != null){
			Complain tem = complainService.findObjectById(complain.getCompId());
			//1、更新投诉的状态为 已受理
			if(!Complain.COMPLAIN_STATE_DONE.equals(tem.getState())){//更新状态为 已受理
				tem.setState(Complain.COMPLAIN_STATE_DONE);
			}
			//2、保存回复信息
			if(reply != null){
				reply.setComplain(tem);
				reply.setReplyTime(new Timestamp(new Date().getTime()));
				tem.getComplainReplies().add(reply);
			}
			complainService.update(tem);
		}
		return "list";
	}
	
	public Complain getComplain() {
		return complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public ComplainReply getReply() {
		return reply;
	}

	public void setReply(ComplainReply reply) {
		this.reply = reply;
	}
}
