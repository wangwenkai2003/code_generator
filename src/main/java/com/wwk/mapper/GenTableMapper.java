package com.wwk.mapper;


import com.wwk.domain.GenTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 业务 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface GenTableMapper
{
    /**
     * 查询业务列表
     * 
     * @param genTable 业务信息
     * @return 业务集合
     */
    public List<GenTable> selectGenTableList(GenTable genTable);

    /**
     * 查询据库列表
     *
     * @return 数据库表集合
     */
    @Select("select table_name, table_comment, create_time, update_time from information_schema.tables where table_schema = (select database()) AND table_name NOT LIKE 'qrtz_%' AND table_name NOT LIKE 'gen_%' order by create_time desc\t")
    public List<GenTable> selectDbTableList();

    /**
     * 查询据库列表
     * 
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    public List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 查询所有表信息
     * 
     * @return 表信息集合
     */
    @Select(" SELECT t.table_id, t.table_name, t.table_comment, t.sub_table_name, t.sub_table_fk_name, t.class_name, t.tpl_category, t.package_name, t.module_name, t.business_name, t.function_name, t.function_author, t.options, t.remark,\n" +
            "\t\t\t   c.column_id, c.column_name, c.column_comment, c.column_type, c.java_type, c.java_field, c.is_pk, c.is_increment, c.is_required, c.is_insert, c.is_edit, c.is_list, c.is_query, c.query_type, c.html_type, c.dict_type, c.sort\n" +
            "\t\tFROM gen_table t\n" +
            "\t\t\t LEFT JOIN gen_table_column c ON t.table_id = c.table_id\n" +
            "\t\torder by c.sort")
    public List<GenTable> selectGenTableAll();

    /**
     * 查询表ID业务信息
     * 
     * @param id 业务ID
     * @return 业务信息
     */
    public GenTable selectGenTableById(Long id);

    /**
     * 查询表名称业务信息
     * 
     * @param tableName 表名称
     * @return 业务信息
     */
    @Select("  SELECT t.table_id, t.table_name, t.table_comment, t.sub_table_name, t.sub_table_fk_name, t.class_name, t.tpl_category, t.package_name, t.module_name, t.business_name, t.function_name, t.function_author, t.gen_type, t.gen_path, t.options, t.remark,\n" +
            "\t\t\t   c.column_id, c.column_name, c.column_comment, c.column_type, c.java_type, c.java_field, c.is_pk, c.is_increment, c.is_required, c.is_insert, c.is_edit, c.is_list, c.is_query, c.query_type, c.html_type, c.dict_type, c.sort\n" +
            "\t\tFROM gen_table t\n" +
            "\t\t\t LEFT JOIN gen_table_column c ON t.table_id = c.table_id\n" +
            "\t\twhere t.table_name = #{tableName} order by c.sort")
    public GenTable selectGenTableByName(String tableName);

    /**
     * 新增业务
     * 
     * @param genTable 业务信息
     * @return 结果
     */
    @Insert("insert into gen_table(table_name, table_comment) values (#{tableName}, #{tableComment})")
    public int insertGenTable( GenTable genTable);

    /**
     * 修改业务
     * 
     * @param genTable 业务信息
     * @return 结果
     */
    public int updateGenTable(GenTable genTable);

    /**
     * 批量删除业务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGenTableByIds(Long[] ids);
}
