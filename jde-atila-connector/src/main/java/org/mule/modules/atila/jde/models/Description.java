package org.mule.modules.atila.jde.models;

import java.util.List;

/**
 * This Class represents the description an entity and its fields.
 */
public class Description {

    /**
     *
     */
    private String name;
    private String description;
    private DataType dataType;
    private String innerType;
    private List<Description> innerFields;
    private JDEAtilaConfiguracion conf;

    public Description() {
        super();
        this.innerType = "";
        this.innerFields = null;
    }

    public JDEAtilaConfiguracion getConf() {
        return conf;
    }

    public void setConf(JDEAtilaConfiguracion conf) {
        this.conf = conf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInnerType() {
        return innerType;
    }

    public void setInnerType(String innerType) {
        this.innerType = innerType;
    }

    public List<Description> getInnerFields() {
        return innerFields;
    }

    public void setInnerFields(List<Description> innerFields) {
        this.innerFields = innerFields;
    }

    @Override
    public String toString() {

        return "Description [name=" + name + ", description=" + description + ", dataType=" + dataType + ", innerType="
                + innerType + ", innerFields=" + innerFields + "]";
    }

}