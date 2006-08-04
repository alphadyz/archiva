package org.apache.maven.repository.manager.web.interceptor;

/*
 * Copyright 2005-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;
import org.apache.maven.repository.configuration.Configuration;
import org.apache.maven.repository.configuration.ConfigurationStore;

/**
 * An interceptor that makes the application configuration available
 *
 * @author <a href="mailto:brett@apache.org">Brett Porter</a>
 * @todo might be a generally useful thing in plexus-xwork-integration
 * @plexus.component role="com.opensymphony.xwork.interceptor.Interceptor" role-hint="configurationInterceptor"
 */
public class ConfigurationInterceptor
    implements Interceptor
{
    /**
     * @plexus.requirement
     */
    private ConfigurationStore configurationStore;

    public String intercept( ActionInvocation actionInvocation )
        throws Exception
    {
        Configuration configuration = configurationStore.getConfigurationFromStore();

        if ( !configuration.isValid() )
        {
            if ( configuration.getRepositories().isEmpty() )
            {
                return "config-repository-needed";
            }
            else
            {
                return "config-needed";
            }
        }
        else
        {
            return actionInvocation.invoke();
        }
    }

    public void destroy()
    {
        // This space left intentionally blank
    }

    public void init()
    {
        // This space left intentionally blank
    }
}
