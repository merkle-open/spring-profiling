package com.namics.oss.spring.profiling.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

public class ProfilingConfigurationSelector extends AdviceModeImportSelector<EnableProfiling> {
	public String[] selectImports(AdviceMode adviceMode) {
		switch (adviceMode) {
		case PROXY:
			return null;
		case ASPECTJ:
			return new String[] { ProfilingConfiguration.class.getName() };
		default:
			return null;
		}
	}

}
