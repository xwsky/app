package com.platform.common.context.init;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;

/**
 * 应用程序初始化
 * @author xw
 *
 */
public class AppInitalizer implements ServletContextAware{
	
	Logger logger = Logger.getLogger(AppInitalizer.class);
	
	private List<Initializer> initializers;

	public void setServletContext(ServletContext ctx) {
		try {
			if(initializers != null){
				for(Initializer initializer : initializers){					
					initializer.before(ctx);					
					initializer.init(ctx);
					initializer.after(ctx);
				}
			}
			logger.info("[App context init success]");
		} catch (Exception e) {	
			logger.error("[App context init failure]", e);
			e.printStackTrace();
		}		
	}
	
	public List<Initializer> getInitializers() {
		return initializers;
	}

	public void setInitializers(List<Initializer> initializers) {
		this.initializers = initializers;
	}

	
	
}
