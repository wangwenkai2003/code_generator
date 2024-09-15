package com.wwk.controller;

import com.wwk.domain.GenTable;
import com.wwk.service.IGenTableService;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.surefire.shade.org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/gen")
public class GenController {
    @Autowired
    IGenTableService genTableService;
    @GetMapping("/get")
    public String get() {
        return "ss";
    }

    /**
     * 查询数据库所有表的信息
     * @return
     */
    @GetMapping("/db/list")
    public List<GenTable> dataList()
    {
        List<GenTable> list = genTableService.selectDbTableList();
        return list;
    }

    /**
     * 查询数据库中代码生成表的信息
     * @return
     */
    @GetMapping("gentab/list")
    public List<GenTable> genTables( ){
        List<GenTable> genTables = genTableService.selectGenTableAll();
        return genTables;
    }

    /**
     * 插入代码生成表
     * @param
     * @return
     */
    @PostMapping("gentab/insert")
    public String importGentable(@RequestBody List<GenTable> genTables){
        log.info(genTables.toString());
//        GenTable genTable = genTableService.selectDbTableByName(tableName);
//        for (GenTable genTable : genTables) {
//            genTableService.insertGenTable(genTable);
//        }
//        int i = genTableService.insertGenTable(genTable);
//        boolean save = genTableService.save(genTable);
        genTableService.importGenTable(genTables);
        return false ?"导入成功":"导入失败";
    }

    /**
     * 下载代码
     * @param response
     * @param tableName
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response, String tableName) throws IOException
    {
        byte[] data = genTableService.downloadCode(tableName);
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException
    {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
