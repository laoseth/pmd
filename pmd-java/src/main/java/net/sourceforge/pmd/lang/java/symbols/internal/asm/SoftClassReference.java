/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.symbols.internal.asm;


import java.lang.ref.SoftReference;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A soft reference over a (possibly loaded class stub).
 */
final class SoftClassReference {

    private final Loader loader;
    private final String internalName;
    private final int observedArity;
    private final AsmSymbolResolver resolver;
    private SoftReference<ClassStub> ref;

    SoftClassReference(AsmSymbolResolver resolver, String internalName, Loader loader, int observedArity) {
        this.resolver = resolver;
        this.loader = loader;
        this.internalName = internalName;
        this.observedArity = observedArity;
    }

    @SuppressWarnings("PMD.AssignmentInOperand")
    @NonNull ClassStub get() {
        ClassStub c;
        if (ref == null || (c = ref.get()) == null) { // SUPPRESS CHECKSTYLE NOW
            c = new ClassStub(resolver, internalName, loader, observedArity);
            ref = new SoftReference<>(c);
        }
        return c;
    }

    @Override
    public String toString() {
        return internalName + " " + loader;
    }
}
