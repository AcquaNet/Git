package oracle.e1.bssv.JP590000.valueobject;

import java.io.Serializable;

import oracle.e1.bssv.J5900002.valueobject.InternalInsertBatchJournalEntryStaging;
import oracle.e1.bssvfoundation.base.MessageValueObject;


/**
 * Return the number of rows inserted
 */
public class ConfirmInsertBatchJournalEntryStaging extends MessageValueObject  implements Serializable{
    private long numberRecordsInserted = 0;
    public ConfirmInsertBatchJournalEntryStaging() {
    }
    /**
     * Set NumberRecordsInserted to the value from the internal VO
     * @param internalVO
     */
    public ConfirmInsertBatchJournalEntryStaging(InternalInsertBatchJournalEntryStaging internalVO){
        this.setNumberRecordsInserted(internalVO.getNumberRecordsInserted());
    }

    public void setNumberRecordsInserted(long numberRecordsInserted) {
        this.numberRecordsInserted = numberRecordsInserted;
    }

    public long getNumberRecordsInserted() {
        return numberRecordsInserted;
    }
}
