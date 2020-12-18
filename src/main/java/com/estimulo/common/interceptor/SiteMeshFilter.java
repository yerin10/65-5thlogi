package com.estimulo.common.interceptor;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.DivExtractingTagRuleBundle;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {

		// Map default decorator. This shall be applied to all paths if no other paths match.
		builder.addDecoratorPath("/*", "/WEB-INF/decorators/mainLayout/layout1.jsp")
//           .addDecoratorPaths("/articles/*", "/decorators/article.html",
//                                             "/decoratos/two-page-layout.html",
//                                             "/decorators/common.html")
				// Exclude path from decoration.
				.addExcludedPath("*.do*")
				.addExcludedPath("*login*")
				.addExcludedPath("*logout*")
				.addExcludedPath("*codeModal*");
		builder.addTagRuleBundles(new DivExtractingTagRuleBundle());

	}

}