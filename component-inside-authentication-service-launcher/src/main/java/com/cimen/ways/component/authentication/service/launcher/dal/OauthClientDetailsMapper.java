package com.cimen.ways.component.authentication.service.launcher.dal;

import com.cimen.ways.component.authentication.service.launcher.model.OauthClientDetails;
import com.cimen.ways.component.authentication.service.launcher.model.OauthClientDetailsExample;
import java.util.List;

import com.cimen.ways.component.dal.domains.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface OauthClientDetailsMapper extends BaseMapper {
    /**
     *
     * @param example
     */
    int countByExample(OauthClientDetailsExample example);

    /**
     *
     * @param example
     */
    int deleteByExample(OauthClientDetailsExample example);

    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(OauthClientDetails record);

    /**
     *
     * @param record
     */
    int insertSelective(OauthClientDetails record);

    /**
     *
     * @param example
     */
    List<OauthClientDetails> selectByExample(OauthClientDetailsExample example);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    OauthClientDetails selectByPrimaryKey(Long id);

    /**
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") OauthClientDetails record, @Param("example") OauthClientDetailsExample example);

    /**
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") OauthClientDetails record, @Param("example") OauthClientDetailsExample example);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(OauthClientDetails record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(OauthClientDetails record);
}