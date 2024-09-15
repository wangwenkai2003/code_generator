package com.wwk.mapper;



import com.wwk.domain.GenTableColumn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 业务字段 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface GenTableColumnMapper
{
    /**
     * 根据表名称查询列信息
     * 
     * @param tableName 表名称
     * @return 列信息
     */
    @Select("select column_name, \n" +
            "       (case when (is_nullable = 'no' and column_key != 'PRI') then '1' else null end) as is_required, \n" +
            "       (case when column_key = 'PRI' then '1' else '0' end) as is_pk, \n" +
            "       ordinal_position as sort, \n" +
            "       column_comment, \n" +
            "       (case when extra = 'auto_increment' then '1' else '0' end) as is_increment, \n" +
            "       column_type\n" +
            "from information_schema.columns \n" +
            "where table_schema = (select database()) \n" +
            "  and table_name = #{tableName}\n" +
            "order by ordinal_position")
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName);

    /**
     * 查询业务字段列表
     * 
     * @param tableId 业务字段编号
     * @return 业务字段集合
     */
    public List<GenTableColumn> selectGenTableColumnListByTableId(Long tableId);

    /**
     * 新增业务字段
     * 
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    @Insert(" insert into gen_table_column(table_id, column_name, column_comment, column_type) values  (#{tableId}, #{columnName}, #{columnComment}, #{columnType})")
    public int insertGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 修改业务字段
     * 
     * @param genTableColumn 业务字段信息
     * @return 结果
     */
    public int updateGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 删除业务字段
     * 
     * @param genTableColumns 列数据
     * @return 结果
     */
    public int deleteGenTableColumns(List<GenTableColumn> genTableColumns);

    /**
     * 批量删除业务字段
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGenTableColumnByIds(Long[] ids);
}
