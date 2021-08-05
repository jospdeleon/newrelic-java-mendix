package com.mendix.m2ee.appcontainer.server.handler;

import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public class RuntimeServlet {

	@NewField
	private static final Logger LOGGER = NewRelic.getAgent().getLogger();

	@Trace
	public void service(HttpServletRequest request, HttpServletResponse response) {
		String URI = request.getRequestURI();
		LOGGER.log(Level.FINER, "INSIDE MENDIX SERVLET : URI - {0}", URI);
		
		String referer = request.getHeader("Referer");
		LOGGER.log(Level.FINER, "MENDIX SERVLET : Referer - {0}", referer);

		NewRelic.getAgent().getTracedMethod().addCustomAttribute("mendix.uri", URI);
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("mendix.referer", referer);
		
		Weaver.callOriginal();
	}
}
