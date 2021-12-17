package oracle.e1.bssv.J5900002.valueobject;

import java.util.ArrayList;
import oracle.e1.bssvfoundation.base.ValueObject;



public class InternalInsertBatchJournalEntryStaging extends ValueObject {

    /**
    *  The number of rows inserted
    */
    private long numberRecordsInserted = 0;
    /**
     * Collection of InternalInsertBatchJournaleEntryStagingFields records to be 
     * inserted into the Journal entry Z Table (F0911Z1).
     */
    private ArrayList insertFields;
    
    public InternalInsertBatchJournalEntryStaging() {
    }

    public void setNumberRecordsInserted(long numberRecordsInserted) {
        this.numberRecordsInserted = numberRecordsInserted;
    }

    public long getNumberRecordsInserted() {
        return numberRecordsInserted;
    }

    public void setInsertFields(ArrayList insertFields) {
        this.insertFields = insertFields;
    }

    public ArrayList getInsertFields() {
        return insertFields;
    }
    
    public InternalInsertBatchJournaleEntryStagingFields getInsertFields(int i){
        if(insertFields !=null && insertFields.size()>=i){
           return (InternalInsertBatchJournaleEntryStagingFields) this.insertFields.get(i);
        }else{
            return null;
        }
    }
}
