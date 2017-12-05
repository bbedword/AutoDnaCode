package com.jiuqi.autocode;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


/**
 * @ClassName DnaTable
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author liubin
 * @Date 2017年7月27日 下午5:55:23
 * @version 1.0.0
 */
@XStreamAlias("tabledefine")
public class DnaTable {
    
    @XStreamAsAttribute
    @XStreamAlias("storagename")
    private String storagename;
    
    @XStreamAsAttribute
    @XStreamAlias("ID")
    private String id;
    
    @XStreamAsAttribute
    @XStreamAlias("name-db")
    private String nameDb;
    
    @XStreamAsAttribute
    @XStreamAlias("category")
    private String category;
    
    @XStreamAsAttribute
    @XStreamAlias("title")
    private String title;
    
    @XStreamAsAttribute
    @XStreamAlias("author")
    private String author;
    
    @XStreamAsAttribute
    @XStreamAlias("description")
    private String description;
    
    @XStreamAsAttribute
    @XStreamAlias("packagename")
    private String packagename;
    
    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;
    
    @XStreamAsAttribute
    @XStreamAlias("isneedrebuild")
    private boolean isneedrebuild;
    
    @XStreamImplicit(itemFieldName="fields")
    private List<DnaFileds> dnaFileds = new ArrayList<DnaFileds>();
    
    @XStreamImplicit(itemFieldName="dbtables")
    private List<DnaDbtables> dbtables = new ArrayList<DnaDbtables>();
    
    @XStreamImplicit(itemFieldName="indexs")
    private List<DnaIndexs> dnaIndexs;
    
    @XStreamImplicit(itemFieldName="hierarchies")
    private List<DnaHierarchies> dnaHierarchies;
    
    @XStreamImplicit(itemFieldName="relations")
    private List<DnaRelations> dnaRelations;

    public List<DnaFileds> getDnaFileds() {
        return dnaFileds;
    }
    
    public void setDnaFileds(List<DnaFileds> dnaFileds) {
        this.dnaFileds = dnaFileds;
    }

    public List<DnaDbtables> getDbtables() {
        return dbtables;
    }
    
    public void setDbtables(List<DnaDbtables> dbtables) {
        this.dbtables = dbtables;
    }
    
    public List<DnaIndexs> getDnaIndexs() {
        return dnaIndexs;
    }

    public void setDnaIndexs(List<DnaIndexs> dnaIndexs) {
        this.dnaIndexs = dnaIndexs;
    }
    
    public List<DnaHierarchies> getDnaHierarchies() {
        return dnaHierarchies;
    }
    
    public void setDnaHierarchies(List<DnaHierarchies> dnaHierarchies) {
        this.dnaHierarchies = dnaHierarchies;
    }

    public List<DnaRelations> getDnaRelations() {
        return dnaRelations;
    }
    
    public void setDnaRelations(List<DnaRelations> dnaRelations) {
        this.dnaRelations = dnaRelations;
    }

    public String getStoragename() {
        return storagename;
    }
    
    public void setStoragename(String storagename) {
        this.storagename = storagename;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getNameDb() {
        return nameDb;
    }
    
    public void setNameDb(String nameDb) {
        this.nameDb = nameDb;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPackagename() {
        return packagename;
    }
    
    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isIsneedrebuild() {
        return isneedrebuild;
    }

    public void setIsneedrebuild(boolean isneedrebuild) {
        this.isneedrebuild = isneedrebuild;
    }

    public String toXml(){
        XStream xStream = new XStream();
        xStream.autodetectAnnotations(true);
        String xml = xStream.toXML(this);
        System.out.println(xml);
        return xml;
    }
}
@XStreamAlias("indexs")
class DnaIndexs{}
@XStreamAlias("hierarchies")
class DnaHierarchies{}
@XStreamAlias("relations")
class DnaRelations{}

@XStreamAlias("fields")
class DnaFileds {

    @XStreamImplicit(itemFieldName="field")
    private List<DnaFiled> dnaFileds = new ArrayList<DnaFiled>();
    
    public List<DnaFiled> getDnaFileds() {
        return dnaFileds;
    }
    
    public void setDnaFileds(List<DnaFiled> dnaFileds) {
        this.dnaFileds = dnaFileds;
    }
}

@XStreamAlias("field")
class DnaFiled {

    @XStreamAsAttribute
    @XStreamAlias("dbtable")
    private String dbtable;
    
    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;
    
    @XStreamAsAttribute
    @XStreamAlias("name-db")
    private String nameDb;
    
    @XStreamAsAttribute
    @XStreamAlias("type")
    private String type;
    
    @XStreamAsAttribute
    @XStreamAlias("primary-key")
    private boolean primaryKey;
    
    @XStreamAsAttribute
    @XStreamAlias("keep-valid")
    private boolean keepValid;
    
    @XStreamAsAttribute
    @XStreamAlias("author")
    private String author;
    
    @XStreamAsAttribute
    @XStreamAlias("title")
    private String title;
    
    @XStreamAsAttribute
    @XStreamAlias("description")
    private String description;
    
    @XStreamAsAttribute
    @XStreamAlias("ID")
    private String id;

    public String getDbtable() {
        return dbtable;
    }

    public void setDbtable(String dbtable) {
        this.dbtable = dbtable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDb() {
        return nameDb;
    }
    
    public void setNameDb(String nameDb) {
        this.nameDb = nameDb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getPrimaryKey() {
        return primaryKey;
    }
    
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean getKeepValid() {
        return keepValid;
    }

    public void setKeepValid(boolean keepValid) {
        this.keepValid = keepValid;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}

@XStreamAlias("dbtables")
class DnaDbtables {
    
    @XStreamImplicit(itemFieldName="dbtable")
    private List<DnaDbtable> dbtables = new ArrayList<DnaDbtable>();

    public List<DnaDbtable> getDbtables() {
        return dbtables;
    }

    public void setDbtables(List<DnaDbtable> dbtables) {
        this.dbtables = dbtables;
    }
}

@XStreamAlias("dbtable")
class DnaDbtable {
    
    @XStreamAsAttribute
    @XStreamAlias("author")
    private String author;
    
    @XStreamAsAttribute
    @XStreamAlias("name")
    private String name;
    
    @XStreamAsAttribute
    @XStreamAlias("name-db")
    private String nameDb;
    
    @XStreamAsAttribute
    @XStreamAlias("title")
    private String title;
    
    @XStreamAsAttribute
    @XStreamAlias("description")
    private String description;
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNameDb() {
        return nameDb;
    }
    
    public void setNameDb(String nameDb) {
        this.nameDb = nameDb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}