package my.ssh.biz.ssh.msg.dao.impl;

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import my.ssh.biz.common.dao.impl.BaseDaoImpl;
import my.ssh.biz.ssh.msg.dao.MsgFileDao;
import my.ssh.biz.ssh.msg.entity.MsgFile;

import java.util.Calendar;

/**
 *  持久层实现： 消息附件表
 */
@Repository
public class MsgFileDaoImpl extends BaseDaoImpl<MsgFile> implements MsgFileDao {

    public Class<MsgFile> getClassType() {
        return MsgFile.class;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(MsgFile entity, Page<MsgFile> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        if(null != entity) {
            /**
             * 附件编号
             */
            if (entity.getId() > 0) {
               dc.add(Restrictions.eq("id", entity.getId()));
            }
            /**
             * 附件编号
             */
            if (null != entity.getMsgRecord()) {
                dc.add(Restrictions.eq("msgRecord.id", entity.getMsgRecord().getId()));
            }
            /**
             * 消息记录编号
             */
            if (null != entity.getRecordId()) {
               dc.add(Restrictions.eq("recordId", entity.getRecordId()));
            }
            /**
             * 文件名称
             */
            if (StringUtils.isNotBlank(entity.getFileName())) {
                dc.add(Restrictions.like("fileName", entity.getFileName(), MatchMode.ANYWHERE));
            }
            /**
             * 保存路径
             */
            if (StringUtils.isNotBlank(entity.getPath())) {
                dc.add(Restrictions.like("path", entity.getPath(), MatchMode.ANYWHERE));
            }
            /**
             * 文件大小
             */
            if (null != entity.getFileSize()) {
               dc.add(Restrictions.eq("fileSize", entity.getFileSize()));
            }
        }
        return dc;
    }
}