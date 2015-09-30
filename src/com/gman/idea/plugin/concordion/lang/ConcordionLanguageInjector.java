package com.gman.idea.plugin.concordion.lang;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static com.gman.idea.plugin.concordion.Concordion.*;
import static com.gman.idea.plugin.concordion.ConcordionPsiUtils.setOf;
import static com.intellij.psi.util.PsiTreeUtil.getParentOfType;
import static java.util.Collections.singletonList;

public class ConcordionLanguageInjector implements MultiHostInjector {

    private static final Set<String> CONCORDION_TAGS_FOR_EXPRESSION_INJECTION = setOf(
            "assertEquals", "assert-equals",
            "assertTrue", "assert-true",
            "assertFalse", "assert-false",
            "echo",
            "execute",
            "set",
            "verifyRows", "verify-rows"
    );

    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        if (!isConcordionHtmlSpec(context.getContainingFile())
                || !(context instanceof XmlAttributeValue)) {
            return;
        }
        XmlAttributeValue attributeValue = (XmlAttributeValue) context;
        XmlAttribute attribute = getParentOfType(attributeValue, XmlAttribute.class);
        if (attribute == null
                || !isConcordionNamespace(attribute.getNamespace())
                || !CONCORDION_TAGS_FOR_EXPRESSION_INJECTION.contains(attribute.getLocalName())) {
            return;
        }

        registrar
                .startInjecting(ConcordionLanguage.INSTANCE)
                .addPlace(null, null, (PsiLanguageInjectionHost) attributeValue, new TextRange(1, attributeValue.getTextLength()-1))
                .doneInjecting();
    }

    @NotNull
    @Override
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return singletonList(XmlAttributeValue.class);
    }
}
