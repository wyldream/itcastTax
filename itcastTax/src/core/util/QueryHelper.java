package core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装模糊查询操作（组装好的查询语句和封装好的参数集合）
 * @author LBJ
 *
 */
public class QueryHelper {
	//from语句
	private String fromClause="";
	//where语句
	private String whereClause="";
	//orderby语句
	private String orderByClause="";
	
	//封装参数的list集合
	private List<Object> parameters;
	//排序顺序
	public static String ORDER_BY_DESC = "DESC";//降序
	public static String ORDER_BY_ASC = "ASC";//升序
	
	//构造from子句
	public QueryHelper(Class clazz,String i) {
		fromClause="from "+clazz.getSimpleName()+" "+i;
	}
	
	/**
	 * 构造where子句
	 * @param condition 查询条件语句；例如：i.title like ?
	 * @param params 查询条件语句中?对应的查询条件值；例如： %标题%
	 */
	public void addCondition(String condition, Object... params){
		if(whereClause.length() > 1){//有条件时
			whereClause += " and "+condition;
		}else{//第一个条件
			whereClause += " where "+condition;
		}
		
		if(parameters == null){
			parameters = new ArrayList<Object>();
		}
		
		for(Object param : params){
			parameters.add(param);
		}
	}
	
	/**
	 * 构造order by子语句
     * @param property 排序属性，如：i.createTime
	 * @param order 排序顺序，如：DESC 或者 ASC
	 */
	public void addOrderByProperty(String condition,String order){
		if(orderByClause.length() > 1){//非第一个排序属性
			orderByClause +=","+condition+" "+order;
		}else{//第一个排序属性
			orderByClause += " order by "+condition+" "+order;
		}
	}
	
	//
	//查询hql语句
	public String getQueryListHql(){
		return fromClause + whereClause + orderByClause;
	}
	
	//查询统计数的hql语句
	public String getQueryCountHql(){
		return "SELECT COUNT(*) " + fromClause + whereClause;
	}
	
	//查询hql语句中?对应的查询条件值集合
	public List<Object> getParameters(){
		return parameters;
	}

}
