package my.ssh.biz.ssh.msg.dao.impl;

import my.ssh.biz.common.entity.Page;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.StringUtils;
import my.ssh.biz.common.dao.impl.BaseDaoImpl;
import my.ssh.biz.ssh.msg.dao.MsgRecordDao;
import my.ssh.biz.ssh.msg.entity.MsgRecord;

import java.util.Calendar;

/**
 *  持久层实现： 消息记录表
 */
@Repository
public class MsgRecordDaoImpl extends BaseDaoImpl<MsgRecord> implements MsgRecordDao {

    public Class<MsgRecord> getClassType() {
        return MsgRecord.class;
    }

    @Override
    public DetachedCriteria getDetachedCriteria(MsgRecord entity, Page<MsgRecord> page) {
        DetachedCriteria dc = initDetachedCriteriaByPage(page);

        if(null != entity) {
            /**
             * 消息编号
             */
            if (entity.getId() > 0) {
               dc.add(Restrictions.eq("id", entity.getId()));
            }
            /**
             * 发送用户
             */
            if (null != entity.getSysUserByFromUserId()) {
               dc.add(Restrictions.eq("fromUserId", entity.getSysUserByFromUserId().getId()));
            }
            /**
             * 接受用户
             */
            if (null != entity.getSysUserByToUserId()) {
               dc.add(Restrictions.eq("toUserId", entity.getSysUserByToUserId()));
            }
            /**
             * 发送时间
             */
            if (null != entity.getSendTime()) {
                dc.add(Restrictions.between("sendTime", entity.getSendTime(), Calendar.getInstance().getTime()));
            }
            /**
             * 消息类型（message:富文本消息，file:附件传送）
             */
            if (StringUtils.isNotBlank(entity.getType())) {
                dc.add(Restrictions.like("type", entity.getType(), MatchMode.ANYWHERE));
            }
            /**
             * 消息体
             */
            if (StringUtils.isNotBlank(entity.getContent())) {
                dc.add(Restrictions.like("content", entity.getContent(), MatchMode.ANYWHERE));
            }
            /**
             * 附件编号
             */
            if (null != entity.getMsgFile()) {
                dc.add(Restrictions.eq("msgFile.id", entity.getMsgFile().getId()));
            }
        }
        return dc;
    }
}