package com.jiuqi.autocode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @ClassName DnaXml
 * @Description DNAXML对象
 * @author liubin
 * @Date 2017年7月27日 下午1:31:36
 * @version 1.0.0
 */
public class DnaXml {
    
    public static void main(String[] args) {
        DnaXml dnaXml = new DnaXml();
        dnaXml.addServiceNode(new ServiceNode("dna" , "com.jiuqi.dna.TestService"));
        dnaXml.addOrmNode(new OrmNode("dna" , "com.jiuqi.dna.TestOrm"));
        dnaXml.addPageNode(new PageNode("dna", "com.jiuqi.dna.TestPage" , "TestPage"));
        System.out.println(dnaXml.toXml());
    }
    
    private List<ServiceNode> serviceNodes = new ArrayList<ServiceNode>();
    private List<OrmNode> ormNodes = new ArrayList<OrmNode>();
    private List<PageNode> pageNodes = new ArrayList<PageNode>();
    private List<TableNode> tableNodes = new ArrayList<TableNode>();
    
    private DnaNode dnaNode = new DnaNode();
    public DnaXml(){
        PublishNode publishNode = new PublishNode();
        
        List<ServicesNode> servicesNodes = new ArrayList<ServicesNode>();
        ServicesNode servicesNode = new ServicesNode();
        servicesNode.setServiceNodes(serviceNodes);
        servicesNodes.add(servicesNode);
        publishNode.setServicesNodes(servicesNodes);
        
        List<OrmsNode> ormsNodes = new ArrayList<OrmsNode>();
        OrmsNode ormsNode = new OrmsNode();
        ormsNode.setOrmNodes(ormNodes);
        ormsNodes.add(ormsNode);
        publishNode.setOrmsNodes(ormsNodes);
        
        List<PagesNode> pagesNodes = new ArrayList<PagesNode>();
        PagesNode pagesNode = new PagesNode();
        pagesNode.setPageNodes(pageNodes);
        pagesNodes.add(pagesNode);
        publishNode.setPagesNodes(pagesNodes);
        
        List<TablesNode> tablesNodes = new ArrayList<TablesNode>();
        TablesNode tablesNode = new TablesNode();
        tablesNode.setTableNodes(tableNodes);
        tablesNodes.add(tablesNode);
        publishNode.setTablesNodes(tablesNodes);
        
        List<PublishNode> publishNodes = new ArrayList<PublishNode>();
        publishNodes.add(publishNode);
        dnaNode.setPublishNodes(publishNodes);
    }
    
    public String toXml(){
        XStream xstream = new XStream();
        xstream.autodetectAnnotations(true);
        String top = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n";  
        return top + xstream.toXML(dnaNode);
    }
    
    public void toXmlFile(String xmlFilePath){
        AutoCodeUtil.writeIntoFile(new File(xmlFilePath), this.toXml() , "UTF-8");
    }
    
    public void addServiceNode(ServiceNode serviceNode){
        this.serviceNodes.add(serviceNode);
    }
    
    public void addOrmNode(OrmNode ormNode){
        this.ormNodes.add(ormNode);
    }
    
    public void addPageNode(PageNode pageNode){
        this.pageNodes.add(pageNode);
    }
    
    public void addTableNode(TableNode tableNode){
        this.tableNodes.add(tableNode);
    }
}

@XStreamAlias("dna")
class DnaNode{
    
    @XStreamImplicit(itemFieldName="publish")
    private List<PublishNode> publishNodes;
    
    public List<PublishNode> getPublishNodes() {
        return publishNodes;
    }
    
    public void setPublishNodes(List<PublishNode> publishNodes) {
        this.publishNodes = publishNodes;
    }
}

@XStreamAlias("publish")
class PublishNode{
    @XStreamImplicit(itemFieldName="services")
    private List<ServicesNode> servicesNodes;
    
    @XStreamImplicit(itemFieldName="orms")
    private List<OrmsNode> ormsNodes;
    
    @XStreamImplicit(itemFieldName="pages")
    private List<PagesNode> pagesNodes;
    
    @XStreamImplicit(itemFieldName="tables")
    private List<TablesNode> tablesNodes;
    
    public List<ServicesNode> getServicesNodes() {
        return servicesNodes;
    }
    
    public void setServicesNodes(List<ServicesNode> servicesNodes) {
        this.servicesNodes = servicesNodes;
    }

    public List<OrmsNode> getOrmsNodes() {
        return ormsNodes;
    }

    public void setOrmsNodes(List<OrmsNode> ormsNodes) {
        this.ormsNodes = ormsNodes;
    }

    public List<PagesNode> getPagesNodes() {
        return pagesNodes;
    }
    
    public void setPagesNodes(List<PagesNode> pagesNodes) {
        this.pagesNodes = pagesNodes;
    }
    
    public List<TablesNode> getTablesNodes() {
        return tablesNodes;
    }
    
    public void setTablesNodes(List<TablesNode> tablesNodes) {
        this.tablesNodes = tablesNodes;
    }
}

@XStreamAlias("services")
class ServicesNode{
    @XStreamImplicit(itemFieldName="service")
    private List<ServiceNode> serviceNodes;
    private List<OrmsNode> ormsNodes;
    
    public List<ServiceNode> getServiceNodes() {
        return serviceNodes;
    }
    
    public void setServiceNodes(List<ServiceNode> serviceNodes) {
        this.serviceNodes = serviceNodes;
    }

    public List<OrmsNode> getOrmsNodes() {
        return ormsNodes;
    }

    public void setOrmsNodes(List<OrmsNode> ormsNodes) {
        this.ormsNodes = ormsNodes;
    }
}

@XStreamAlias("service")
class ServiceNode extends CommonNode{

    public ServiceNode(String space, String classPath) {
        super(space, classPath);
    }
}

@XStreamAlias("orms")
class OrmsNode{
    @XStreamImplicit(itemFieldName="orm")
    private List<OrmNode> ormNodes;
    
    public List<OrmNode> getOrmNodes() {
        return ormNodes;
    }
    
    public void setOrmNodes(List<OrmNode> ormNodes) {
        this.ormNodes = ormNodes;
    }
}

@XStreamAlias("pages")
class PagesNode{
    @XStreamImplicit(itemFieldName="page")
    private List<PageNode> pageNodes;

    public List<PageNode> getPageNodes() {
        return pageNodes;
    }
    
    public void setPageNodes(List<PageNode> pageNodes) {
        this.pageNodes = pageNodes;
    }
}

@XStreamAlias("tables")
class TablesNode{
    @XStreamImplicit(itemFieldName="table")
    private List<TableNode> tableNodes;
    
    public List<TableNode> getTableNodes() {
        return tableNodes;
    }
    
    public void setTableNodes(List<TableNode> tableNodes) {
        this.tableNodes = tableNodes;
    }
    
}


class CommonNode{
    @XStreamAsAttribute
    @XStreamAlias("space")
    private String spaceNode;
    @XStreamAsAttribute
    @XStreamAlias("class")
    private String classNode;
    
    public CommonNode(String space , String classPath){
        this.spaceNode = space;
        this.classNode = classPath;
    }
    
    public String getSpaceNode() {
        return spaceNode;
    }
    
    public void setSpaceNode(String spaceNode) {
        this.spaceNode = spaceNode;
    }
    
    public String getClassNode() {
        return classNode;
    }
    
    public void setClassNode(String classNode) {
        this.classNode = classNode;
    }
}


@XStreamAlias("orm")
class OrmNode extends CommonNode{

    public OrmNode(String space, String classPath) {
        super(space, classPath);
    }
}

@XStreamAlias("command")
class CommandNode extends CommonNode{

    public CommandNode(String space, String classPath) {
        super(space, classPath);
    }
}

@XStreamAlias("query")
class QuerydNode extends CommonNode{

    public QuerydNode(String space, String classPath) {
        super(space, classPath);
    }
}

@XStreamAlias("table")
class TableNode extends CommonNode{

    public TableNode(String space, String classPath) {
        super(space, classPath);
    }
}

@XStreamAlias("uientry")
class UientryNode extends CommonNode{

    public UientryNode(String space, String classPath) {
        super(space, classPath);
    }
}

@XStreamAlias("page")
class PageNode extends CommonNode{

    @XStreamAsAttribute
    @XStreamAlias("name")
    private String pageName;
    
    public PageNode(String space, String classPath , String pageName) {
        super(space, classPath);
        this.setPageName(pageName);
    }
    
    public String getPageName() {
        return pageName;
    }
    
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
