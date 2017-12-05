package com.jiuqi.autocode;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.jiuqi.xlib.utils.StringUtil;
import com.thoughtworks.xstream.XStream;


/**
 * @ClassName AutoCodeUtil
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liubin
 * @Date 2017年7月27日 下午4:56:00
 * @version 1.0.0
 */
public class AutoCodeUtil {

    public static File createFile(File parent , String fileName , boolean isFolder){
        if(!parent.exists()){
            parent.mkdirs();
        }
        File createFile = new File(parent , fileName);
        if(createFile.exists()){
            createFile.delete();
        }
        if(isFolder){
            createFile.mkdirs();
        }else{
            try {
                createFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return createFile;
    }
    
    /**
     * @Description (写入一行文本)
     * @param file
     * @param rowText
     */
    public static void writeRowText(File file , String rowText){
        if(!file.exists() || file.isDirectory()){
           return;
        }
        try{
            FileWriter fw = new FileWriter(file , true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(rowText);
            writer.newLine();//换行
            writer.flush();
            writer.close();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static File createPackageFolder(File parent , String packageName){
        File myParentFile = parent;
        String[] folders = packageName.split("\\.");
        for (String folder : folders) {
            myParentFile = createFile(myParentFile, folder, true);
        }
        return myParentFile;
    }
    
    public static void writeIntoFile(File file ,  String text , String charset){
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, charset);
            BufferedWriter bufferWritter = new BufferedWriter(outputStreamWriter);
            bufferWritter.write(text);
            bufferWritter.close();
            outputStreamWriter.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean copyFile(File srcFile, File destFile) {
        if(destFile.exists()){
            destFile.delete();
        }
        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;
        try {
            destFile.createNewFile();
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String getText(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    
    public static String toXml(DnaTable object){
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.processAnnotations(DnaTable.class);
        String top = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n";  
        return top + xStream.toXML(object);
    }
    /**
     * @Description (首字母大写)
     * @param string
     * @return
     */
    public static String firstCharUpper(String string){
    	return  firstCharUpper(string, true);
    }
    /**
     * @Description (首字母大写)
     * @param string
     * @return
     */
    public static String firstCharUpper(String string , boolean toLowerCase){
    	if(toLowerCase){
    		return  string.substring(0, 1).toUpperCase()+string.substring(1).toLowerCase();
    	}else{
    		return  string.substring(0, 1).toUpperCase()+string.substring(1);
    	}
    }
    
    /**
     * @Description (首字母大写)
     * @param string
     * @return
     */
    public static String firstCharLower(String string){
    	return firstCharLower(string , true);
    }
    
    /**
     * @Description (首字母大写)
     * @param string
     * @return
     */
    public static String firstCharLower(String string , boolean toLowerCase){
    	if(toLowerCase){
    		return  string.substring(0, 1).toLowerCase()+string.substring(1).toLowerCase();
    	}else{
    		return  string.substring(0, 1).toLowerCase()+string.substring(1);
    	}
    }

    /**
     * @Description (创建get方法)
     * @param fieldName
     * @param noteTitle
     * @param fieldType
     * @return
     */
    private static String createGet(String fieldName , String noteTitle , String fieldType) {
        StringBuffer sb = new StringBuffer();
        sb.append(createNote("获取"+noteTitle, null , 1));
        sb.append("\tpublic "+getFieldTypeStr(fieldType)+" get"+firstCharUpper(fieldName)+"(){\r\n");
            sb.append("\t\treturn this."+fieldName+";\r\n");
        sb.append("\t}\r\n");
        return sb.toString();
    }
    
    /**
     * @Description (创建get方法)
     * @param fieldName
     * @param noteTitle
     * @param fieldType
     * @return
     */
    private static String createIntfGet(String fieldName , String noteTitle , String fieldType) {
        StringBuffer sb = new StringBuffer();
        sb.append(createNote("获取"+noteTitle, null , 1));
        sb.append("\tpublic "+getFieldTypeStr(fieldType)+" get"+firstCharUpper(fieldName)+"();\r\n");
        return sb.toString();
    }
    
    /**
     * @Description (创建set方法)
     * @param fieldName
     * @param noteTitle
     * @param fieldType
     * @return
     */
    private static String createSet(String fieldName , String noteTitle , String fieldType) {
        StringBuffer sb = new StringBuffer();
        sb.append(createNote("设置"+noteTitle, null , 1));
        sb.append("\tpublic void set"+firstCharUpper(fieldName)+"("+getFieldTypeStr(fieldType)+" "+fieldName+"){\r\n");
            sb.append("\t\tthis."+fieldName+" = "+fieldName+";\r\n");
        sb.append("\t}\r\n");
        return sb.toString();
    }
    
    /**
     * @Description (创建get、set方法)
     * @param fieldName
     * @param noteTitle
     * @param fieldType
     * @return
     */
    private static String createGetSet(String fieldName , String noteTitle , String fieldType) {
        StringBuffer sb = new StringBuffer();
        sb.append(createGet(fieldName, noteTitle, fieldType));
        sb.append(createSet(fieldName, noteTitle, fieldType));
        return sb.toString();
    }
    
    /**
     * @Description (根据字段类型返回java类中的字段类型)
     * @param fieldType
     * @return
     */
    public static String getFieldTypeStr(String fieldType){
        if(fieldType.startsWith("nvarchar") || fieldType.startsWith("ntext")){
            return "String";
        }
        if(fieldType.toUpperCase().equals("GUID")){
            return "GUID";
        }
        if(fieldType.startsWith("numeric")){
            return "double";
        }
        return fieldType;
    }
    
    /**
     * 
     * @Description (TODO这里用一句话描述这个方法的作用)
     * @param desc
     * @param _t缩进tab个数
     * @author liubin
     * @return
     */
    public static String createNote(String desc , String author , int _t){
        String tabs = "";
        for (int i = 0; i < _t; i++) {
            tabs+="\t";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(tabs+"/**\r\n");
        sb.append(tabs+" *@Description ").append(desc).append("\r\n");
        if(!StringUtil.isEmpty(author)){
            sb.append(tabs+" *@author ").append(author).append("\r\n");
        }
        sb.append(tabs+" */\r\n");
        return sb.toString();
    }

    /**
     * @Description (生成Task类)
     * @param srcTaskFolder
     * @param taskFileName
     * @param string
     * @param taskPackage
     * @param dnaFiledList
     */
    public static void createTask(File srcTaskFolder, String tableJavaName, String desc, String taskPackage, List<DnaFiled> dnaFiledList) {
    	String taskFileName = AutoCodeUtil.firstCharUpper(tableJavaName , false)+"Task";
        File implFile = AutoCodeUtil.createFile(srcTaskFolder, taskFileName+".java", false);
        StringBuffer sb = new StringBuffer();
        sb.append("package "+taskPackage+";\r\n");
        sb.append("import com.jiuqi.dna.core.type.GUID;\r\n");
        sb.append("import com.jiuqi.dna.core.invoke.Task;\r\n");
        sb.append(AutoCodeUtil.createNote(desc, "liubin" , 0));
        sb.append("public class "+taskFileName+" extends Task<"+taskFileName+".Method>{\r\n");
        sb.append("\tprivate GUID recid;\r\n");
        sb.append("\tprivate long recver;\r\n");
        for (DnaFiled dnaFiled : dnaFiledList) {
            String fieldName = dnaFiled.getName().toLowerCase();
            sb.append("\tprivate "+getFieldTypeStr(dnaFiled.getType())+" "+fieldName+";\r\n");
        }
        sb.append(createGetSet("recid" , "主键标识" , "GUID"));
        sb.append(createGetSet("recver" , "版本号" , "long"));
        for (DnaFiled dnaFiled : dnaFiledList) {
            sb.append(createGetSet(dnaFiled.getName().toLowerCase() , dnaFiled.getTitle() , dnaFiled.getType()));
        }
        sb.append("\tpublic enum Method{\r\n");
            sb.append("\t\tADD,MODIFY,DELETE;\r\n");
        sb.append("\t}\r\n");
        sb.append(createAssignValueMethod(tableJavaName , dnaFiledList));
        sb.append("}");
        writeIntoFile(implFile, sb.toString() , "GBK");
    }
    
    public static void createImpl(File srcImplFolder, String projectName, String tableJavaName , String desc , List<DnaFiled> dnaFiledList) {
        File implFile = AutoCodeUtil.createFile(srcImplFolder, tableJavaName+"Impl.java", false);
        StringBuffer sb = new StringBuffer();
        sb.append("package "+projectName+".impl;\r\n");
        sb.append("import com.jiuqi.dna.core.type.GUID;\r\n");
        sb.append("import com.jiuqi.dna.core.def.table.TableDefine;\r\n");
        sb.append("import com.jiuqi.dna.core.Context;\r\n");
        sb.append("import com.jiuqi.emgr.util.dnasql.RowObject;\r\n");
        sb.append("import "+projectName+".intf.F"+tableJavaName+";\r\n");
        String tableDefineName = AutoCode.configs.get("tableName");
        sb.append("import "+projectName+".table.TB_"+tableDefineName+";\r\n");
        sb.append(AutoCodeUtil.createNote(desc, "liubin" , 0));
        sb.append("public class "+tableJavaName+"Impl implements F"+tableJavaName+"{\r\n");
        sb.append("\tprivate GUID recid;\r\n");
        sb.append("\tprivate long recver;\r\n");
        for (DnaFiled dnaFiled : dnaFiledList) {
            String fieldName = dnaFiled.getName().toLowerCase();
            sb.append("\tprivate "+getFieldTypeStr(dnaFiled.getType())+" "+fieldName+";\r\n");
        }
        sb.append(createGetSet("recid" , "主键标识" , "GUID"));
        sb.append(createGetSet("recver" , "版本号" , "long"));
        for (DnaFiled dnaFiled : dnaFiledList) {
            sb.append(createGetSet(dnaFiled.getName().toLowerCase() , dnaFiled.getTitle() , dnaFiled.getType()));
        }
        sb.append(createLoadMethod(tableDefineName , dnaFiledList));
        sb.append(createConver2ImplMethod(tableDefineName , dnaFiledList));
        sb.append(createAssignValueMethod(tableJavaName , dnaFiledList));
        sb.append("}");
        writeIntoFile(implFile, sb.toString() , "GBK");
    }

    private static String createConver2ImplMethod(String tableDefineName, List<DnaFiled> dnaFiledList) {
        StringBuffer sb = new StringBuffer();
        sb.append("\t").append("public RowObject convert2Impl(Context context){").append("\r\n");
        sb.append("\t\t").append("TableDefine table = context.find(TableDefine.class , TB_"+tableDefineName+".TABLE_NAME);\r\n");
        sb.append("\t\t").append("RowObject row = new RowObject(table);\r\n");
        sb.append("\t\t").append("row.setFieldValue(\"RECID\", this.getRecid());\r\n");
        sb.append("\t\t").append("row.setFieldValue(\"RECVER\", this.getRecver());\r\n");
        for (DnaFiled dnaFiled : dnaFiledList) {
            String fieldName = dnaFiled.getName().toLowerCase();
            sb.append("\t\trow.setFieldValue(TB_"+tableDefineName+".FN_"+dnaFiled.getName()+" , this.get"+firstCharUpper(fieldName)+"());\r\n");
        }
        sb.append("\t\treturn row;\r\n");
        sb.append("\t}").append("\r\n");
        return sb.toString();
    }
    
    private static String createAssignValueMethod(String intfName, List<DnaFiled> dnaFiledList) {
    	StringBuffer sb = new StringBuffer();
		sb.append("\t").append("public void assignValue("+intfName+"Task task){").append("\r\n");
    	sb.append("\t\t").append("this.setRecid(task.getRecid());\r\n");
    	sb.append("\t\t").append("this.setRecver(task.getRecver());\r\n");
    	for (DnaFiled dnaFiled : dnaFiledList) {
    		sb.append("\t\t").append("this.set"+firstCharUpper(dnaFiled.getName())+"(task.get"+firstCharUpper(dnaFiled.getName())+"());\r\n");
    	}
    	sb.append("\t}").append("\r\n");
    	return sb.toString();
    }
    
    private static String createLoadMethod(String tableDefineName, List<DnaFiled> dnaFiledList) {
        StringBuffer sb = new StringBuffer();
        sb.append("\t").append("public void load(RowObject row){").append("\r\n");
        sb.append("\t\t").append("this.setRecid((GUID) row.getFieldValue(\"RECID\"));\r\n");
        sb.append("\t\t").append("this.setRecver((Long) row.getFieldValue(\"RECVER\"));\r\n");
        for (DnaFiled dnaFiled : dnaFiledList) {
            String fieldValue = "row.getFieldValue(TB_"+tableDefineName+".FN_"+dnaFiled.getName()+")";
            sb.append("\t\tif("+fieldValue+" != null){\r\n");
            String fieldName = dnaFiled.getName().toLowerCase();
            sb.append("\t\t\t").append("this.set" + firstCharUpper(fieldName) + "((" + getFieldTypeStr(dnaFiled.getType()) + ") " + fieldValue + ");\r\n");
            sb.append("\t\t}\r\n");
        }
        sb.append("\t}").append("\r\n");
        return sb.toString();
    }

    /**
     * @Description (创建接口类)
     * @param srcIntfFolder
     * @param intfFileName
     * @param string
     * @param intfPackage
     * @param dnaFiledList
     */
    public static void createIntf(File srcIntfFolder, String intfFileName, String desc, String intfPackage,List<DnaFiled> dnaFiledList) {
        File implFile = AutoCodeUtil.createFile(srcIntfFolder, "F"+intfFileName+".java", false);
        StringBuffer sb = new StringBuffer();
        sb.append("package "+intfPackage+";\r\n\r\n");
        sb.append("import com.jiuqi.dna.core.type.GUID;\r\n\r\n");
        sb.append(AutoCodeUtil.createNote(desc, "liubin" , 0));
        sb.append("public interface F"+intfFileName+"{\r\n");
        sb.append(createIntfGet("recid" , "主键标识" , "GUID"));
        sb.append(createIntfGet("recver" , "版本号" , "long"));
        for (DnaFiled dnaFiled : dnaFiledList) {
            sb.append(createIntfGet(dnaFiled.getName().toLowerCase() , dnaFiled.getTitle() , dnaFiled.getType()));
        }
        sb.append("}");
        writeIntoFile(implFile, sb.toString() , "GBK");
    }

    /**
     * @param metaOrmFolder 
     * @Description (创建Orm脚本)
     * @param projectName
     * @param tableJavaName
     * @param implFileName
     * @param dnaFiledList 
     */
    public static void createOrmFile(File metaOrmFolder, String projectName,String tableName, String ormName, String implFileName, List<DnaFiled> dnaFiledList) {
        File implFile = AutoCodeUtil.createFile(metaOrmFolder, ormName+".orm", false);
        StringBuffer sb = new StringBuffer();
        sb.append("define orm "+ormName+"()\r\n");
        sb.append("\tmapping "+projectName+".impl."+implFileName+"\r\n");
        sb.append("begin\r\n");
        sb.append("\tselect\r\n");
        sb.append("\t\t").append("t.\"").append("RECID").append("\" as \"recid\",\r\n");
        sb.append("\t\t").append("t.\"").append("RECVER").append("\" as \"recver\",\r\n");
        for (int j = 0; j < dnaFiledList.size(); j++) {
            DnaFiled dnaFiled = dnaFiledList.get(j);
            sb.append("\t\t").append("t.\"").append(dnaFiled.getName()).append("\" as \"").append(dnaFiled.getName().toLowerCase());
            if(j == dnaFiledList.size() -1){
                sb.append("\"\r\n");
            }else{
                sb.append("\",\r\n");
            }
        }
        sb.append("\tfrom ").append(tableName).append(" as t\r\n");
        sb.append("end\r\n");
        writeIntoFile(implFile, sb.toString() , "GBK");
    }

    /**
     * @Description (生成服务类)
     * @param srcServiceFolder
     * @param tableJavaName
     * @param dnaFiledList 
     */
    public static void createService(File srcServiceFolder, String packageName , String tableJavaName, List<DnaFiled> dnaFiledList) {
        File serviceFile = AutoCodeUtil.createFile(srcServiceFolder, tableJavaName+"Service.java", false);
        String intfName = "F"+tableJavaName;
        String ormName = tableJavaName+"Orm";
        String keyName = tableJavaName+"Key";
        String implName = tableJavaName+"Impl";
        String taskName = tableJavaName+"Task";
        StringBuffer sb = new StringBuffer();
        sb.append("package "+packageName+".service"+";\r\n\r\n");
        sb.append("import java.util.List;").append("\r\n");
        sb.append("import com.jiuqi.dna.core.Context;").append("\r\n");
        sb.append("import com.jiuqi.dna.core.type.GUID;").append("\r\n");
        sb.append("import com.jiuqi.dna.core.da.ORMAccessor;").append("\r\n");
        sb.append("import com.jiuqi.dna.core.service.Publish;").append("\r\n");
        sb.append("import com.jiuqi.dna.core.service.Service;").append("\r\n");
        sb.append("import com.jiuqi.emgr.util.dnasql.query.SelectColumn;").append("\r\n");
        sb.append("import com.jiuqi.emgr.util.dnasql.query.QueryDefine;").append("\r\n");
        sb.append("import com.jiuqi.emgr.util.dnasql.query.QueryCondition;").append("\r\n");
        sb.append("import com.jiuqi.emgr.util.dnasql.condition.Operator;").append("\r\n");
        sb.append("import com.jiuqi.emgr.util.dnasql.condition.QueryParameter;").append("\r\n");
        sb.append("import com.jiuqi.emgr.util.dnasql.DnaSqlQuerier;").append("\r\n");
        sb.append("import com.jiuqi.emgr.util.dnasql.RowObject;").append("\r\n");
        sb.append("import ").append(packageName).append(".key." + keyName).append(";\r\n");
        sb.append("import ").append(packageName).append(".intf." + intfName).append(";\r\n");
        sb.append("import ").append(packageName).append(".impl." + implName).append(";\r\n");
        sb.append("import ").append(packageName).append(".orm."+ormName).append(";\r\n");
        sb.append("import ").append(packageName).append(".table.TB_"+AutoCode.configs.get("tableName")).append(";\r\n");
        sb.append("import ").append(packageName).append(".task."+taskName).append(";\r\n");
        String serviceClassName = tableJavaName+"Service";
        String chinaeseTableName = AutoCode.configs.get("tableTitle");
        sb.append(createNote(chinaeseTableName+"服务", "liubin", 0));
        sb.append("public class " + serviceClassName + " extends Service{\r\n");
            String ormConstant = "orm";
            sb.append("\tprivate final ").append(ormName).append(" ").append(ormConstant).append(";").append("\r\n");
            sb.append("\tpublic ").append(serviceClassName).append("(").append(ormName+" "+ormConstant).append("){").append("\r\n");
            sb.append("\t\tsuper(\"").append(chinaeseTableName).append("服务\"").append(");").append("\r\n");
            sb.append("\t\tthis."+ormConstant+" = " + ormConstant).append(";\r\n");
            sb.append("\t}").append("\r\n");
            sb.append(createTask(dnaFiledList, implName, taskName , 0));
            sb.append(createTask(dnaFiledList, implName, taskName , 1));
            sb.append(createTask(dnaFiledList, implName, taskName , 2));
            sb.append(createFindByRecid(dnaFiledList , tableJavaName ,intfName, implName));
            sb.append(createFindAll(dnaFiledList , tableJavaName , intfName, implName));
            sb.append(createFindByKey(dnaFiledList , tableJavaName , intfName));
        sb.append("}");
        
        AutoCodeUtil.writeIntoFile(serviceFile, sb.toString(), "GBK");
    }

    private static String createFindByKey(List<DnaFiled> dnaFiledList, String tableJavaName, String intfName) {
        StringBuffer sb = new StringBuffer();
        sb.append(createNote("根据key查询", "liubin", 1));
        sb.append("\t@Publish\r\n");
        sb.append("\tprotected class FindByKey extends OneKeyResultListProvider<F"+tableJavaName+", "+tableJavaName+"Key>{\r\n");
            sb.append("\r\n");
            sb.append("\t\t@Override\r\n");
            sb.append("\t\tprotected void provide(Context context,"+tableJavaName+"Key key , List<"+intfName+"> result) throws Throwable {\r\n");
                String tableDefineName = AutoCode.configs.get("tableName");
                sb.append("\t\t\t").append("String tableName = TB_"+tableDefineName+".TABLE_NAME;\r\n");
                sb.append("\t\t\t").append("List<SelectColumn> selectColumns = DnaSqlQuerier.generateSelectColumns(context, tableName, false);\r\n");
                sb.append("\t\t\t").append("QueryDefine queryDefine = new QueryDefine(tableName, selectColumns);\r\n");
                sb.append("\t\t\t").append("QueryCondition queryCondition = new QueryCondition();\r\n");
                sb.append("\t\t\t").append("if(key.recid != null){\r\n");
                    sb.append("\t\t\t\t").append("queryCondition.addQueryParameter(new QueryParameter(\"RECID\", Operator.EQ, key.recid));\r\n");
                sb.append("\t\t\t}\r\n");
                sb.append("\t\t\t").append("if(key.recver != null){\r\n");
                    sb.append("\t\t\t\t").append("queryCondition.addQueryParameter(new QueryParameter(\"RECVER\", Operator.EQ, key.recver));\r\n");
                sb.append("\t\t\t}\r\n");
                for (DnaFiled dnaFiled : dnaFiledList) {
                    sb.append("\t\t\t").append("if(key."+dnaFiled.getName().toLowerCase()+" != null){\r\n");
                        sb.append("\t\t\t\t").append("queryCondition.addQueryParameter(new QueryParameter(TB_"+tableDefineName+".FN_"+dnaFiled.getName()+", Operator.EQ, key."+dnaFiled.getName().toLowerCase()+"));\r\n");
                    sb.append("\t\t\t}\r\n");
                }
                sb.append("\t\t\t").append("queryDefine.setCondition(queryCondition);\r\n");
                sb.append("\t\t\t").append("List<RowObject> rows = DnaSqlQuerier.querySingleTableRows(context, queryDefine, key.offset, key.limit);\r\n");
                sb.append("\t\t\t").append("for (RowObject row : rows) {\r\n");
                sb.append("\t\t\t\t").append(tableJavaName+"Impl impl = new "+tableJavaName+"Impl();\r\n");
                sb.append("\t\t\t\t").append("impl.load(row);\r\n");
                sb.append("\t\t\t\t").append("result.add(impl);\r\n");
                sb.append("\t\t\t}\r\n");
            sb.append("\t\t}\r\n");
        sb.append("\t}\r\n");
        return sb.toString();
    }

    private static String createFindByRecid(List<DnaFiled> dnaFiledList,String tableJavaName, String intfName, String implName) {
        StringBuffer sb = new StringBuffer();
        sb.append(createNote("根据RECID查找", "liubin", 1));
        sb.append("\t@Publish\r\n");
        sb.append("\tprotected class FindByRecid extends OneKeyResultProvider<F"+tableJavaName+", GUID>{\r\n");
            sb.append("\r\n");
            sb.append("\t\t@Override\r\n");
            sb.append("\t\tprotected "+intfName+" provide(Context context, GUID recid) throws Throwable {\r\n");
                sb.append("\t\t\tORMAccessor<"+implName+">").append(" accessor = context.newORMAccessor(orm);\r\n");
                sb.append("\t\t\t"+implName+" impl = accessor.findByRECID(recid);\r\n");
                sb.append("\t\t\taccessor.unuse();\r\n");
                sb.append("\t\t\treturn impl;\r\n");
            sb.append("\t\t}\r\n");
        sb.append("\t}\r\n");
        return sb.toString();
    }
    
    private static String createFindAll(List<DnaFiled> dnaFiledList,String tableJavaName,String intfName, String implName) {
        StringBuffer sb = new StringBuffer();
        sb.append(createNote("查询所有记录", "liubin", 1));
        sb.append("\t@Publish\r\n");
        sb.append("\tprotected class GetAll extends ResultListProvider<F"+tableJavaName+">{\r\n");
        sb.append("\r\n");
        sb.append("\t\t@Override\r\n");
        sb.append("\t\tprotected void provide(Context context, List<"+intfName+"> result) throws Throwable {\r\n");
        sb.append("\t\t\tORMAccessor<"+implName+">").append(" accessor = context.newORMAccessor(orm);\r\n");
        sb.append("\t\t\tList<"+implName+"> impls = accessor.fetch();\r\n");
        sb.append("\t\t\tresult.addAll(impls);\r\n");
        sb.append("\t\t\taccessor.unuse();\r\n");
        sb.append("\t\t}\r\n");
        sb.append("\t}\r\n");
        return sb.toString();
    }

    private static String createTask(List<DnaFiled> dnaFiledList, String implName, String taskName , int method) {
        StringBuffer sb  = new StringBuffer();
        String methodName = null;
        if(method == 0){
            methodName = "ADD";
            sb.append(createNote("新增", "liubin", 1));
        }else if(method == 1){
            methodName = "MODIFY";
            sb.append(createNote("修改", "liubin", 1));
        }else{
            methodName = "DELETE";
            sb.append(createNote("删除", "liubin", 1));
        }
        sb.append("\t@Publish\r\n");
        sb.append("\t").append("protected class "+methodName+" extends TaskMethodHandler<").append(taskName).append(", ").append(taskName+".Method>").append(" {\r\n");
            sb.append("\t\t").append("protected "+methodName+"(){\r\n");
                sb.append("\t\t\tsuper("+taskName+".Method."+methodName+");\r\n");
            sb.append("\t\t").append("}\r\n");
            sb.append("\r\n");
            sb.append(createTaskImplement(dnaFiledList, implName, taskName , method));
        sb.append("\t").append("}\r\n");
        return sb.toString();
    }

    private static String createTaskImplement(List<DnaFiled> dnaFiledList, String implName, String taskName , int method) {
        StringBuffer sb  = new StringBuffer();
        sb.append("\t\t@Override\r\n");
        sb.append("\t\tprotected void handle(Context context, ").append(taskName+" task").append(") throws Throwable {\r\n");
            if(method != 2){
               sb.append(generateImpl(dnaFiledList, implName, method));
            }
            sb.append("\t\t\tORMAccessor<"+implName+">").append(" accessor = context.newORMAccessor(orm);\r\n");
            if(method == 0){
                sb.append("\t\t\taccessor.insert(impl);\r\n");
            }else if(method == 1){
                sb.append("\t\t\taccessor.update(impl);\r\n");
            }else{
                sb.append("\t\t\taccessor.delete(task.getRecid());\r\n");
            }
            sb.append("\t\t\taccessor.unuse();\r\n");
        sb.append("\t\t").append("}\r\n");
        return sb.toString();
    }

    private static String generateImpl(List<DnaFiled> dnaFiledList, String implName , int method) {
        StringBuffer sb  = new StringBuffer();
        sb.append("\t\t\t").append(implName+" impl = new "+implName+"();\r\n");
        sb.append("\t\t\t").append("impl.assignValue(task);\r\n");
        return sb.toString();
    }
    
    // 递归删除指定路径下的所有文件
    public static void deleteAll(File file) {
        if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                deleteAll(f);// 递归删除每一个文件
                f.delete();// 删除该文件夹
            }
        }
    }

    public static void createKey(File srcKeyFolder, String keyFileName, String desc, String keyPackage, List<DnaFiled> dnaFiledList) {
        File implFile = AutoCodeUtil.createFile(srcKeyFolder, keyFileName+".java", false);
        StringBuffer sb = new StringBuffer();
        sb.append("package "+keyPackage+";\r\n");
        sb.append("import com.jiuqi.dna.core.type.GUID;\r\n");
        sb.append(AutoCodeUtil.createNote(desc, "liubin" , 0));
        sb.append("public class "+keyFileName+"{\r\n");
        sb.append("\tpublic GUID recid;\r\n");
        sb.append("\tpublic Long recver;\r\n");
        sb.append("\tpublic int limit;\r\n");
        sb.append("\tpublic int offset;\r\n");
        for (DnaFiled dnaFiled : dnaFiledList) {
            String fieldName = dnaFiled.getName().toLowerCase();
            String fieldTypeStr = getFieldTypeStr(dnaFiled.getType());
            if(fieldTypeStr.equals("int")){
                fieldTypeStr = "Integer";
            }else if(fieldTypeStr.equals("long")){
                fieldTypeStr = "Long";
            }else if(fieldTypeStr.equals("boolean")){
                fieldTypeStr = "Boolean";
            }else if(fieldTypeStr.equals("double")){
                fieldTypeStr = "Double";
            }
            sb.append("\tpublic "+fieldTypeStr+" "+fieldName+";\r\n");
        }
        sb.append("}");
        writeIntoFile(implFile, sb.toString() , "GBK");
    }
}
