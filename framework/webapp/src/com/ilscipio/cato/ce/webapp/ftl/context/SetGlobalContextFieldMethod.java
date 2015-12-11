/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package com.ilscipio.cato.ce.webapp.ftl.context;

import java.util.List;
import java.util.Map;

import org.ofbiz.base.util.template.FreeMarkerWorker;

import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

/**
 * SetGlobalContextFieldMethod - Freemarker Method for setting global context fields
 * <p>
 * Derived from {@link org.ofbiz.webapp.ftl.SetContextFieldTransform}.
 */
public class SetGlobalContextFieldMethod implements TemplateMethodModelEx {

    public static final String module = SetGlobalContextFieldMethod.class.getName();

    /*
     * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object exec(List args) throws TemplateModelException {
        if (args == null || args.size() != 2)
            throw new TemplateModelException("Invalid number of arguements");
        if (!(args.get(0) instanceof TemplateScalarModel))
            throw new TemplateModelException("First argument not an instance of TemplateScalarModel");
        if (!(args.get(1) instanceof BeanModel) && !(args.get(1) instanceof TemplateNumberModel) && !(args.get(1) instanceof TemplateScalarModel))
            throw new TemplateModelException("Second argument not an instance of BeanModel nor TemplateNumberModel nor TemplateScalarModel");

        Environment env = FreeMarkerWorker.getCurrentEnvironment();
        BeanModel req = (BeanModel)env.getVariable("globalContext");
        Map globalContext = (Map) req.getWrappedObject();

        // Cato: for time being, keep same behavior as stock setContextField here...
        String name = ((TemplateScalarModel) args.get(0)).getAsString();
        Object value = null;
        if (args.get(1) instanceof TemplateScalarModel)
            value = ((TemplateScalarModel) args.get(1)).getAsString();
        if (args.get(1) instanceof TemplateNumberModel)
            value = ((TemplateNumberModel) args.get(1)).getAsNumber();
        if (args.get(1) instanceof BeanModel)
            value = ((BeanModel) args.get(1)).getWrappedObject();

        globalContext.put(name, value);
        return new SimpleScalar("");
    }

}