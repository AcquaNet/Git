/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.atina.jdeconnector.sqltransform;
 
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang3.StringUtils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 *
 * @author jgodi
 */
public class TransformSQSentenceLtoXMLSentece {
    
    private static final String DATA_SEQUENCING_BEGIN = "<DATA_SEQUENCING>";
    private static final String DATA_SEQUENCING_END = "</DATA_SEQUENCING>"; 
    private static final String DATA_SELECTION_BEGIN = "<DATA_SELECTION>";
    private static final String DATA_SELECTION_END = "</DATA_SELECTION>";
    private static final String COLUMN_SELECTION_BEGIN = "<COLUMN_SELECTION>";
    private static final String COLUMN_SELECTION_END = "</COLUMN_SELECTION>";
    private static final String OPERAND_BEGIN = "<OPERAND>";
    private static final String OPERAND_END = "</OPERAND>"; 
    private static final String WHERE = "WHERE";
    
    private CCJSqlParserManager pm;
    private PlainSelect plainSelect;
    private String tableOrViewName = "";
    private List<String> columnList = new ArrayList<String>();
    private String whereStr;
    private List<TransformSQSentenceLtoXMLSenteceColumn> orderByList = new ArrayList<TransformSQSentenceLtoXMLSenteceColumn>();
    private StringBuilder sqlXML = new StringBuilder();
    private StringBuilder sqlWhereXML = new StringBuilder();
    private Queue<TransformSQSentenceLtoXMLSenteceParenthesis> queueParent = new LinkedList<TransformSQSentenceLtoXMLSenteceParenthesis>();
    private int parentLevel = 0;
    private String columnSelection;
    private String whereSelection;
    private String sequenceSelection;
     
    public String convertWhereSQLtoXML(String sqlSentence) throws JSQLParserException, ClassNotFoundException {

        convertSQLtoXML("SELECT AX FROM TABLEXX WHERE " + sqlSentence);

        return sqlWhereXML.toString();

    }
  
    public String getTableOrViewName() {
        return tableOrViewName;
    }

    public List<String> getColumnList() {
        return columnList;
    }

    public List<TransformSQSentenceLtoXMLSenteceColumn> getOrderByList() {
        return orderByList;
    }

    public String convertSQLtoXML(String sqlS) throws JSQLParserException, ClassNotFoundException {
 

        // =============
        // Create Parser
        // =============

        pm = new CCJSqlParserManager();

        // =============
        // Fix Sentence
        // =============

        String sqlSentence = StringUtils.replace(StringUtils.replace(sqlS, "&lt;", "<"), "&gt;", ">");

        // ================
        // Create Statement
        // ================

        Statement statement = pm.parse(new StringReader(sqlSentence));

        Select selectStatement = null;

        if (statement instanceof Select) {

            selectStatement = (Select)statement;

        }

        // ======================
        // Get Table or View Name
        // ======================

        tableOrViewName = getTableOrViewName(selectStatement);

        // ===================
        // Create Plain Select
        // ===================

        if (!tableOrViewName.isEmpty()) {

            SelectBody body = selectStatement.getSelectBody();

            plainSelect = (PlainSelect)body;

        }

        // ===========
        // Get Columns
        // ===========

        if (plainSelect != null) {

            getColumns();

        }

        // ===================
        // Get Where
        // ===================

        if (plainSelect != null) {

            getWhere();

        }

        // ===================
        // Order By
        // ===================

        if (plainSelect != null) {

            getOrderBy();

        }

        return sqlXML.toString();
    }

    private void getOrderBy() {
        
        List<OrderByElement> orderList = plainSelect.getOrderByElements();

        if (orderList != null && !orderList.isEmpty()) {

            StringBuilder sequenceSelec = new StringBuilder();
            sequenceSelection = "";

            sequenceSelec.append(DATA_SEQUENCING_BEGIN);

            for (OrderByElement orderElement : orderList) {

                Column column = (Column)orderElement.getExpression();

                orderByList.add(new TransformSQSentenceLtoXMLSenteceColumn(column.getColumnName(), orderElement.isAsc()));

                if (orderElement.isAsc()) {
                    sequenceSelec.append("<DATA SORT=\"ASCENDING\">");
                } else {
                    sequenceSelec.append("<DATA SORT=\"DESCENDING\">");
                }

                sequenceSelec.append("<COLUMN NAME=\"\" TABLE=\"" + this.tableOrViewName + "\" INSTANCE=\"\" ALIAS=\"" + column.getColumnName() + "\"/>");
                sequenceSelec.append("</DATA>");

            }

            sequenceSelec.append(DATA_SEQUENCING_END);

            sequenceSelection = sequenceSelec.toString();

            sqlXML.append(sequenceSelection);

        }
    }

    private void getWhere() throws ClassNotFoundException {
        Expression whereExpresion = plainSelect.getWhere();
        whereStr = whereExpresion.toString();

        StringBuilder whereSelec = new StringBuilder();
        whereSelection = "";

        if (whereStr.length() > 0) {

            sqlXML.append(DATA_SELECTION_BEGIN);
            whereSelec.append(DATA_SELECTION_BEGIN);

            String parent = WHERE;

            Expression expressionMain = plainSelect.getWhere();

            evaluateSQLWhere(parent, expressionMain);

            sqlXML.append(sqlWhereXML);
            whereSelec.append(sqlWhereXML);

            sqlXML.append(DATA_SELECTION_END);
            whereSelec.append(DATA_SELECTION_END);

            whereSelection = whereSelec.toString();
        }
    }

    private void getColumns() {
        List<SelectItem> columnSelect = plainSelect.getSelectItems();

        columnSelection = "";
        StringBuilder columnSelec = new StringBuilder();

        if (!columnSelect.isEmpty()) {

            columnSelec.append(COLUMN_SELECTION_BEGIN);

            for (SelectItem itemS : columnSelect) {
                columnList.add(itemS.toString());

                columnSelec.append("<COLUMN ALIAS=\"" + itemS.toString() + "\"/>");
            }

            columnSelec.append(COLUMN_SELECTION_END);
        }

        columnSelection = columnSelec.toString();

        sqlXML.append(columnSelection);
    }

    private String getTableOrViewName(Select selectStatement) {
        if (selectStatement != null) {

            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

            List<String> tableList = tablesNamesFinder.getTableList(selectStatement);

            if (tableList.size() == 1) {
                return tableList.get(0);
            }
        }
        return "";
    }

    private void evaluateSQLWhere(String parent, Expression expressionMain) throws ClassNotFoundException {

        ifAnd(parent, expressionMain);

        ifOr(parent, expressionMain);

        ifParenthesis(parent, expressionMain);

        ifEquals(parent, expressionMain);

        ifNotEquals(parent, expressionMain);

        ifGreaterThan(parent, expressionMain);

        ifGreaterThanEquals(parent, expressionMain);

        ifMinorThan(parent, expressionMain);

        ifMinorThanEquals(parent, expressionMain);

        ifIn(parent, expressionMain);

        ifBetween(parent, expressionMain);

    }

    private void ifBetween(String parent, Expression expressionMain) {
        if (expressionMain instanceof Between) {

            doBetweenExpression(parent, expressionMain);

        }
    }

    private void ifIn(String parent, Expression expressionMain) {
        if (expressionMain instanceof InExpression) {

            doInExpression(parent, expressionMain);

        }
    }

    private void ifMinorThanEquals(String parent, Expression expressionMain) {
        if (expressionMain instanceof MinorThanEquals) {

            doLessThanOrEquals(parent, expressionMain);

        }
    }

    private void ifMinorThan(String parent, Expression expressionMain) {
        if (expressionMain instanceof MinorThan) {

            doLessThan(parent, expressionMain);

        }
    }

    private void ifGreaterThanEquals(String parent, Expression expressionMain) {
        if (expressionMain instanceof GreaterThanEquals) {

            doGreaterThanOrEquals(parent, expressionMain);

        }
    }

    private void ifGreaterThan(String parent, Expression expressionMain) {
        if (expressionMain instanceof GreaterThan) {

            doGreaterThan(parent, expressionMain);

        }
    }

    private void ifNotEquals(String parent, Expression expressionMain) {
        if (expressionMain instanceof NotEqualsTo) {

            doNotEqualsTo(parent, expressionMain);

        }
    }

    private void ifEquals(String parent, Expression expressionMain) {
        if (expressionMain instanceof EqualsTo) {

            doEqualsTo(parent, expressionMain);

        }
    }

    private void ifParenthesis(String parent, Expression expressionMain) throws ClassNotFoundException {
        if (expressionMain instanceof Parenthesis) {

            doParenthesis(parent, expressionMain);

        }
    }

    private void ifOr(String parent, Expression expressionMain) throws ClassNotFoundException {
        if (expressionMain instanceof OrExpression) {

            evaluateSQLWhere(parent, ((OrExpression)expressionMain).getLeftExpression());

            evaluateSQLWhere("OR", ((OrExpression)expressionMain).getRightExpression());

        }
    }

    private void ifAnd(String parent, Expression expressionMain) throws ClassNotFoundException {
        if (expressionMain instanceof AndExpression) {

            evaluateSQLWhere(parent, ((AndExpression)expressionMain).getLeftExpression());

            evaluateSQLWhere("AND", ((AndExpression)expressionMain).getRightExpression());

        }
    }

    private void doBetweenExpression(String parent, Expression expressionMain) {
        if (parent.compareTo(WHERE) == 0 || parent.compareTo("AND") == 0 || parent.compareTo("OR") == 0) {

            sqlWhereXML.append("<CLAUSE TYPE=\"" + parent + "\">");
        }

        Column leftExpresion = (Column)((Between)expressionMain).getLeftExpression();

        String tableToUse = this.tableOrViewName;

        if (leftExpresion.getTable() != null && !leftExpresion.getTable()
            .getName()
            .isEmpty()) {
            tableToUse = leftExpresion.getTable()
                .getName();
        }

        sqlWhereXML.append("<COLUMN NAME=\"\" TABLE=\"" + tableToUse + "\" INSTANCE=\"\" ALIAS=\"" + leftExpresion.getColumnName() + "\"/>");

        boolean isNotBetween = ((Between)expressionMain).isNot();

        if (isNotBetween) {
            sqlWhereXML.append("<OPERATOR TYPE=\"NB\"/>");
        } else {
            sqlWhereXML.append("<OPERATOR TYPE=\"BW\"/>");
        }

        sqlWhereXML.append("<OPERAND>");
        sqlWhereXML.append("<RANGE>");

        Expression leftBetween = ((Between)expressionMain).getBetweenExpressionStart();
        Expression rightBetween = ((Between)expressionMain).getBetweenExpressionEnd();

        getOperando(leftBetween, "LITERAL_FROM");
        getOperando(rightBetween, "LITERAL_TO");

        sqlWhereXML.append("</RANGE>");
        sqlWhereXML.append(OPERAND_END);

        if (!queueParent.isEmpty() && queueParent.peek()
            .isOpened()) {

            sqlWhereXML.append("<PARENS TYPE=\"OPEN\" COUNT=\"" + Integer.toString(queueParent.peek()
                .getLevel()) + "\"/>");
            queueParent.peek()
                .setOpened(false);

        }

        sqlWhereXML.append("</CLAUSE>");
    }

    private void doInExpression(String parent, Expression expressionMain) {
        if (parent.compareTo(WHERE) == 0 || parent.compareTo("AND") == 0 || parent.compareTo("OR") == 0) {

            sqlWhereXML.append("<CLAUSE TYPE=\"" + parent + "\">");
        }

        Column leftExpresion = (Column)((InExpression)expressionMain).getLeftExpression();

        String tableToUse = this.tableOrViewName;

        if (leftExpresion.getTable() != null && !leftExpresion.getTable()
            .getName()
            .isEmpty()) {
            tableToUse = leftExpresion.getTable()
                .getName();
        }

        sqlWhereXML.append("<COLUMN NAME=\"\" TABLE=\"" + tableToUse + "\" INSTANCE=\"\" ALIAS=\"" + leftExpresion.getColumnName() + "\"/>");

        boolean isNotBetween = ((InExpression)expressionMain).isNot();

        if (isNotBetween) {
            sqlWhereXML.append("<OPERATOR TYPE=\"NI\"/>");
        } else {
            sqlWhereXML.append("<OPERATOR TYPE=\"IN\"/>");
        }

        ExpressionList rightExpression = (ExpressionList)((InExpression)expressionMain).getRightItemsList();

        List<Expression> expression = rightExpression.getExpressions();

        sqlWhereXML.append(OPERAND_BEGIN);

        for (Expression expressionLit : expression) {
            getOperando(expressionLit);
        }

        sqlWhereXML.append(OPERAND_END);

        if (!queueParent.isEmpty() && queueParent.peek()
            .isOpened()) {

            sqlWhereXML.append("<PARENS TYPE=\"OPEN\" COUNT=\"" + Integer.toString(queueParent.peek()
                .getLevel()) + "\"/>");
            queueParent.peek()
                .setOpened(false);

        }

        sqlWhereXML.append("</CLAUSE>");
    }

    private void doLessThanOrEquals(String parent, Expression expressionMain) {
        if (parent.compareTo("WHERE") == 0 || parent.compareTo("AND") == 0 || parent.compareTo("OR") == 0) {

            sqlWhereXML.append("<CLAUSE TYPE=\"" + parent + "\">");
        }

        Column leftExpresion = (Column)((MinorThanEquals)expressionMain).getLeftExpression();

        String tableToUse = this.tableOrViewName;

        if (leftExpresion.getTable() != null && !leftExpresion.getTable()
            .getName()
            .isEmpty()) {
            tableToUse = leftExpresion.getTable()
                .getName();
        }

        Expression rightExpresion = ((MinorThanEquals)expressionMain).getRightExpression();

        sqlWhereXML.append("<COLUMN NAME=\"\" TABLE=\"" + tableToUse + "\" INSTANCE=\"\" ALIAS=\"" + leftExpresion.getColumnName() + "\"/>");
        sqlWhereXML.append("<OPERATOR TYPE=\"LE\"/>");
        sqlWhereXML.append(OPERAND_BEGIN);
        getOperando(rightExpresion);
        sqlWhereXML.append(OPERAND_END);

        if (!queueParent.isEmpty() && queueParent.peek()
            .isOpened()) {

            sqlWhereXML.append("<PARENS TYPE=\"OPEN\" COUNT=\"" + Integer.toString(queueParent.peek()
                .getLevel()) + "\"/>");
            queueParent.peek()
                .setOpened(false);

        }

        sqlWhereXML.append("</CLAUSE>");
    }

    private void doLessThan(String parent, Expression expressionMain) {
        if (parent.compareTo(WHERE) == 0 || parent.compareTo("AND") == 0 || parent.compareTo("OR") == 0) {

            sqlWhereXML.append("<CLAUSE TYPE=\"" + parent + "\">");
        }

        Column leftExpresion = (Column)((MinorThan)expressionMain).getLeftExpression();

        String tableToUse = this.tableOrViewName;

        if (leftExpresion.getTable() != null && !leftExpresion.getTable()
            .getName()
            .isEmpty()) {
            tableToUse = leftExpresion.getTable()
                .getName();
        }

        Expression rightExpresion = ((MinorThan)expressionMain).getRightExpression();

        sqlWhereXML.append("<COLUMN NAME=\"\" TABLE=\"" + tableToUse + "\" INSTANCE=\"\" ALIAS=\"" + leftExpresion.getColumnName() + "\"/>");
        sqlWhereXML.append("<OPERATOR TYPE=\"LT\"/>");
        sqlWhereXML.append(OPERAND_BEGIN);
        getOperando(rightExpresion);
        sqlWhereXML.append(OPERAND_END);

        if (!queueParent.isEmpty() && queueParent.peek()
            .isOpened()) {

            sqlWhereXML.append("<PARENS TYPE=\"OPEN\" COUNT=\"" + Integer.toString(queueParent.peek()
                .getLevel()) + "\"/>");
            queueParent.peek()
                .setOpened(false);

        }

        sqlWhereXML.append("</CLAUSE>");
    }

    private void doGreaterThanOrEquals(String parent, Expression expressionMain) {
        if (parent.compareTo(WHERE) == 0 || parent.compareTo("AND") == 0 || parent.compareTo("OR") == 0) {

            sqlWhereXML.append("<CLAUSE TYPE=\"" + parent + "\">");
        }

        Column leftExpresion = (Column)((GreaterThanEquals)expressionMain).getLeftExpression();

        String tableToUse = this.tableOrViewName;

        if (leftExpresion.getTable() != null && !leftExpresion.getTable()
            .getName()
            .isEmpty()) {
            tableToUse = leftExpresion.getTable()
                .getName();
        }

        Expression rightExpresion = ((GreaterThanEquals)expressionMain).getRightExpression();

        sqlWhereXML.append("<COLUMN NAME=\"\" TABLE=\"" + tableToUse + "\" INSTANCE=\"\" ALIAS=\"" + leftExpresion.getColumnName() + "\"/>");
        sqlWhereXML.append("<OPERATOR TYPE=\"GE\"/>");
        sqlWhereXML.append(OPERAND_BEGIN);
        getOperando(rightExpresion);
        sqlWhereXML.append(OPERAND_END);

        if (!queueParent.isEmpty() && queueParent.peek()
            .isOpened()) {

            sqlWhereXML.append("<PARENS TYPE=\"OPEN\" COUNT=\"" + Integer.toString(queueParent.peek()
                .getLevel()) + "\"/>");
            queueParent.peek()
                .setOpened(false);

        }

        sqlWhereXML.append("</CLAUSE>");
    }

    private void doGreaterThan(String parent, Expression expressionMain) {
        if (parent.compareTo(WHERE) == 0 || parent.compareTo("AND") == 0 || parent.compareTo("OR") == 0) {

            sqlWhereXML.append("<CLAUSE TYPE=\"" + parent + "\">");
        }

        Column leftExpresion = (Column)((GreaterThan)expressionMain).getLeftExpression();

        String tableToUse = this.tableOrViewName;

        if (leftExpresion.getTable() != null && !leftExpresion.getTable()
            .getName()
            .isEmpty()) {
            tableToUse = leftExpresion.getTable()
                .getName();
        }

        Expression rightExpresion = ((GreaterThan)expressionMain).getRightExpression();

        sqlWhereXML.append("<COLUMN NAME=\"\" TABLE=\"" + tableToUse + "\" INSTANCE=\"\" ALIAS=\"" + leftExpresion.getColumnName() + "\"/>");
        sqlWhereXML.append("<OPERATOR TYPE=\"GT\"/>");
        sqlWhereXML.append(OPERAND_BEGIN);
        getOperando(rightExpresion);
        sqlWhereXML.append(OPERAND_END);

        if (!queueParent.isEmpty() && queueParent.peek()
            .isOpened()) {

            sqlWhereXML.append("<PARENS TYPE=\"OPEN\" COUNT=\"" + Integer.toString(queueParent.peek()
                .getLevel()) + "\"/>");
            queueParent.peek()
                .setOpened(false);

        }

        sqlWhereXML.append("</CLAUSE>");
    }

    private void doNotEqualsTo(String parent, Expression expressionMain) {
        if (parent.compareTo(WHERE) == 0 || parent.compareTo("AND") == 0 || parent.compareTo("OR") == 0) {

            sqlWhereXML.append("<CLAUSE TYPE=\"" + parent + "\">");
        }

        Column leftExpresion = (Column)((NotEqualsTo)expressionMain).getLeftExpression();

        String tableToUse = this.tableOrViewName;

        if (leftExpresion.getTable() != null && !leftExpresion.getTable()
            .getName()
            .isEmpty()) {
            tableToUse = leftExpresion.getTable()
                .getName();
        }

        Expression rightExpresion = ((NotEqualsTo)expressionMain).getRightExpression();

        sqlWhereXML.append("<COLUMN NAME=\"\" TABLE=\"" + tableToUse + "\" INSTANCE=\"\" ALIAS=\"" + leftExpresion.getColumnName() + "\"/>");
        sqlWhereXML.append("<OPERATOR TYPE=\"NE\"/>");
        sqlWhereXML.append(OPERAND_BEGIN);
        getOperando(rightExpresion);
        sqlWhereXML.append(OPERAND_END);

        if (!queueParent.isEmpty() && queueParent.peek()
            .isOpened()) {

            sqlWhereXML.append("<PARENS TYPE=\"OPEN\" COUNT=\"" + Integer.toString(queueParent.peek()
                .getLevel()) + "\"/>");
            queueParent.peek()
                .setOpened(false);

        }

        sqlWhereXML.append("</CLAUSE>");
    }

    private void doEqualsTo(String parent, Expression expressionMain) {
        if (parent.compareTo(WHERE) == 0 || parent.compareTo("AND") == 0 || parent.compareTo("OR") == 0) {

            sqlWhereXML.append("<CLAUSE TYPE=\"" + parent + "\">");
        }

        Column leftExpresion = (Column)((EqualsTo)expressionMain).getLeftExpression();

        String tableToUse = this.tableOrViewName;

        Table tableLeftE = leftExpresion.getTable();

        if (tableLeftE != null && tableLeftE.getName() != null && !tableLeftE.getName()
            .isEmpty()) {
            tableToUse = leftExpresion.getTable()
                .getName();
        }

        Expression rightExpresion = ((EqualsTo)expressionMain).getRightExpression();

        sqlWhereXML.append("<COLUMN NAME=\"\" TABLE=\"" + tableToUse + "\" INSTANCE=\"\" ALIAS=\"" + leftExpresion.getColumnName() + "\"/>");
        sqlWhereXML.append("<OPERATOR TYPE=\"EQ\"/>");
        sqlWhereXML.append(OPERAND_BEGIN);
        getOperando(rightExpresion);
        sqlWhereXML.append(OPERAND_END);

        if (!queueParent.isEmpty() && queueParent.peek()
            .isOpened()) {

            sqlWhereXML.append("<PARENS TYPE=\"OPEN\" COUNT=\"" + Integer.toString(queueParent.peek()
                .getLevel()) + "\"/>");
            queueParent.peek()
                .setOpened(false);

        }

        sqlWhereXML.append("</CLAUSE>");
    }

    private void doParenthesis(String parent, Expression expressionMain) throws ClassNotFoundException {
        parentLevel++;

        queueParent.add(new TransformSQSentenceLtoXMLSenteceParenthesis(parentLevel));

        evaluateSQLWhere(parent, ((Parenthesis)expressionMain).getExpression());

        int position = sqlWhereXML.lastIndexOf("</CLAUSE>");
        sqlWhereXML.replace(position, position + 9, "<PARENS TYPE=\"CLOSE\" COUNT=\"" + Integer.toString(queueParent.peek()
            .getLevel()) + "\"/></CLAUSE>");

        queueParent.remove();

        parentLevel--;
    }

    private void getOperando(Expression expressionMain) {

        getOperando(expressionMain, "LITERAL");

    }

    private void getOperando(Expression expressionMain, String literal) {

        if (expressionMain instanceof Column) {

            Column rightExpresion = (Column)expressionMain;

            String tableToUse = this.tableOrViewName;

            if (rightExpresion.getTable() != null && !rightExpresion.getTable()
                .getName()
                .isEmpty()) {
                tableToUse = rightExpresion.getTable()
                    .getName();
            }

            sqlWhereXML.append("<COLUMN NAME=\"\" TABLE=\"" + tableToUse + "\" INSTANCE=\"0\" ALIAS=\"" + rightExpresion.getColumnName() + "\"/>");
        }

        if (expressionMain instanceof LongValue) {
            LongValue rightExpresion = (LongValue)expressionMain;
            sqlWhereXML.append("<" + literal + " VALUE=\"" + rightExpresion.getStringValue() + "\"/>");
        }

        if (expressionMain instanceof DateValue) {
            DateValue rightExpresion = (DateValue)expressionMain;
            sqlWhereXML.append("<" + literal + " VALUE=\"" + rightExpresion.toString() + "\"/>");

        }

        if (expressionMain instanceof StringValue) {
            StringValue rightExpresion = (StringValue)expressionMain;
            sqlWhereXML.append("<" + literal + " VALUE=\"" + rightExpresion.getValue() + "\"/>");
        }

        if (expressionMain instanceof TimestampValue) {
            TimestampValue rightExpresion = (TimestampValue)expressionMain;
            sqlWhereXML.append("<" + literal + " VALUE=\"" + rightExpresion.toString() + "\"/>");

        }

        if (expressionMain instanceof TimeValue) {
            TimeValue rightExpresion = (TimeValue)expressionMain;
            sqlWhereXML.append("<" + literal + " VALUE=\"" + rightExpresion.toString() + "\"/>");
        }

        if (expressionMain instanceof NullValue) {

            sqlWhereXML.append("<" + literal + " VALUE=\"\"/>");

        }

    }

    public String getColumnSelection() {
        return columnSelection;
    }

    public String getWhereSelection() {
        return whereSelection;
    }

    public String getSequenceSelection() {
        return sequenceSelection;
    }
    
    
    private class TransformSQSentenceLtoXMLSenteceColumn {
    
        private String columnName;

        public TransformSQSentenceLtoXMLSenteceColumn() {
            // Default Constructor
        }

        public TransformSQSentenceLtoXMLSenteceColumn(String columnName, boolean columnOrderAsc) {
            this.columnName = columnName;
        }

        public String getColumnName() {
            return columnName;
        }
    
    }
    
    private class TransformSQSentenceLtoXMLSenteceParenthesis {
    
        private int level;
        private boolean opened;

        public TransformSQSentenceLtoXMLSenteceParenthesis(int level) {
            this.level = level;
            this.opened = true;
        }

        public int getLevel() {
            return level;
        }

        public boolean isOpened() {
            return opened;
        }

        public void setOpened(boolean opened) {
            this.opened = opened;
        }

        }
    
}
