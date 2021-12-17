package oracle.e1.bssv.JP590000;
 
import oracle.e1.bssv.J5900002.BatchJournalEntryInsertProcessor;
import oracle.e1.bssv.J5900002.valueobject.InternalInsertBatchJournalEntryStaging;
import oracle.e1.bssv.JP590000.valueobject.ConfirmInsertBatchJournalEntryStaging;
import oracle.e1.bssv.JP590000.valueobject.InsertBatchJournalEntry;
import oracle.e1.bssvfoundation.base.IContext;
import oracle.e1.bssvfoundation.base.PublishedBusinessService;
import oracle.e1.bssvfoundation.connection.IConnection;
import oracle.e1.bssvfoundation.exception.BusinessServiceException;
import oracle.e1.bssvfoundation.util.E1MessageList;


public class FinancialsManager extends PublishedBusinessService {

    /**
     * Published Business Service Public Constructor
     */
    public FinancialsManager() {
    }
 

    /**
     * Protected method for FinancialManager Published Business Service.  InsertBatchJournalEntry
     * will call make calls to BSSV classes
     * for completing business process.
     * @param vo the value object representing input data for
     * @param context conditionally provides the connection for the database operation and logging information
     * @param connection can either be an explicit connection or null. If null the default connection is used.
     * @return response value object is the data returned from the business process
     * @throws oracle.e1.bssvfoundation.exception.BusinessServiceException
     */
    public ConfirmInsertBatchJournalEntryStaging insertBatchJournalEntry(IContext context,
                                                                            IConnection connection,
                                                                            InsertBatchJournalEntry vo) throws BusinessServiceException {
        //perform all work within try block, finally will clean up any connections
        try {
            //Call start published method, passing context of null
            //will return context object so BSFN or DB operation can be called later.
            //Context will be used to indicate default transaction boundary, as well as access
            //to formatting and logging operations.
            context =
                    startPublishedMethod(context, "insertBatchJournalEntryStagingTable",
                                         vo);
            //Create new Published Business Service messages object for holding errors and warnings that occur during processing.
            E1MessageList messages = new E1MessageList();


            // Create a new internal vo based on the published vo passed in.
            InternalInsertBatchJournalEntryStaging internalVO =
                new InternalInsertBatchJournalEntryStaging();

            //Call formatters and utilities.

            E1MessageList mapMessages =
                vo.mapFromPublished(context, connection, internalVO);
            messages.addMessages(mapMessages);
            if (messages.hasErrors()) {
                //get the string representation of all the messages
                String error = messages.getMessagesAsString();
                //Throw new BusinessServiceException
                throw new BusinessServiceException(error, context);
            }

            //Call BSSV insertBatchJournalEntry passing context and internal VO
            E1MessageList sbfMessages =
                BatchJournalEntryInsertProcessor.insertBatchJournalEntryStaging(context,
                                                                                connection,
                                                                                internalVO);

            //Add messages returned from BSSV to message list for Published Business Service.
            messages.addMessages(sbfMessages);
            //Published Business Service will send either warnings in the Confirm Value Object or throw a BusinessServiceException.
            //If messages contains errors, throw the exception

            if (messages.hasErrors()) {
                //get the string representation of all the messages
                String error = messages.getMessagesAsString();
                //Throw new BusinessServiceException
                throw new BusinessServiceException(error, context);
            }

            //Exception was not thrown, so create the confirm VO from internal VO
            ConfirmInsertBatchJournalEntryStaging confirmVO =
                new ConfirmInsertBatchJournalEntryStaging(internalVO);


            confirmVO.setE1MessageList(messages);
            finishPublishedMethod(context,
                                  "insertBatchJournalEntryStagingTable");
            //return outVO, filled with return values and messages
            return confirmVO;


        } finally {
            //Call close to clean up all remaining connections and resources.
            close(context, "InsertBatchJournalEntry");

        }
    }

}
