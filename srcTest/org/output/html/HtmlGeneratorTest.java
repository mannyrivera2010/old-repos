package org.output.html;

import org.enums.EngineOutputEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class HtmlGeneratorTest.
 */
public class HtmlGeneratorTest {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		HtmlGenerator htmlOutputGenerator=new HtmlGenerator();
		System.out.println(htmlOutputGenerator.printHTMLGrantList(EngineOutputEnum.FULLHTMLONLY));
	}

}
