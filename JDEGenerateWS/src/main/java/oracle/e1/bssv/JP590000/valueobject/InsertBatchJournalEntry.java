package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;


import java.util.ArrayList;

import oracle.e1.bssv.J5900002.valueobject.InternalInsertBatchJournalEntryStaging;
import oracle.e1.bssv.J5900002.valueobject.InternalInsertBatchJournaleEntryStagingFields;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.base.ValueObject;
import oracle.e1.bssvfoundation.connection.IConnection;
import oracle.e1.bssvfoundation.util.E1MessageList;

public class InsertBatchJournalEntry extends ValueObject implements Serializable {
    /**
     * Collection of records to be inserted into the F0911Z1 table.
     */
    private InsertBatchJournalEntryStagingFields[] insertFields;


    public InsertBatchJournalEntry() {
    }

    public void setInsertFields(InsertBatchJournalEntryStagingFields[] insertFields) {
        if(insertFields != null)
            this.insertFields = insertFields;
    }

    public void setInsertField(int i, InsertBatchJournalEntryStagingFields insertField){
        if(this.insertFields !=null && this.insertFields.length>i)
           this.insertFields[i]= insertField;
    }

    public InsertBatchJournalEntryStagingFields[] getInsertFields() {
        return insertFields;
    }
    
    public InsertBatchJournalEntryStagingFields getInsertFields(int i) {
        if(this.insertFields !=null && this.insertFields.length>i){
           return insertFields[i];
        }else{
            return null;
        }
    }
    
    public E1MessageList mapFromPublished(IContext context,
                                            IConnection connection,
                                            InternalInsertBatchJournalEntryStaging vo) {
    
            ArrayList insertFieldList = new ArrayList();
            E1MessageList messages = new E1MessageList();
            E1MessageList retMessages = new E1MessageList();
            
            for(int i = 0;i<this.getInsertFields().length;i++){
                InternalInsertBatchJournaleEntryStagingFields fields = new InternalInsertBatchJournaleEntryStagingFields();
                retMessages = this.getInsertFields(i).mapFromPublished(context, connection, fields);
                insertFieldList.add(fields);
                messages.addMessages(retMessages);
            }
            
            vo.setInsertFields(insertFieldList);
            return messages;
    }
}
