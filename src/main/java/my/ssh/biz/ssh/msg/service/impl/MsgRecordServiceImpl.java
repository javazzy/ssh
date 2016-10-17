package my.ssh.biz.ssh.msg.service.impl;

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.service.impl.*;
import my.ssh.biz.ssh.msg.dao.MsgRecordDao;
import my.ssh.biz.ssh.msg.entity.MsgRecord;
import my.ssh.biz.ssh.msg.service.MsgRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  服务实现： 消息记录表
 */
@Service
public class MsgRecordServiceImpl extends BaseServiceImpl<MsgRecord> implements MsgRecordService{

    @Resource
    private MsgRecordDao msgRecordDao;

    @Override
    public BaseDao<MsgRecord> getDao() {
        return msgRecordDao;
    }
}
