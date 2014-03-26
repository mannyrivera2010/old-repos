package org.table;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import table.coders.Base64Coder;
import table.compression.Compression;
import table.compression.ZipInflater;

public class DomParserExample {

	// No generics
	ArrayList myEmpls;
	Document dom;

	public DomParserExample() {
		// create a list to hold the employee objects
		myEmpls = new ArrayList();
	}

	public void runExample() {
		// parse the xml file and get the dom object
		parseXmlFile();
		parseDocument();
	}

	private void parseXmlFile() {
		Compression ZipCom = new ZipInflater();

		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			File file = new File("data.dbx");
			// create FileInputStream object
			FileInputStream fin = new FileInputStream(file);
			/*
			 * Create byte array large enough to hold the content of the file.
			 * Use File.length to determine size of the file in bytes.
			 */
			byte fileContent[] = new byte[(int) file.length()];

			/*
			 * To read content of the file in byte array, use int read(byte[]
			 * byteArray) method of java FileInputStream class.
			 */
			fin.read(fileContent);

			// System.out.println(new String(fileContent));
			//byte[] base64BytesLevel1 = Base64Coder.decodeLines(new String(
			//		fileContent));
			byte[] deCompressedBytesLevel1 = ZipCom
					.Decompress(fileContent);

			byte[] base64BytesLevel2 = Base64Coder.decodeLines(new String(
					deCompressedBytesLevel1));
			byte[] deCompressedBytesLevel2 = ZipCom
					.Decompress(base64BytesLevel2);

			// System.out.println(new String(deCompressedBytesLevel2));

			// System.out.println(new String(base64BytesLevel2));

			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			dom = db.parse(new ByteArrayInputStream(deCompressedBytesLevel2));

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void parseDocument() {
		// get the root elememt
		Element docEle = dom.getDocumentElement();

		// get a nodelist of <Table> elements
		NodeList nlTable = docEle.getElementsByTagName("Table");
		if (nlTable != null && nlTable.getLength() > 0) {
			for (int i = 0; i < nlTable.getLength(); i++) {
				// get the Table elements
				Element elTable = (Element) nlTable.item(i);

				String Table_Name = getTextValue(elTable, "Table_Name");
				String Delimiter = getTextValue(elTable, "Delimiter");
				System.out.println(Table_Name + "\t" + Delimiter);

				// Getting Header Info
				NodeList nlHeader = elTable.getElementsByTagName("Header");
				if (nlHeader != null && nlHeader.getLength() > 0) {
					Element elHeaderSub = (Element) nlHeader.item(0);

					NodeList nlHeaderSub = elHeaderSub
							.getElementsByTagName("title");

					for (int k = 0; k < nlHeaderSub.getLength(); k++) {
						Element eltitleSub = (Element) nlHeaderSub.item(k);
						System.out.print("\t"
								+ eltitleSub.getFirstChild().getNodeValue()
										.trim());
					}
					// System.out.println(nlHeaderSub.getLength());

				}

				System.out.println();
				NodeList nlEntry = elTable.getElementsByTagName("Entry");
				if (nlEntry != null && nlEntry.getLength() > 0) {
					for (int ka = 0; ka < nlEntry.getLength(); ka++) {
						Element elEntrySub = (Element) nlEntry.item(ka);

						NodeList nlValueSub = elEntrySub
								.getElementsByTagName("Value");

						for (int k = 0; k < nlValueSub.getLength(); k++) {
							Element eltitleSub = (Element) nlValueSub.item(k);
							String Value = "";
							try {
								Value = eltitleSub.getFirstChild()
										.getNodeValue().trim();
							} catch (Exception e) {
								Value = "";
							}

							System.out.print("\t" + Value);
						}
						System.out.println();
					}
				}

			}
		}// end Table
	}

	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return textVal;
	}

	/**
	 * Iterate through the list and print the content to console
	 */
	private void printData() {

		System.out.println("No of Employees '" + myEmpls.size() + "'.");

		Iterator it = myEmpls.iterator();
		while (it.hasNext()) {
			System.out.println(it.next().toString());
		}
	}

	public static void main(String[] args) {
		// create an instance
		DomParserExample dpe = new DomParserExample();

		// call run example
		dpe.runExample();
	}

}
