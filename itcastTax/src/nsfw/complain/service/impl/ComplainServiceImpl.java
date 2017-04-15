package nsfw.complain.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import nsfw.complain.dao.ComplainDao;
import nsfw.complain.entity.Complain;
import nsfw.complain.service.ComplainService;
import core.service.impl.BaseServiceImpl;

@Service("ComplainServiceImpl")
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements ComplainService{
	
	private ComplainDao complainDao;

	@Resource
	public void setComplainDao(ComplainDao complainDao) {
		super.setBaseDao(complainDao);
		this.complainDao = complainDao;
	}
}
