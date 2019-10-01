/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * https://www.programcreek.com/java-api-examples/?code=pcgomes/javaparser2jctree/javaparser2jctree-master/src/main/java/stave/javaparser/visitor/ASTDumpVisitor.java#
 */
package com.atina.buildjdebundle.metadata;

import static com.github.javaparser.PositionUtils.sortByBeginPosition;
import static com.github.javaparser.ast.internal.Utils.isNullOrEmpty;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.TypeParameter;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EmptyMemberDeclaration;
import com.github.javaparser.ast.body.EmptyTypeDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.MultiTypeParameter;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.IntegerLiteralMinValueExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.LongLiteralMinValueExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.QualifiedNameExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntryStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.TypeDeclarationStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.VoidVisitor;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class DFDumpVisitor   implements VoidVisitor<Object>{
    
    private static final Logger logger = LoggerFactory.getLogger(DFDumpVisitor.class);
    
public final SourcePrinter printer = new SourcePrinter();
    public boolean printComments; 

    public DFDumpVisitor() {
        this(true);
    }

    public DFDumpVisitor(boolean printComments) {
        this.printComments = printComments;
    }
 
    public String getSource() {
        return printer.getSource();
    }

    public void printModifiers(final int modifiers) {
        if (ModifierSet.isPrivate(modifiers)) {
            logger.debug("public ");
        }
        if (ModifierSet.isProtected(modifiers)) {
            logger.debug("protected ");
        }
        if (ModifierSet.isPublic(modifiers)) {
            logger.debug("public ");
        }
        if (ModifierSet.isAbstract(modifiers)) {
            logger.debug("abstract ");
        }
        if (ModifierSet.isStatic(modifiers)) {
            logger.debug("static ");
        }
        if (ModifierSet.isFinal(modifiers)) {
            logger.debug("final ");
        }
        if (ModifierSet.isNative(modifiers)) {
            logger.debug("native ");
        }
        if (ModifierSet.isStrictfp(modifiers)) {
            logger.debug("strictfp ");
        }
        if (ModifierSet.isSynchronized(modifiers)) {
            logger.debug("synchronized ");
        }
        if (ModifierSet.isTransient(modifiers)) {
            logger.debug("transient ");
        }
        if (ModifierSet.isVolatile(modifiers)) {
            logger.debug("volatile ");
        }
    }

    public void printMembers(final List<BodyDeclaration> members, final Object arg) {
        for (final BodyDeclaration member : members) {
            logger.debug(">>");
            member.accept(this, arg);
            logger.debug(">>");
        }
    }

    public void printMemberAnnotations(final List<AnnotationExpr> annotations, final Object arg) {
        if (!isNullOrEmpty(annotations)) {
            for (final AnnotationExpr a : annotations) {
                a.accept(this, arg);
                logger.debug(">>");
            }
        }
    }

    public void printAnnotations(final List<AnnotationExpr> annotations, final Object arg) {
        if (!isNullOrEmpty(annotations)) {
            for (final AnnotationExpr a : annotations) {
                a.accept(this, arg);
                logger.debug(" ");
            }
        }
    }

    public void printTypeArgs(final List<Type> args, final Object arg) {
        if (!isNullOrEmpty(args)) {
            logger.debug("<");
            for (final Iterator<Type> i = args.iterator(); i.hasNext(); ) {
                final Type t = i.next();
                t.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
            logger.debug(">");
        }
    }

    public void printTypeParameters(final List<TypeParameter> args, final Object arg) {
        if (!isNullOrEmpty(args)) {
            logger.debug("<");
            for (final Iterator<TypeParameter> i = args.iterator(); i.hasNext(); ) {
                final TypeParameter t = i.next();
                t.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
            logger.debug(">");
        }
    }

    public void printArguments(final List<Expression> args, final Object arg) {
        logger.debug("(");
        if (!isNullOrEmpty(args)) {
            for (final Iterator<Expression> i = args.iterator(); i.hasNext(); ) {
                final Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }
        logger.debug(")");
    }

    public void printJavadoc(final JavadocComment javadoc, final Object arg) {
        if (javadoc != null) {
            javadoc.accept(this, arg);
        }
    }

    public void printJavaComment(final Comment javacomment, final Object arg) {
        if (javacomment != null) {
            javacomment.accept(this, arg);
        }
    }

    @Override
    public void visit(final CompilationUnit n, final Object arg) {
        logger.debug(" ******************* CompilationUnit **********************************************");
        printJavaComment(n.getComment(), arg);

        if (n.getPackage() != null) {
            n.getPackage().accept(this, arg);
        }

        if (n.getImports() != null) {
            for (final ImportDeclaration i : n.getImports()) {
                i.accept(this, arg);
            }
            logger.debug(">>");
        }

        if (n.getTypes() != null) {
            for (final Iterator<TypeDeclaration> i = n.getTypes().iterator(); i.hasNext(); ) {
                i.next().accept(this, arg);
                logger.debug(">>");
                if (i.hasNext()) {
                    logger.debug(">>");
                }
            }
        }

        printOrphanCommentsEnding(n);
    }

    @Override
    public void visit(final PackageDeclaration n, final Object arg) {
        logger.debug(" ******************* PackageDeclaration **********************************************");
        printJavaComment(n.getComment(), arg);
        printAnnotations(n.getAnnotations(), arg);
        logger.debug("package ");
        n.getName().accept(this, arg);
        logger.debug(";");
        logger.debug(">>");

        printOrphanCommentsEnding(n);
    }

    @Override
    public void visit(final NameExpr n, final Object arg) {
        logger.debug(" ******************* NameExpr **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        logger.debug(n.getName());

        printOrphanCommentsEnding(n);
    }

    @Override
    public void visit(final QualifiedNameExpr n, final Object arg) {
         logger.debug(" ******************* QualifiedNameExpr **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        n.getQualifier().accept(this, arg);
        logger.debug(".");
        logger.debug(n.getName());

        printOrphanCommentsEnding(n);
    }

    @Override
    public void visit(final ImportDeclaration n, final Object arg) {
        logger.debug(" ******************* ImportDeclaration **********************************************");
        printJavaComment(n.getComment(), arg);
        logger.debug("import ");
        if (n.isStatic()) {
            logger.debug("static ");
        }
        n.getName().accept(this, arg);
        if (n.isAsterisk()) {
            logger.debug(".*");
        }
        logger.debug(";");

        printOrphanCommentsEnding(n);
    }

    @Override
    public void visit(final ClassOrInterfaceDeclaration n, final Object arg) {
        logger.debug(" ******************* ClassOrInterfaceDeclaration **********************************************");
        logger.debug("ClassOrInterfaceDeclaration");
        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        if (n.isInterface()) {
            logger.debug("interface ");
        } else {
            logger.debug("class ");
        }

        logger.debug(n.getName());

        printTypeParameters(n.getTypeParameters(), arg);

        if (!isNullOrEmpty(n.getExtends())) {
            logger.debug(" extends ");
            for (final Iterator<ClassOrInterfaceType> i = n.getExtends().iterator(); i.hasNext(); ) {
                final ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }

        if (!isNullOrEmpty(n.getImplements())) {
            logger.debug(" implements ");
            for (final Iterator<ClassOrInterfaceType> i = n.getImplements().iterator(); i.hasNext(); ) {
                final ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }

        logger.debug(" {");
        printer.indent();
        if (!isNullOrEmpty(n.getMembers())) {
            printMembers(n.getMembers(), arg);
        }

        printOrphanCommentsEnding(n);

        printer.unindent();
        logger.debug("}");
    }

    @Override
    public void visit(final EmptyTypeDeclaration n, final Object arg) {
        logger.debug(" ******************* EmptyTypeDeclaration **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        logger.debug(";");

        printOrphanCommentsEnding(n);
    }

    @Override
    public void visit(final JavadocComment n, final Object arg) {
        logger.debug(" ******************* JavadocComment **********************************************");
        logger.debug("/**");
        logger.debug(n.getContent());
        logger.debug("*/");
    }

    @Override
    public void visit(final ClassOrInterfaceType n, final Object arg) {
        logger.debug(" ******************* ClassOrInterfaceType **********************************************");
        logger.debug("ClassOrInterfaceType");
        printJavaComment(n.getComment(), arg);

        if (n.getAnnotations() != null) {
            for (AnnotationExpr ae : n.getAnnotations()) {
                ae.accept(this, arg);
                logger.debug(" ");
            }
        }

        if (n.getScope() != null) {
            n.getScope().accept(this, arg);
            logger.debug(".");
        }
        logger.debug(n.getName());
        printTypeArgs(n.getTypeArgs(), arg);
    }

    @Override
    public void visit(final TypeParameter n, final Object arg) {
         logger.debug(" ******************* TypeParameter **********************************************");
        logger.debug("TypeParameter");
        printJavaComment(n.getComment(), arg);
        if (n.getAnnotations() != null) {
            for (AnnotationExpr ann : n.getAnnotations()) {
                ann.accept(this, arg);
                logger.debug(" ");
            }
        }
        logger.debug(n.getName());
        if (n.getTypeBound() != null) {
            logger.debug(" extends ");
            for (final Iterator<ClassOrInterfaceType> i = n.getTypeBound().iterator(); i.hasNext(); ) {
                final ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(" & ");
                }
            }
        }
    }

    @Override
    public void visit(final PrimitiveType n, final Object arg) {
        logger.debug(" ******************* PrimitiveType **********************************************");
        logger.debug("PrimitiveType");
        printJavaComment(n.getComment(), arg);
        if (n.getAnnotations() != null) {
            for (AnnotationExpr ae : n.getAnnotations()) {
                ae.accept(this, arg);
                logger.debug(" ");
            }
        }
        switch (n.getType()) {
            case Boolean:
                logger.debug("boolean");
                break;
            case Byte:
                logger.debug("byte");
                break;
            case Char:
                logger.debug("char");
                break;
            case Double:
                logger.debug("double");
                break;
            case Float:
                logger.debug("float");
                break;
            case Int:
                logger.debug("int");
                break;
            case Long:
                logger.debug("long");
                break;
            case Short:
                logger.debug("short");
                break;
        }
    }

    @Override
    public void visit(final ReferenceType n, final Object arg) {
         logger.debug(" ******************* ReferenceType **********************************************");
        logger.debug("ReferenceType");
        printJavaComment(n.getComment(), arg);
        if (n.getAnnotations() != null) {
            for (AnnotationExpr ae : n.getAnnotations()) {
                ae.accept(this, arg);
                logger.debug(" ");
            }
        }
        n.getType().accept(this, arg);
        List<List<AnnotationExpr>> arraysAnnotations = n.getArraysAnnotations();
        for (int i = 0; i < n.getArrayCount(); i++) {
            if (arraysAnnotations != null && i < arraysAnnotations.size()) {
                List<AnnotationExpr> annotations = arraysAnnotations.get(i);
                if (annotations != null) {
                    for (AnnotationExpr ae : annotations) {
                        logger.debug(" ");
                        ae.accept(this, arg);

                    }
                }
            }
            logger.debug("[]");
        }
    }

    @Override
    public void visit(final WildcardType n, final Object arg) {
        logger.debug(" ******************* WildcardType **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        if (n.getAnnotations() != null) {
            for (AnnotationExpr ae : n.getAnnotations()) {
                logger.debug(" ");
                ae.accept(this, arg);
            }
        }
        logger.debug("?");
        if (n.getExtends() != null) {
            logger.debug(" extends ");
            n.getExtends().accept(this, arg);
        }
        if (n.getSuper() != null) {
            logger.debug(" super ");
            n.getSuper().accept(this, arg);
        }
    }

    @Override
    public void visit(final UnknownType n, final Object arg) {
        logger.debug(" ******************* UnknownType **********************************************");
        logger.debug("");
        // Nothing to dump
    }

    @Override
    public void visit(final FieldDeclaration n, final Object arg) {
        logger.debug(" ******************* FieldDeclaration **********************************************");
        logger.debug("");
        printOrphanCommentsBeforeThisChildNode(n);

        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());
        n.getType().accept(this, arg);

        logger.debug(" ");
        for (final Iterator<VariableDeclarator> i = n.getVariables().iterator(); i.hasNext(); ) {
            final VariableDeclarator var = i.next();
            var.accept(this, arg);
            if (i.hasNext()) {
                logger.debug(", ");
            }
        }

        logger.debug(";");
    }

    @Override
    public void visit(final VariableDeclarator n, final Object arg) {
        logger.debug(" ******************* VariableDeclarator **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        n.getId().accept(this, arg);
        if (n.getInit() != null) {
            logger.debug(" = ");
            n.getInit().accept(this, arg);
        }
    }

    @Override
    public void visit(final VariableDeclaratorId n, final Object arg) {
        logger.debug(" ******************* VariableDeclaratorId **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        logger.debug(n.getName());
        for (int i = 0; i < n.getArrayCount(); i++) {
            logger.debug("[]");
        }
    }

    @Override
    public void visit(final ArrayInitializerExpr n, final Object arg) {
        logger.debug(" ******************* ArrayInitializerExpr **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        logger.debug("{");
        if (n.getValues() != null) {
            logger.debug(" ");
            for (final Iterator<Expression> i = n.getValues().iterator(); i.hasNext(); ) {
                final Expression expr = i.next();
                expr.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
            logger.debug(" ");
        }
        logger.debug("}");
    }

    @Override
    public void visit(final VoidType n, final Object arg) {
        logger.debug(" ******************* VoidType **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        logger.debug("void");
    }

    @Override
    public void visit(final ArrayAccessExpr n, final Object arg) {
        logger.debug(" ******************* ArrayAccessExpr **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        n.getName().accept(this, arg);
        logger.debug("[");
        n.getIndex().accept(this, arg);
        logger.debug("]");
    }

    @Override
    public void visit(final ArrayCreationExpr n, final Object arg) {
        logger.debug(" ******************* ArrayCreationExpr **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        logger.debug("new ");
        n.getType().accept(this, arg);
        List<List<AnnotationExpr>> arraysAnnotations = n.getArraysAnnotations();
        if (n.getDimensions() != null) {
            int j = 0;
            for (final Expression dim : n.getDimensions()) {

                if (arraysAnnotations != null && j < arraysAnnotations.size()) {
                    List<AnnotationExpr> annotations = arraysAnnotations.get(j);
                    if (annotations != null) {
                        for (AnnotationExpr ae : annotations) {
                            logger.debug(" ");
                            ae.accept(this, arg);
                        }
                    }
                }
                logger.debug("[");
                dim.accept(this, arg);
                logger.debug("]");
                j++;
            }
            for (int i = 0; i < n.getArrayCount(); i++) {
                if (arraysAnnotations != null && i < arraysAnnotations.size()) {

                    List<AnnotationExpr> annotations = arraysAnnotations.get(i);
                    if (annotations != null) {
                        for (AnnotationExpr ae : annotations) {
                            logger.debug(" ");
                            ae.accept(this, arg);

                        }
                    }
                }
                logger.debug("[]");
            }

        } else {
            for (int i = 0; i < n.getArrayCount(); i++) {
                if (arraysAnnotations != null && i < arraysAnnotations.size()) {
                    List<AnnotationExpr> annotations = arraysAnnotations.get(i);
                    if (annotations != null) {
                        for (AnnotationExpr ae : annotations) {
                            ae.accept(this, arg);
                            logger.debug(" ");
                        }
                    }
                }
                logger.debug("[]");
            }
            logger.debug(" ");
            n.getInitializer().accept(this, arg);
        }
    }

    @Override
    public void visit(final AssignExpr n, final Object arg) {
        logger.debug(" ******************* AssignExpr **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        n.getTarget().accept(this, arg);
        logger.debug(" ");
        switch (n.getOperator()) {
            case assign:
                logger.debug("=");
                break;
            case and:
                logger.debug("&=");
                break;
            case or:
                logger.debug("|=");
                break;
            case xor:
                logger.debug("^=");
                break;
            case plus:
                logger.debug("+=");
                break;
            case minus:
                logger.debug("-=");
                break;
            case rem:
                logger.debug("%=");
                break;
            case slash:
                logger.debug("/=");
                break;
            case star:
                logger.debug("*=");
                break;
            case lShift:
                logger.debug("<<=");
                break;
            case rSignedShift:
                logger.debug(">>=");
                break;
            case rUnsignedShift:
                logger.debug(">>>=");
                break;
        }
        logger.debug(" ");
        n.getValue().accept(this, arg);
    }

    @Override
    public void visit(final BinaryExpr n, final Object arg) {
        logger.debug(" ******************* BinaryExpr **********************************************");
        logger.debug("");
        printJavaComment(n.getComment(), arg);
        n.getLeft().accept(this, arg);
        logger.debug(" ");
        switch (n.getOperator()) {
            case or:
                logger.debug("||");
                break;
            case and:
                logger.debug("&&");
                break;
            case binOr:
                logger.debug("|");
                break;
            case binAnd:
                logger.debug("&");
                break;
            case xor:
                logger.debug("^");
                break;
            case equals:
                logger.debug("==");
                break;
            case notEquals:
                logger.debug("!=");
                break;
            case less:
                logger.debug("<");
                break;
            case greater:
                logger.debug(">");
                break;
            case lessEquals:
                logger.debug("<=");
                break;
            case greaterEquals:
                logger.debug(">=");
                break;
            case lShift:
                logger.debug("<<");
                break;
            case rSignedShift:
                logger.debug(">>");
                break;
            case rUnsignedShift:
                logger.debug(">>>");
                break;
            case plus:
                logger.debug("+");
                break;
            case minus:
                logger.debug("-");
                break;
            case times:
                logger.debug("*");
                break;
            case divide:
                logger.debug("/");
                break;
            case remainder:
                logger.debug("%");
                break;
        }
        logger.debug(" ");
        n.getRight().accept(this, arg);
    }

    @Override
    public void visit(final CastExpr n, final Object arg) {
        logger.debug("CastExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug("(");
        n.getType().accept(this, arg);
        logger.debug(") ");
        n.getExpr().accept(this, arg);
    }

    @Override
    public void visit(final ClassExpr n, final Object arg) {
        logger.debug("ClassExpr");
        printJavaComment(n.getComment(), arg);
        n.getType().accept(this, arg);
        logger.debug(".class");
    }

    @Override
    public void visit(final ConditionalExpr n, final Object arg) {
        logger.debug("ConditionalExpr");
        printJavaComment(n.getComment(), arg);
        n.getCondition().accept(this, arg);
        logger.debug(" ? ");
        n.getThenExpr().accept(this, arg);
        logger.debug(" : ");
        n.getElseExpr().accept(this, arg);
    }

    @Override
    public void visit(final EnclosedExpr n, final Object arg) {
        logger.debug("EnclosedExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug("(");
        if (n.getInner() != null) {
            n.getInner().accept(this, arg);
        }
        logger.debug(")");
    }

    @Override
    public void visit(final FieldAccessExpr n, final Object arg) {
        logger.debug("FieldAccessExpr");
        printJavaComment(n.getComment(), arg);
        n.getScope().accept(this, arg);
        logger.debug(".");
        logger.debug(n.getField());
    }

    @Override
    public void visit(final InstanceOfExpr n, final Object arg) {
        logger.debug("InstanceOfExpr");
        printJavaComment(n.getComment(), arg);
        n.getExpr().accept(this, arg);
        logger.debug(" instanceof ");
        n.getType().accept(this, arg);
    }

    @Override
    public void visit(final CharLiteralExpr n, final Object arg) {
        logger.debug("CharLiteralExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug("'");
        logger.debug(n.getValue());
        logger.debug("'");
    }

    @Override
    public void visit(final DoubleLiteralExpr n, final Object arg) {
        logger.debug("DoubleLiteralExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug(n.getValue());
    }

    @Override
    public void visit(final IntegerLiteralExpr n, final Object arg) {
        logger.debug("IntegerLiteralExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug(n.getValue());
    }

    @Override
    public void visit(final LongLiteralExpr n, final Object arg) {
        logger.debug("LongLiteralExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug(n.getValue());
    }

    @Override
    public void visit(final IntegerLiteralMinValueExpr n, final Object arg) {
        logger.debug("IntegerLiteralMinValueExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug(n.getValue());
    }

    @Override
    public void visit(final LongLiteralMinValueExpr n, final Object arg) {
        logger.debug("LongLiteralMinValueExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug(n.getValue());
    }

    @Override
    public void visit(final StringLiteralExpr n, final Object arg) {
        logger.debug("StringLiteralExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug("\"");
        logger.debug(n.getValue());
        logger.debug("\"");
    }

    @Override
    public void visit(final BooleanLiteralExpr n, final Object arg) {
        logger.debug("BooleanLiteralExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug(String.valueOf(n.getValue()));
    }

    @Override
    public void visit(final NullLiteralExpr n, final Object arg) {
        logger.debug("NullLiteralExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug("null");
    }

    @Override
    public void visit(final ThisExpr n, final Object arg) {
        logger.debug("ThisExpr");
        printJavaComment(n.getComment(), arg);
        if (n.getClassExpr() != null) {
            n.getClassExpr().accept(this, arg);
            logger.debug(".");
        }
        logger.debug("this");
    }

    @Override
    public void visit(final SuperExpr n, final Object arg) {
        logger.debug("SuperExpr");
        printJavaComment(n.getComment(), arg);
        if (n.getClassExpr() != null) {
            n.getClassExpr().accept(this, arg);
            logger.debug(".");
        }
        logger.debug("super");
    }

    @Override
    public void visit(final MethodCallExpr n, final Object arg) {
        logger.debug("MethodCallExpr");
        printJavaComment(n.getComment(), arg);
        if (n.getScope() != null) {
            n.getScope().accept(this, arg);
            logger.debug(".");
        }
        printTypeArgs(n.getTypeArgs(), arg);
        logger.debug(n.getName());
        printArguments(n.getArgs(), arg);
    }

    @Override
    public void visit(final ObjectCreationExpr n, final Object arg) {
        logger.debug("ObjectCreationExpr");
        printJavaComment(n.getComment(), arg);
        if (n.getScope() != null) {
            n.getScope().accept(this, arg);
            logger.debug(".");
        }

        logger.debug("new ");

        printTypeArgs(n.getTypeArgs(), arg);
        logger.debug(" ");

        n.getType().accept(this, arg);

        printArguments(n.getArgs(), arg);

        if (n.getAnonymousClassBody() != null) {
            logger.debug(" {");
            printer.indent();
            printMembers(n.getAnonymousClassBody(), arg);
            printer.unindent();
            logger.debug("}");
        }
    }

    @Override
    public void visit(final UnaryExpr n, final Object arg) {
        logger.debug("UnaryExpr");
        printJavaComment(n.getComment(), arg);
        switch (n.getOperator()) {
            case positive:
                logger.debug("+");
                break;
            case negative:
                logger.debug("-");
                break;
            case inverse:
                logger.debug("~");
                break;
            case not:
                logger.debug("!");
                break;
            case preIncrement:
                logger.debug("++");
                break;
            case preDecrement:
                logger.debug("--");
                break;
            default:
        }

        n.getExpr().accept(this, arg);

        switch (n.getOperator()) {
            case posIncrement:
                logger.debug("++");
                break;
            case posDecrement:
                logger.debug("--");
                break;
            default:
        }
    }

    @Override
    public void visit(final ConstructorDeclaration n, final Object arg) {
        logger.debug("ConstructorDeclaration");
        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        printTypeParameters(n.getTypeParameters(), arg);
        if (n.getTypeParameters() != null) {
            logger.debug(" ");
        }
        logger.debug(n.getName());

        logger.debug("(");
        if (n.getParameters() != null) {
            for (final Iterator<Parameter> i = n.getParameters().iterator(); i.hasNext(); ) {
                final Parameter p = i.next();
                p.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }
        logger.debug(")");

        if (!isNullOrEmpty(n.getThrows())) {
            logger.debug(" throws ");
            for (final Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext(); ) {
                final NameExpr name = i.next();
                name.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }
        logger.debug(" ");
        n.getBlock().accept(this, arg);
    }

    @Override
    public void visit(final MethodDeclaration n, final Object arg) {
        logger.debug("MethodDeclaration");
        printOrphanCommentsBeforeThisChildNode(n);

        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());
        if (n.isDefault()) {
            logger.debug("default ");
        }
        printTypeParameters(n.getTypeParameters(), arg);
        if (n.getTypeParameters() != null) {
            logger.debug(" ");
        }

        n.getType().accept(this, arg);
        logger.debug(" ");
        logger.debug(n.getName());

        logger.debug("(");
        if (n.getParameters() != null) {
            for (final Iterator<Parameter> i = n.getParameters().iterator(); i.hasNext(); ) {
                final Parameter p = i.next();
                p.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }
        logger.debug(")");

        for (int i = 0; i < n.getArrayCount(); i++) {
            logger.debug("[]");
        }

        if (!isNullOrEmpty(n.getThrows())) {
            logger.debug(" throws ");
            for (final Iterator<NameExpr> i = n.getThrows().iterator(); i.hasNext(); ) {
                final NameExpr name = i.next();
                name.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }
        if (n.getBody() == null) {
            logger.debug(";");
        } else {
            logger.debug(" ");
            n.getBody().accept(this, arg);
        }
    }

    @Override
    public void visit(final Parameter n, final Object arg) {
        logger.debug("Parameter");
        printJavaComment(n.getComment(), arg);
        printAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());
        if (n.getType() != null) {
            n.getType().accept(this, arg);
        }
        if (n.isVarArgs()) {
            logger.debug("...");
        }
        logger.debug(" ");
        n.getId().accept(this, arg);
    }

    @Override
    public void visit(MultiTypeParameter n, Object arg) {
        logger.debug("MultiTypeParameter");
        printAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        Iterator<Type> types = n.getTypes().iterator();
        types.next().accept(this, arg);
        while (types.hasNext()) {
            logger.debug(" | ");
            types.next().accept(this, arg);
        }

        logger.debug(" ");
        n.getId().accept(this, arg);
    }

    @Override
    public void visit(final ExplicitConstructorInvocationStmt n, final Object arg) {
        logger.debug("ExplicitConstructorInvocationStmt");
        printJavaComment(n.getComment(), arg);
        if (n.isThis()) {
            printTypeArgs(n.getTypeArgs(), arg);
            logger.debug("this");
        } else {
            if (n.getExpr() != null) {
                n.getExpr().accept(this, arg);
                logger.debug(".");
            }
            printTypeArgs(n.getTypeArgs(), arg);
            logger.debug("super");
        }
        printArguments(n.getArgs(), arg);
        logger.debug(";");
    }

    @Override
    public void visit(final VariableDeclarationExpr n, final Object arg) {
        logger.debug("VariableDeclarationExpr");
        printJavaComment(n.getComment(), arg);
        printAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        n.getType().accept(this, arg);
        logger.debug(" ");

        for (final Iterator<VariableDeclarator> i = n.getVars().iterator(); i.hasNext(); ) {
            final VariableDeclarator v = i.next();
            v.accept(this, arg);
            if (i.hasNext()) {
                logger.debug(", ");
            }
        }
    }

    @Override
    public void visit(final TypeDeclarationStmt n, final Object arg) {
        logger.debug("TypeDeclarationStmt");
        printJavaComment(n.getComment(), arg);
        n.getTypeDeclaration().accept(this, arg);
    }

    @Override
    public void visit(final AssertStmt n, final Object arg) {
        logger.debug("AssertStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("assert ");
        n.getCheck().accept(this, arg);
        if (n.getMessage() != null) {
            logger.debug(" : ");
            n.getMessage().accept(this, arg);
        }
        logger.debug(";");
    }

    @Override
    public void visit(final BlockStmt n, final Object arg) {
        logger.debug("BlockStmt");
        printOrphanCommentsBeforeThisChildNode(n);
        printJavaComment(n.getComment(), arg);
        logger.debug("{");
        if (n.getStmts() != null) {
            printer.indent();
            for (final Statement s : n.getStmts()) {
                s.accept(this, arg);
                logger.debug(">>");
            }
            printer.unindent();
        }
        logger.debug("}");

    }

    @Override
    public void visit(final LabeledStmt n, final Object arg) {
        logger.debug("LabeledStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug(n.getLabel());
        logger.debug(": ");
        n.getStmt().accept(this, arg);
    }

    @Override
    public void visit(final EmptyStmt n, final Object arg) {
        logger.debug("EmptyStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug(";");
    }

    @Override
    public void visit(final ExpressionStmt n, final Object arg) {
        logger.debug("ExpressionStmt");
        printOrphanCommentsBeforeThisChildNode(n);
        printJavaComment(n.getComment(), arg);
        n.getExpression().accept(this, arg);
        logger.debug(";");
    }

    @Override
    public void visit(final SwitchStmt n, final Object arg) {
        logger.debug("SwitchStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("switch(");
        n.getSelector().accept(this, arg);
        logger.debug(") {");
        if (n.getEntries() != null) {
            printer.indent();
            for (final SwitchEntryStmt e : n.getEntries()) {
                e.accept(this, arg);
            }
            printer.unindent();
        }
        logger.debug("}");

    }

    @Override
    public void visit(final SwitchEntryStmt n, final Object arg) {
        logger.debug("SwitchEntryStmt");
        printJavaComment(n.getComment(), arg);
        if (n.getLabel() != null) {
            logger.debug("case ");
            n.getLabel().accept(this, arg);
            logger.debug(":");
        } else {
            logger.debug("default:");
        }
        logger.debug(">>");
        printer.indent();
        if (n.getStmts() != null) {
            for (final Statement s : n.getStmts()) {
                s.accept(this, arg);
                logger.debug(">>");
            }
        }
        printer.unindent();
    }

    @Override
    public void visit(final BreakStmt n, final Object arg) {
        logger.debug("BreakStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("break");
        if (n.getId() != null) {
            logger.debug(" ");
            logger.debug(n.getId());
        }
        logger.debug(";");
    }

    @Override
    public void visit(final ReturnStmt n, final Object arg) {
        logger.debug("ReturnStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("return");
        if (n.getExpr() != null) {
            logger.debug(" ");
            n.getExpr().accept(this, arg);
        }
        logger.debug(";");
    }

    @Override
    public void visit(final EnumDeclaration n, final Object arg) {
        logger.debug("EnumDeclaration");
        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        logger.debug("enum ");
        logger.debug(n.getName());

        if (n.getImplements() != null) {
            logger.debug(" implements ");
            for (final Iterator<ClassOrInterfaceType> i = n.getImplements().iterator(); i.hasNext(); ) {
                final ClassOrInterfaceType c = i.next();
                c.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }

        logger.debug(" {");
        printer.indent();
        if (n.getEntries() != null) {
            logger.debug(">>");
            for (final Iterator<EnumConstantDeclaration> i = n.getEntries().iterator(); i.hasNext(); ) {
                final EnumConstantDeclaration e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }
        if (n.getMembers() != null) {
            logger.debug(";");
            printMembers(n.getMembers(), arg);
        } else {
            if (n.getEntries() != null) {
                logger.debug(">>");
            }
        }
        printer.unindent();
        logger.debug("}");
    }

    @Override
    public void visit(final EnumConstantDeclaration n, final Object arg) {
        logger.debug("EnumConstantDeclaration");
        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        printMemberAnnotations(n.getAnnotations(), arg);
        logger.debug(n.getName());

        if (n.getArgs() != null) {
            printArguments(n.getArgs(), arg);
        }

        if (n.getClassBody() != null) {
            logger.debug(" {");
            printer.indent();
            printMembers(n.getClassBody(), arg);
            printer.unindent();
            logger.debug("}");
        }
    }

    @Override
    public void visit(final EmptyMemberDeclaration n, final Object arg) {
        logger.debug("EmptyMemberDeclaration");
        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        logger.debug(";");
    }

    @Override
    public void visit(final InitializerDeclaration n, final Object arg) {
        logger.debug("InitializerDeclaration");
        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        if (n.isStatic()) {
            logger.debug("static ");
        }
        n.getBlock().accept(this, arg);
    }

    @Override
    public void visit(final IfStmt n, final Object arg) {
        logger.debug("IfStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("if (");
        n.getCondition().accept(this, arg);
        final boolean thenBlock = n.getThenStmt() instanceof BlockStmt;
        if (thenBlock) // block statement should start on the same line
        {
            logger.debug(") ");
        } else {
            logger.debug(")");
            printer.indent();
        }
        n.getThenStmt().accept(this, arg);
        if (!thenBlock) {
            printer.unindent();
        }
        if (n.getElseStmt() != null) {
            if (thenBlock) {
                logger.debug(" ");
            } else {
                logger.debug(">>");
            }
            final boolean elseIf = n.getElseStmt() instanceof IfStmt;
            final boolean elseBlock = n.getElseStmt() instanceof BlockStmt;
            if (elseIf || elseBlock) // put chained if and start of block statement on a same level
            {
                logger.debug("else ");
            } else {
                logger.debug("else");
                printer.indent();
            }
            n.getElseStmt().accept(this, arg);
            if (!(elseIf || elseBlock)) {
                printer.unindent();
            }
        }
    }

    @Override
    public void visit(final WhileStmt n, final Object arg) {
        logger.debug("WhileStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("while (");
        n.getCondition().accept(this, arg);
        logger.debug(") ");
        n.getBody().accept(this, arg);
    }

    @Override
    public void visit(final ContinueStmt n, final Object arg) {
        logger.debug("ContinueStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("continue");
        if (n.getId() != null) {
            logger.debug(" ");
            logger.debug(n.getId());
        }
        logger.debug(";");
    }

    @Override
    public void visit(final DoStmt n, final Object arg) {
        logger.debug("DoStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("do ");
        n.getBody().accept(this, arg);
        logger.debug(" while (");
        n.getCondition().accept(this, arg);
        logger.debug(");");
    }

    @Override
    public void visit(final ForeachStmt n, final Object arg) {
        logger.debug("ForeachStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("for (");
        n.getVariable().accept(this, arg);
        logger.debug(" : ");
        n.getIterable().accept(this, arg);
        logger.debug(") ");
        n.getBody().accept(this, arg);
    }

    @Override
    public void visit(final ForStmt n, final Object arg) {
        logger.debug("ForStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("for (");
        if (n.getInit() != null) {
            for (final Iterator<Expression> i = n.getInit().iterator(); i.hasNext(); ) {
                final Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }
        logger.debug("; ");
        if (n.getCompare() != null) {
            n.getCompare().accept(this, arg);
        }
        logger.debug("; ");
        if (n.getUpdate() != null) {
            for (final Iterator<Expression> i = n.getUpdate().iterator(); i.hasNext(); ) {
                final Expression e = i.next();
                e.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }
        logger.debug(") ");
        n.getBody().accept(this, arg);
    }

    @Override
    public void visit(final ThrowStmt n, final Object arg) {
        logger.debug("ThrowStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("throw ");
        n.getExpr().accept(this, arg);
        logger.debug(";");
    }

    @Override
    public void visit(final SynchronizedStmt n, final Object arg) {
        logger.debug("SynchronizedStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("synchronized (");
        n.getExpr().accept(this, arg);
        logger.debug(") ");
        n.getBlock().accept(this, arg);
    }

    @Override
    public void visit(final TryStmt n, final Object arg) {
        logger.debug("TryStmt");
        printJavaComment(n.getComment(), arg);
        logger.debug("try ");
        if (!n.getResources().isEmpty()) {
            logger.debug("(");
            Iterator<VariableDeclarationExpr> resources = n.getResources().iterator();
            boolean first = true;
            while (resources.hasNext()) {
                visit(resources.next(), arg);
                if (resources.hasNext()) {
                    logger.debug(";");
                    logger.debug(">>");
                    if (first) {
                        printer.indent();
                    }
                }
                first = false;
            }
            if (n.getResources().size() > 1) {
                printer.unindent();
            }
            logger.debug(") ");
        }
        n.getTryBlock().accept(this, arg);
        if (n.getCatchs() != null) {
            for (final CatchClause c : n.getCatchs()) {
                c.accept(this, arg);
            }
        }
        if (n.getFinallyBlock() != null) {
            logger.debug(" finally ");
            n.getFinallyBlock().accept(this, arg);
        }
    }

    @Override
    public void visit(final CatchClause n, final Object arg) {
        logger.debug("CatchClause");
        printJavaComment(n.getComment(), arg);
        logger.debug(" catch (");
        n.getExcept().accept(this, arg);
        logger.debug(") ");
        n.getCatchBlock().accept(this, arg);

    }

    @Override
    public void visit(final AnnotationDeclaration n, final Object arg) {
        logger.debug("AnnotationDeclaration");
        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        logger.debug("@interface ");
        logger.debug(n.getName());
        logger.debug(" {");
        printer.indent();
        if (n.getMembers() != null) {
            printMembers(n.getMembers(), arg);
        }
        printer.unindent();
        logger.debug("}");
    }

    @Override
    public void visit(final AnnotationMemberDeclaration n, final Object arg) {
        logger.debug("AnnotationMemberDeclaration");
        printJavaComment(n.getComment(), arg);
        printJavadoc(n.getJavaDoc(), arg);
        printMemberAnnotations(n.getAnnotations(), arg);
        printModifiers(n.getModifiers());

        n.getType().accept(this, arg);
        logger.debug(" ");
        logger.debug(n.getName());
        logger.debug("()");
        if (n.getDefaultValue() != null) {
            logger.debug(" default ");
            n.getDefaultValue().accept(this, arg);
        }
        logger.debug(";");
    }

    @Override
    public void visit(final MarkerAnnotationExpr n, final Object arg) {
        logger.debug("MarkerAnnotationExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug("@");
        n.getName().accept(this, arg);
    }

    @Override
    public void visit(final SingleMemberAnnotationExpr n, final Object arg) {
        logger.debug("SingleMemberAnnotationExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug("@");
        n.getName().accept(this, arg);
        logger.debug("(");
        n.getMemberValue().accept(this, arg);
        logger.debug(")");
    }

    @Override
    public void visit(final NormalAnnotationExpr n, final Object arg) {
        logger.debug("NormalAnnotationExpr");
        printJavaComment(n.getComment(), arg);
        logger.debug("@");
        n.getName().accept(this, arg);
        logger.debug("(");
        if (n.getPairs() != null) {
            for (final Iterator<MemberValuePair> i = n.getPairs().iterator(); i.hasNext(); ) {
                final MemberValuePair m = i.next();
                m.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }
        logger.debug(")");
    }

    @Override
    public void visit(final MemberValuePair n, final Object arg) {
        logger.debug("MemberValuePair");
        printJavaComment(n.getComment(), arg);
        logger.debug(n.getName());
        logger.debug(" = ");
        n.getValue().accept(this, arg);
    }

    @Override
    public void visit(final LineComment n, final Object arg) {
        logger.debug("LineComment");
        if (!this.printComments) {
            return;
        }
        logger.debug("//");
        String tmp = n.getContent();
        tmp = tmp.replace('r', ' ');
        tmp = tmp.replace('n', ' ');
        logger.debug(tmp);
    }

    @Override
    public void visit(final BlockComment n, final Object arg) {
        logger.debug("BlockComment");
        if (!this.printComments) {
            return;
        }
        logger.debug("/*");
        logger.debug(n.getContent());
        logger.debug("*/");
    }

    @Override
    public void visit(LambdaExpr n, Object arg) {
        logger.debug("LambdaExpr");
        printJavaComment(n.getComment(), arg);

        List<Parameter> parameters = n.getParameters();
        boolean printPar = false;
        printPar = n.isParametersEnclosed();

        if (printPar) {
            logger.debug("(");
        }
        if (parameters != null) {
            for (Iterator<Parameter> i = parameters.iterator(); i.hasNext(); ) {
                Parameter p = i.next();
                p.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
        }
        if (printPar) {
            logger.debug(")");
        }

        logger.debug("->");
        Statement body = n.getBody();
        String bodyStr = body.toString();
        if (body instanceof ExpressionStmt) {
            // removing ';'
            bodyStr = bodyStr.substring(0, bodyStr.length() - 1);
        }
        logger.debug(bodyStr);

    }

    @Override
    public void visit(MethodReferenceExpr n, Object arg) {
        logger.debug("MethodReferenceExpr");
        printJavaComment(n.getComment(), arg);
        Expression scope = n.getScope();
        String identifier = n.getIdentifier();
        if (scope != null) {
            n.getScope().accept(this, arg);
        }

        logger.debug("::");
        if (n.getTypeParameters() != null) {
            logger.debug("<");
            for (Iterator<TypeParameter> i = n.getTypeParameters().iterator(); i
                    .hasNext(); ) {
                TypeParameter p = i.next();
                p.accept(this, arg);
                if (i.hasNext()) {
                    logger.debug(", ");
                }
            }
            logger.debug(">");
        }
        if (identifier != null) {
            logger.debug(identifier);
        }

    }

    @Override
    public void visit(TypeExpr n, Object arg) {
        logger.debug("TypeExpr");
        printJavaComment(n.getComment(), arg);
        if (n.getType() != null) {
            n.getType().accept(this, arg);
        }
    }

    public void printOrphanCommentsBeforeThisChildNode(final Node node) {
        if (node instanceof Comment) {
            return;
        }

        Node parent = node.getParentNode();
        if (parent == null) {
            return;
        }
        List<Node> everything = new LinkedList<Node>();
        everything.addAll(parent.getChildrenNodes());
        sortByBeginPosition(everything);
        int positionOfTheChild = -1;
        for (int i = 0; i < everything.size(); i++) {
            if (everything.get(i) == node) {
                positionOfTheChild = i;
            }
        }
        if (positionOfTheChild == -1) {
            throw new RuntimeException("My index not found!!! " + node);
        }
        int positionOfPreviousChild = -1;
        for (int i = positionOfTheChild - 1; i >= 0 && positionOfPreviousChild == -1; i--) {
            if (!(everything.get(i) instanceof Comment)) {
                positionOfPreviousChild = i;
            }
        }
        for (int i = positionOfPreviousChild + 1; i < positionOfTheChild; i++) {
            Node nodeToPrint = everything.get(i);
            if (!(nodeToPrint instanceof Comment)) {
                throw new RuntimeException("Expected comment, instead " + nodeToPrint.getClass() + ". Position of previous child: " + positionOfPreviousChild + ", position of child " + positionOfTheChild);
            }
            nodeToPrint.accept(this, null);
        }
    }

    public void printOrphanCommentsEnding(final Node node) {
        List<Node> everything = new LinkedList<Node>();
        everything.addAll(node.getChildrenNodes());
        sortByBeginPosition(everything);
        if (everything.size() == 0) {
            return;
        }

        int commentsAtEnd = 0;
        boolean findingComments = true;
        while (findingComments && commentsAtEnd < everything.size()) {
            Node last = everything.get(everything.size() - 1 - commentsAtEnd);
            findingComments = (last instanceof Comment);
            if (findingComments) {
                commentsAtEnd++;
            }
        }
        for (int i = 0; i < commentsAtEnd; i++) {
            everything.get(everything.size() - commentsAtEnd + i).accept(this, null);
        }
    }

    public static class SourcePrinter {

        public final StringBuilder buf = new StringBuilder();
        public int level = 0;
        public boolean indented = false;

        public void indent() {
            level++;
        }

        public void unindent() {
            level--;
        }

        public void makeIndent() {
            for (int i = 0; i < level; i++) {
                buf.append("    ");
            }
        }

        public void print(final String arg) {
            if (!indented) {
                makeIndent();
                indented = true;
            }
            buf.append(arg);
        }

        public void printLn(final String arg) {
            print(arg);
            printLn();
        }

        public void printLn() {
            buf.append("\n");
            indented = false;
        }

        public String getSource() {
            return buf.toString();
        }

        @Override
        public String toString() {
            return getSource();
        }
    }
}
