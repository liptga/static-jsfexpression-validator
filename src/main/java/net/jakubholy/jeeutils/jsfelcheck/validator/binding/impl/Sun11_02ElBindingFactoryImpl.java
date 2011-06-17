package net.jakubholy.jeeutils.jsfelcheck.validator.binding.impl;

import java.util.logging.Logger;

import javax.faces.application.Application;
import javax.faces.el.MethodBinding;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;

import net.jakubholy.jeeutils.jsfelcheck.validator.binding.ElBindingFactory;

import com.sun.faces.el.MethodBindingImpl;
import com.sun.faces.el.MixedELValueBinding;
import com.sun.faces.el.ValueBindingImpl;
import com.sun.faces.util.Util;

/**
 * Implementation using Sun jsf-impl 1.1_02.
 */
public class Sun11_02ElBindingFactoryImpl implements ElBindingFactory {

    @SuppressWarnings("rawtypes")
    private static Class[] NO_PARAMS = new Class[0];

    private final Logger log = Logger.getLogger(Sun11_02ElBindingFactoryImpl.class
            .getName());

    /** Needed to fetch the property and variable resolvers. */
    private final Application application;

    /**
     * @param application (required) Needed to fetch the property and variable resolvers.
     */
    public Sun11_02ElBindingFactoryImpl(Application application) {
        this.application = application;
    }

    /* (non-Javadoc)
     * @see net.jakubholy.jeeutils.jsfelcheck.validator.ElBindingFactory#createValueBinding(java.lang.String)
     */
    @Override
    public ValueBinding createValueBinding(String ref)
            throws ReferenceSyntaxException {

        ValueBinding valueBinding = null;
        if (ref == null) {
            String message = Util
                    .getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR");
            /*     */
            message = message + " ref " + ref;
            throw new NullPointerException(message);
        }
        if (!(Util.isVBExpression(ref))) {
            log.severe(" Expression " + ref
                    + " does not follow the JSF EL syntax ");
            throw new ReferenceSyntaxException(ref);
        }

        if (Util.isMixedVBExpression(ref)) {
            valueBinding = new MixedELValueBinding(application);
        } else {
            ref = Util.stripBracketsIfNecessary(ref);
            // checkSyntax(ref);
            valueBinding = new ValueBindingImpl(application);
        }
        ((ValueBindingImpl) valueBinding).setRef(ref);

        return valueBinding;
    }

    /* (non-Javadoc)
     * @see net.jakubholy.jeeutils.jsfelcheck.validator.ElBindingFactory#createMethodBinding(java.lang.String)
     */
    @Override
    public MethodBinding createMethodBinding(String ref) {
        if (ref == null) {
            throw new NullPointerException("The argument ref: String may not be null");
        }

        return new MethodBindingImpl(application, ref, NO_PARAMS);
    }

}
