package com.gman.idea.plugin.concordion.lang.psi;

import com.gman.idea.plugin.concordion.ConcordionVariableUsage;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.gman.idea.plugin.concordion.ConcordionVariableUsage.findDeclaration;

public abstract class ConcordionVariableInternalImpl extends AbstractConcordionPsiElement implements ConcordionVariableInternal {

    public ConcordionVariableInternalImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    protected PsiType determineType() {
        ConcordionVariableUsage declaration = findDeclaration(this);
        return declaration != null ? declaration.determineType() : null;
    }

    @Override
    public boolean isResolvable() {
        return findDeclaration(this) != null;
    }
}
