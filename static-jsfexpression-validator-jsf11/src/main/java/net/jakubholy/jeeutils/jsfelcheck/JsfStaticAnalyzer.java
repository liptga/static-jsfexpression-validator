/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.jakubholy.jeeutils.jsfelcheck;

import com.sun.faces.el.impl.AndOperator;
import net.jakubholy.jeeutils.jsfelcheck.beanfinder.InputResource;
import net.jakubholy.jeeutils.jsfelcheck.beanfinder.ManagedBeanFinder;
import net.jakubholy.jeeutils.jsfelcheck.beanfinder.jsf11.Jsf11FacesConfigXmlBeanFinder;
import net.jakubholy.jeeutils.jsfelcheck.expressionfinder.impl.facelets.JsfElValidatingFaceletsParser;
import net.jakubholy.jeeutils.jsfelcheck.expressionfinder.impl.jasper.PageNodeListener;
import net.jakubholy.jeeutils.jsfelcheck.validator.ValidatingElResolver;
import net.jakubholy.jeeutils.jsfelcheck.validator.jsf11.Jsf11ValidatingElResolver;

import java.io.File;
import java.util.Collection;

/**
 * {@inheritDoc}
 *
 * Implementation based on JSF 1.1.
 */
public class JsfStaticAnalyzer extends AbstractJsfStaticAnalyzer<JsfStaticAnalyzer> {

	public static JsfStaticAnalyzer forJsp() {
		return new JsfStaticAnalyzer(ViewType.JSP);
	}

	/** @deprecated use the static factory method {@link #forJsp()}  */
	public JsfStaticAnalyzer() {
		this(ViewType.JSP);
	}

	JsfStaticAnalyzer(ViewType viewType) {
		super(ViewType.JSP);
		LOG.info("Created JSF 1.1 JsfStaticAnalyzer");
	}

	public static void main(String[] args) throws Exception { // SUPPRESS CHECKSTYLE (no JavaDoc)
        AbstractJsfStaticAnalyzer.main(new JsfStaticAnalyzer(), args);
    }

    @Override
    protected ValidatingElResolver createValidatingElResolver() {
        if (!new AndOperator().toString().startsWith("HACKED BY JSFELCHECK ")) {
            handleUnhackedElImplementationLoaded("jsf-impl");
        }
        return new Jsf11ValidatingElResolver();
    }

	@Override
	protected JsfElValidatingFaceletsParser createValidatingFaceletsParser(File webappRoot, PageNodeListener pageNodeValidator) {
		throw new UnsupportedOperationException("Sorry, we haven't implemented support for Facelets in the JSF 1.1 "
				+ "validator, consider trying it with the JSF 1.2 version (should be mostly backwards-compatible),");
	}

	@Override
	protected ManagedBeanFinder createManagedBeanFinder(Collection<InputResource> facesConfigFilesToRead) {
        return Jsf11FacesConfigXmlBeanFinder.forResources(facesConfigFilesToRead);
    }

}
