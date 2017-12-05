package com.jiuqi.autocode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jiuqi.dna.core.type.GUID;
import com.jiuqi.dna.ui.wt.widgets.MessageDialog;
import com.jiuqi.xlib.utils.StringUtil;

/**
 * @ClassName AutoCode
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liubin
 * @Date 2017年7月27日 下午4:53:51
 * @version 1.0.0
 */
public class AutoCode {

    public static Map<String , String> configs = new HashMap<String , String>();
    
    public static void main(String[] args) {
        try {
           autoCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void autoCode() throws Exception{
        Properties prop = new Properties();
        File currentFilePath = new File(AutoCode.class.getResource("").getPath());
        File configFile = new File(currentFilePath , "config.properties");
        if(!configFile.exists()){
            System.out.println("配置文件不存在");
        }
        InputStream in = new BufferedInputStream (new FileInputStream(configFile));
        InputStreamReader reader = new InputStreamReader(in , "UTF-8");
        prop.load(reader);     ///加载属性列表
        Iterator<String> it=prop.stringPropertyNames().iterator();
        while(it.hasNext()){
            String key=it.next();
            configs.put(key, prop.getProperty(key));
        }
        reader.close();
        in.close();
        
        String HOME_FILE_FOLDER = configs.get("outputdir");
        String projectName = configs.get("projectName");
        
        if(StringUtil.isEmpty(projectName)){
            MessageDialog.alert("请配置工程包名");
            return;
        }
        File projectFile = new File(HOME_FILE_FOLDER, projectName);
        if(projectFile.exists()){
            AutoCodeUtil.deleteAll(projectFile);
            projectFile.delete();
        }
        projectFile.mkdirs();
    
        //创建meta文件夹
        File meta = AutoCodeUtil.createFile(projectFile, "meta", true);
        File metaPackageFolder = AutoCodeUtil.createPackageFolder(meta, projectName);
        File metaTableFolder = AutoCodeUtil.createFile(metaPackageFolder, "table", true);
        File metaOrmFolder = AutoCodeUtil.createFile(metaPackageFolder, "orm", true);
        
        File meta_inf = AutoCodeUtil.createFile(projectFile, "META-INF", true);
        File manifest_mf = AutoCodeUtil.createFile(meta_inf, "MANIFEST.MF", false);
        AutoCodeUtil.writeRowText(manifest_mf, "Manifest-Version: 1.0");
        AutoCodeUtil.writeRowText(manifest_mf, "Bundle-ManifestVersion: 2");
        String bunderName = projectName.substring(projectName.lastIndexOf(".")+1);
        AutoCodeUtil.writeRowText(manifest_mf, "Bundle-Name: "+AutoCodeUtil.firstCharUpper(bunderName));
        AutoCodeUtil.writeRowText(manifest_mf, "Bundle-SymbolicName: "+projectName);
        AutoCodeUtil.writeRowText(manifest_mf, "Bundle-Version: 1.0.0");
        AutoCodeUtil.writeRowText(manifest_mf, "Bundle-Vendor: JIUQI");
        AutoCodeUtil.writeRowText(manifest_mf, "Bundle-RequiredExecutionEnvironment: JavaSE-1.7");
        AutoCodeUtil.writeRowText(manifest_mf, "Require-Bundle: com.jiuqi.dna.core;bundle-version=\"3.6.0\"\r\n");

        File src = AutoCodeUtil.createFile(projectFile, "src", true);
        File srcPackageFolder = AutoCodeUtil.createPackageFolder(src, projectName);
        File srcIntfFolder = AutoCodeUtil.createFile(srcPackageFolder, "intf", true);
        File srcKeyFolder = AutoCodeUtil.createFile(srcPackageFolder, "key", true);
        File srcImplFolder = AutoCodeUtil.createFile(srcPackageFolder, "impl", true);
        File srcTaskFolder = AutoCodeUtil.createFile(srcPackageFolder, "task", true);
        File srcServiceFolder = AutoCodeUtil.createFile(srcPackageFolder, "service", true);
        
        File projectConfFile = new File(currentFilePath , ".project");
        File destFile = new File(projectFile , ".project");
        AutoCodeUtil.copyFile(projectConfFile, destFile);
        String text = AutoCodeUtil.getText(destFile);
        String newText = text.replaceAll("projectname", projectName);
        if(newText.startsWith("\r\n")){
            newText = newText.substring(2);
        }
        AutoCodeUtil.writeIntoFile(destFile, newText , "GBK");
        File buildPropertiesFile = new File(currentFilePath , "build.properties");
        destFile = new File(projectFile , "build.properties");
        AutoCodeUtil.copyFile(buildPropertiesFile, destFile);
        
        File classpathFile = new File(currentFilePath , ".classpath");
        destFile = new File(projectFile , ".classpath");
        AutoCodeUtil.copyFile(classpathFile, destFile);
        
        String tableName = configs.get("tableName");
        DnaTable dnaTable = new DnaTable();
        dnaTable.setAuthor("");
        dnaTable.setCategory("基础数据主体");
        dnaTable.setId(GUID.randomID().toString());
        dnaTable.setIsneedrebuild(false);
        dnaTable.setName(tableName);
        dnaTable.setNameDb(tableName);
        dnaTable.setPackagename(projectName+".table");
        dnaTable.setStoragename(tableName);
        String tableTitle = configs.get("tableTitle");
        dnaTable.setTitle(tableTitle);
        dnaTable.setDescription(tableName);
        List<DnaDbtables> dnaDbtablesList = new ArrayList<DnaDbtables>();
        DnaDbtables dnaDbtables = new DnaDbtables();
        List<DnaDbtable> dnaDbtableList = new ArrayList<DnaDbtable>();
        DnaDbtable dnaDbtable = new DnaDbtable();
        dnaDbtable.setAuthor("");
        dnaDbtable.setDescription("");
        dnaDbtable.setName(tableName);
        dnaDbtable.setNameDb(tableName);
        dnaDbtable.setTitle("");
        dnaDbtableList.add(dnaDbtable);
        dnaDbtables.setDbtables(dnaDbtableList);
        dnaDbtablesList.add(dnaDbtables);
        dnaTable.setDbtables(dnaDbtablesList);
        List<DnaFileds> dnaFiledsList = new ArrayList<DnaFileds>();
        DnaFileds dnaFileds = new DnaFileds();
        List<DnaFiled> dnaFiledList = new ArrayList<DnaFiled>();
        String fieldsStr = configs.get("tableFields");
        JSONArray jsonArray = new JSONArray(fieldsStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = jsonArray.optJSONObject(i);
            DnaFiled dnaFiled = new DnaFiled();
            dnaFiled.setAuthor("");
            dnaFiled.setDbtable(tableName);
            dnaFiled.setDescription("");
            dnaFiled.setId(GUID.randomID().toString());
            dnaFiled.setKeepValid(false);
            String fieldName = jo.optString("name");
            dnaFiled.setName(fieldName);
            dnaFiled.setNameDb(fieldName);
            dnaFiled.setPrimaryKey(false);
            dnaFiled.setTitle(jo.optString("title"));
            dnaFiled.setType(jo.optString("type").toLowerCase());
            dnaFiledList.add(dnaFiled);
        }
        dnaFileds.setDnaFileds(dnaFiledList);
        dnaFiledsList.add(dnaFileds);
        dnaTable.setDnaFileds(dnaFiledsList);
        dnaTable.setDnaHierarchies(new ArrayList<DnaHierarchies>());
        dnaTable.setDnaIndexs(new ArrayList<DnaIndexs>());
        dnaTable.setDnaRelations(new ArrayList<DnaRelations>());
        AutoCodeUtil.writeIntoFile(new File(metaTableFolder , tableName+".td"), AutoCodeUtil.toXml(dnaTable),"UTF-8");
        
        //创建接口
        String tJavaName = AutoCodeUtil.firstCharUpper(tableName.substring(tableName.indexOf("_")+1).toLowerCase());
        String[] split = tJavaName.split("_");
        String tableJavaName = "";
        for (String string : split) {
            tableJavaName+=AutoCodeUtil.firstCharUpper(string);
        }
        String intfFileName = AutoCodeUtil.firstCharUpper(tableJavaName , false);
        String intfPackage = projectName+".intf";
        AutoCodeUtil.createIntf(srcIntfFolder , intfFileName , tableTitle+"外观", intfPackage , dnaFiledList);
        
        //创建实现类
        String implFileName = AutoCodeUtil.firstCharUpper(tableJavaName , false)+"Impl";
        AutoCodeUtil.createImpl(srcImplFolder , projectName , tableJavaName , tableTitle+"实现类", dnaFiledList);
        
        //创建task
        String taskPackage = projectName+".task";
        AutoCodeUtil.createTask(srcTaskFolder , tableJavaName , tableTitle+"任务", taskPackage , dnaFiledList);
        //创建查询key
        String keyFileName = AutoCodeUtil.firstCharUpper(tableJavaName , false)+"Key";
        String keyPackage = projectName+".key";
        AutoCodeUtil.createKey(srcKeyFolder , keyFileName , tableTitle+"查询键", keyPackage , dnaFiledList);
        
        //创建orm
        String ormName = tableJavaName+"Orm";
        AutoCodeUtil.createOrmFile(metaOrmFolder , projectName ,tableName , ormName , implFileName , dnaFiledList);
        
        AutoCodeUtil.createService(srcServiceFolder , projectName, tableJavaName , dnaFiledList);
        
        DnaXml dnaXml = new DnaXml();
        String space = configs.get("space");
        dnaXml.addServiceNode(new ServiceNode(space, projectName+".service."+tableJavaName+"Service"));
        dnaXml.addTableNode(new TableNode(space, projectName+".table.TB_"+tableName));
        dnaXml.addOrmNode(new OrmNode(space, projectName+".orm."+ormName));
        dnaXml.toXmlFile(projectFile.getAbsolutePath()+File.separator+"dna.xml");
        
    }
}
