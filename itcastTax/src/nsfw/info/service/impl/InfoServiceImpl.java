package nsfw.info.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import nsfw.info.dao.InfoDao;
import nsfw.info.entity.Info;
import nsfw.info.service.InfoService;

import org.springframework.stereotype.Service;

import core.service.impl.BaseServiceImpl;

@Service("InfoService")
public class InfoServiceImpl extends BaseServiceImpl<Info> implements InfoService{

	private InfoDao infoDao;

	//通过set方法注入infodao对象，同时又向父类注入了其所需的dao对象
	@Resource
	public void setInfoDao(InfoDao infoDao) {
		super.setBaseDao(infoDao);//
		this.infoDao = infoDao;
	}
	

}
