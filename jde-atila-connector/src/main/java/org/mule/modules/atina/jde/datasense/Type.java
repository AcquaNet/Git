package org.mule.modules.atina.jde.datasense;

import org.mule.common.metadata.builder.DynamicObjectBuilder;
import org.mule.common.metadata.builder.MetaDataBuilder;
import org.mule.common.metadata.datatype.DataType;

public enum Type {

    DATE {

        @Override
        public void addField(MetaDataBuilder<?> objectBuilder, String name) {
            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(name, DataType.DATE_TIME);

        }
    },
    DOUBLE {

        @Override
        public void addField(MetaDataBuilder<?> objectBuilder, String name) {
            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(name, DataType.DOUBLE);

        }
    },
    INTEGER {

        @Override
        public void addField(MetaDataBuilder<?> objectBuilder, String name) {
            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(name, DataType.INTEGER);

        }
    },
    BOOLEAN {

        @Override
        public void addField(MetaDataBuilder<?> objectBuilder, String name) {
            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(name, DataType.BOOLEAN);

        }
    },
    STRING {

        @Override
        public void addField(MetaDataBuilder<?> objectBuilder, String name) {
            ((DynamicObjectBuilder<?>) objectBuilder).addSimpleField(name, DataType.STRING);

        }
    };

    public abstract void addField(final MetaDataBuilder<?> objectBuilder, String name);

}