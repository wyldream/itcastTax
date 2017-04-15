package nsfw.info.entity;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

public class Info {
	//信息编号
	private String infoId;

	//信息分类
	private String type;
	//信息来源
	private String source;
	//标题
	private String title;
	//内容
	private String content;
	//备注
	private String memo;
	//发布人
	private String creator;
	//发布时间(精确到分钟)
	private Timestamp createTime;
	//状态
	private String state;
	
	public static String STATIC_INFO_PUBLIC="1";
	public static String STATIC_INFO_STOP="0";
	
	public static String INFO_TYPE_TZGG="tzgg";
	public static String INFO_TYPE_ZCSD="zcsd";
	public static String INFO_TYPE_NSZD="nszd";
	public static Map INFO_TYPE_MAP;
	
	static{
		INFO_TYPE_MAP = new HashedMap();
		INFO_TYPE_MAP.put(INFO_TYPE_TZGG, "通知公告");
		INFO_TYPE_MAP.put(INFO_TYPE_ZCSD, "政策速递");
		INFO_TYPE_MAP.put(INFO_TYPE_NSZD, "纳税指导");
	}
	
	public Info() {
	}
	
	public Info(String id, String type, String source, String title,
			String content, String memo, String creator, Timestamp createTime,
			String state) {
		this.infoId = id;
		this.type = type;
		this.source = source;
		this.title = title;
		this.content = content;
		this.memo = memo;
		this.creator = creator;
		this.createTime = createTime;
		this.state = state;
	}
	
	
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return infoId+"　"+title;
	}
	
}
