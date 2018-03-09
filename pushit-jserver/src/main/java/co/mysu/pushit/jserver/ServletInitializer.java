package co.mysu.pushit.jserver;

import org.springframework.beans.factory.annotation.AnnotationBeanWiringInfoResolver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PushitJserverApplication.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.setServletContext(servletContext);
		ServletRegistration.Dynamic dyn = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		dyn.addMapping("/");
		dyn.setLoadOnStartup(1);


	}
}
