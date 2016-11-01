package my.ssh.biz.ssh.msg.service.impl;

import my.ssh.biz.common.dao.BaseDao;
import my.ssh.biz.common.service.impl.*;
import my.ssh.biz.ssh.msg.dao.MsgFileDao;
import my.ssh.biz.ssh.msg.entity.MsgFile;
import my.ssh.biz.ssh.msg.service.MsgFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 *  服务实现： 消息附件表
 */
@Service
public class MsgFileServiceImpl extends BaseServiceImpl<MsgFile> implements MsgFileService{

    @Resource
    private MsgFileDao msgFileDao;

    @Override
    public BaseDao<MsgFile> getDao() {
        return msgFileDao;
    }
}
