package oracle.e1.bssv.JP590000;

import oracle.e1.bssv.J0900001.GLAccountQueryProcessor;
import oracle.e1.bssv.J0900001.valueobject.InternalGetGLAccount;
import oracle.e1.bssv.J0900002.BatchJournalEntryInsertProcessor;
import oracle.e1.bssv.J0900002.valueobject.InternalInsertBatchJournalEntryStaging;
import oracle.e1.bssv.JP590000.valueobject.ConfirmInsertBatchJournalEntryStaging;
import oracle.e1.bssv.JP590000.valueobject.GetGLAccount;
import oracle.e1.bssv.JP590000.valueobject.InsertBatchJournalEntry;
import oracle.e1.bssv.JP590000.valueobject.ShowGLAccount;
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
     * Published method getGLAccount (JP090000-J0900001) is a database query operation
     * that enables consumers to retrieve and review general ledger account records
     * from the Account Master table (F0901) in the JD Edwards EnterpriseOne General
     * Accounting system. If the operation encounters errors during processing, the
     * operation does not complete and the errors are returned to the consumer.
     * @param vo the value object representing input data for Get GL Acount
     * @return showVO the response data from the query of F0901
     * @throws oracle.e1.bssvfoundation.exception.BusinessServiceException
     */
    public ShowGLAccount getGLAccount(GetGLAccount vo) throws BusinessServiceException {
        return (getGLAccount(null, null, vo));
    }

    /**
     * Protected method for FinancialsManager Published Business Service.  getGLAccount will call make calls to BSSV classes
     * for completing business process.
     * @param vo the value object representing input data for Get GL Acount.
     * @param context conditionally provides the connection for the database operation and logging information
     * @param connection can either be an explicit connection or null. If null the default connection is used.
     * @return confirmVO the response data from the business process Show GL Account.
     * @throws oracle.e1.bssvfoundation.exception.BusinessServiceException
     */
    public ShowGLAccount getGLAccount(IContext context,
                                         IConnection connection,
                                         GetGLAccount vo) throws BusinessServiceException {
        //perform all work within try block, finally will clean up any connections
        try {
            //Call start published method, passing context of null
            //will return context object so BSFN or DB operation can be called later.
            //Context will be used to indicate default transaction boundary, as well as access
            //to formatting and logging operations.
            context =
                    startPublishedMethod(context, "getGLAccount", vo, IConnection.AUTO);

            //Create new Published Business Service messages object for holding errors and warnings that occur during processing.
            E1MessageList messages = new E1MessageList();
            // Create a new internal value object.
            InternalGetGLAccount internalVO = new InternalGetGLAccount();


            messages.addMessages(vo.mapFromPublished(context, connection,
                                                     internalVO));
            //Call BSSV passing context and internal VO
            if (!messages.hasErrors()) {
                messages.addMessages(GLAccountQueryProcessor.getGLAccount(context,
                                                                          connection,
                                                                          internalVO));
            }

            //Published Business Service will send either warnings in the Confirm Value Object or throw a BusinessServiceException.
            //If messages contains errors, throw the exception

            if (messages.hasErrors()) {
                //get the string representation of all the messages
                String error = messages.getMessagesAsString();
                //Throw new BusinessServiceException
                throw new BusinessServiceException(error, context);
            }

            //Exception was not thrown, so create the confirm VO from internal VO
            ShowGLAccount showVO = new ShowGLAccount(internalVO);
            showVO.setE1MessageList(messages);

            //Call finish published method, passing the context
            //to commit default implicit transaction(in case of no exceptions)
            finishPublishedMethod(context, "getGLAccount");
            //return showVO, mapped with return values and messages
            return showVO;

        } finally {

            //Call close to clean up all remaining connections and resources.
            close(context, "getGLAccount");
        }
    }

    /**
     * Published method insertBatchJournalEntry (JP090000-J0900002) is a database
     * insert operation that enables consumers to add journal entry records to
     * the Journal Entry Transactions – Batch table (F0911Z1). The F0911Z1 is a
     * temporary table in the JD Edwards EnterpriseOne General Accounting system.
     * After records have been added to this table, consumers can use JD Edwards
     * EnterpriseOne programs to update and process the journal entries that have
     * been added to the temporary table.
     * @param vo the value object representing input data for
     * @return confirmVO the response data from the business process
     * @throws oracle.e1.bssvfoundation.exception.BusinessServiceException
     */


    public ConfirmInsertBatchJournalEntryStaging insertBatchJournalEntry(InsertBatchJournalEntry vo) throws BusinessServiceException {
        return (insertBatchJournalEntry(null, null, vo));
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
