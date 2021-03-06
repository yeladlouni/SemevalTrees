/**
 * Copyright 2018 Salvatore Romeo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  
 */

package qa.qcri.iyas.feature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.broker.BrokerService;
import org.apache.uima.UIMAFramework;
import org.apache.uima.aae.client.UimaAsynchronousEngine;
import org.apache.uima.adapter.jms.client.BaseUIMAAsynchronousEngine_impl;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.InvalidXMLException;
import org.jdom2.JDOMException;
import org.junit.Test;
import org.xml.sax.SAXException;

import qa.qcri.iyas.Starter;
import qa.qcri.iyas.TestDescriptorGenerator;
import qa.qcri.iyas.data.preprocessing.StandardPreprocessor;
import qa.qcri.iyas.data.reader.InputCollectionDataReader;
import qa.qcri.iyas.data.reader.PlainTextDataReader;
import qa.qcri.iyas.data.reader.XmlSemeval2016CqaEn;

public class ClassificationTest {
	
	private String generateInputTestFile(boolean concatenate) throws IOException {
		StandardPreprocessor sp = new StandardPreprocessor();
		File file = new File("test_input.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		for (int k=1;k<=10;k++) {
			String qid = "Q"+k;
			String org_subject = "This is the subject of original question "+qid;
			String org_body = "This is the body of original question "+qid;
			
			Map<String,String> mapB = new HashMap<String,String>();
			if (concatenate) {
				mapB.put("body_"+qid, sp.concatenateBodyAndSubject(
						sp.preprocess(org_subject,"en"),
						sp.preprocess(org_body,"en"),false));
			} else {
				mapB.put("subject_"+qid, sp.preprocess(org_subject,"en"));
				mapB.put("body_"+qid, sp.preprocess(org_body,"en"));
			}
			for (int i=1;i<=10;i++) {
				out.write(qid+"	"+org_subject+"	"+org_body);
				String rid = qid+"_R"+i;
				String rel_subject = "This is the subject of related question "+rid;
				String rel_body = "This is the body of related question "+rid;
				out.write("	"+rid+"	"+rel_subject+"	"+rel_body);
				
				if (concatenate) {
					mapB.put("rel_body_"+rid, sp.concatenateBodyAndSubject(
							sp.preprocess(rel_subject,"en"),
							sp.preprocess(rel_body,"en"),false));
				} else {
					mapB.put("rel_subject_"+rid, sp.preprocess(rel_subject,"en"));
					mapB.put("rel_body_"+rid, sp.preprocess(rel_body,"en"));
				}
				
				Map<String,String> mapA = new HashMap<String,String>();
				
				if (concatenate) {
					mapA.put("body_"+rid, sp.concatenateBodyAndSubject(
							sp.preprocess(rel_subject,"en"), 
							sp.preprocess(rel_body,"en"),false));
				} else {
					mapA.put("subject_"+rid, sp.preprocess(rel_subject,"en"));
					mapA.put("body_"+rid, sp.preprocess(rel_body,"en"));
				}
				
				for (int j=1;j<=10;j++) {
					String cid = rid+"_C"+j;
					String comment = "This comment "+cid;
					out.write("	"+cid+"	"+comment);
					mapA.put("comment_"+cid, sp.preprocess(comment,"en"));
				}
				out.newLine();
				
			}
		}
		out.close();
		
		return file.getAbsolutePath();
	}
	
	private CollectionReaderDescription getCollectionReaderDescriptorTaskA(String file) throws ResourceInitializationException, IOException {
		CollectionReaderDescription collectionReaderDescr = CollectionReaderFactory.createReaderDescription(
				InputCollectionDataReader.class);
		ExternalResourceDescription reader = ExternalResourceFactory.createExternalResourceDescription(PlainTextDataReader.class,
				PlainTextDataReader.FILE_PARAM, file,
				PlainTextDataReader.TASK_PARAM, PlainTextDataReader.INSTANCE_A_TASK);
		ExternalResourceFactory.bindExternalResource(collectionReaderDescr, 
				InputCollectionDataReader.INPUT_READER_PARAM, reader);
		
		return collectionReaderDescr;
	}
	
	private CollectionReaderDescription getCollectionReaderDescriptorTaskB(String file) throws ResourceInitializationException, IOException {
		CollectionReaderDescription collectionReaderDescr = CollectionReaderFactory.createReaderDescription(
				InputCollectionDataReader.class);
		ExternalResourceDescription reader = ExternalResourceFactory.createExternalResourceDescription(XmlSemeval2016CqaEn.class,
				XmlSemeval2016CqaEn.FILE_PARAM, file,
				XmlSemeval2016CqaEn.TASK_PARAM, XmlSemeval2016CqaEn.INSTANCE_B_TASK);
		ExternalResourceFactory.bindExternalResource(collectionReaderDescr, 
				InputCollectionDataReader.INPUT_READER_PARAM, reader);
		
		return collectionReaderDescr;
	}
	
	private void runTestTaskA(boolean concatenate,String inputFile) throws Exception {
		UimaAsynchronousEngine uimaAsEngine = new BaseUIMAAsynchronousEngine_impl();

		Map<String,Object> appCtx = new HashMap<String,Object>();
		appCtx.put(UimaAsynchronousEngine.DD2SpringXsltFilePath,System.getenv("UIMA_HOME") + "/bin/dd2spring.xsl");
		appCtx.put(UimaAsynchronousEngine.SaxonClasspath,"file:" + System.getenv("UIMA_HOME") + "/saxon/saxon8.jar");
		appCtx.put(UimaAsynchronousEngine.ServerUri, "tcp://localhost:61616");
		appCtx.put(UimaAsynchronousEngine.ENDPOINT, "classificationQueue");
		appCtx.put(UimaAsynchronousEngine.CasPoolSize, 100);
		
		CollectionReader collectionReaderA = UIMAFramework.produceCollectionReader(getCollectionReaderDescriptorTaskA(inputFile));
		ClassificationStatusCallBackListener listenerA = new ClassificationStatusCallBackListener();
		uimaAsEngine.addStatusCallbackListener(listenerA);
		
		uimaAsEngine.initialize(appCtx);
		
		double startA = System.currentTimeMillis();
		while (collectionReaderA.hasNext()) {
			CAS cas = CasCreationUtils.createCas(TypeSystemDescriptionFactory.createTypeSystemDescription(),
					null, null);
			collectionReaderA.getNext(cas);
			uimaAsEngine.sendCAS(cas);
		}
		uimaAsEngine.collectionProcessingComplete();
		double endA = System.currentTimeMillis();
		double secondsA = (endA - startA)/1000;
		System.out.println(secondsA+" seconds");
		
		uimaAsEngine.stop();
	}
	
	private void runTestTaskB(boolean concatenate,String inputFile) throws Exception {
		UimaAsynchronousEngine uimaAsEngine = new BaseUIMAAsynchronousEngine_impl();

		Map<String,Object> appCtx = new HashMap<String,Object>();
		appCtx.put(UimaAsynchronousEngine.DD2SpringXsltFilePath,System.getenv("UIMA_HOME") + "/bin/dd2spring.xsl");
		appCtx.put(UimaAsynchronousEngine.SaxonClasspath,"file:" + System.getenv("UIMA_HOME") + "/saxon/saxon8.jar");
		appCtx.put(UimaAsynchronousEngine.ServerUri, "tcp://localhost:61616");
		appCtx.put(UimaAsynchronousEngine.ENDPOINT, "classificationQueue");
		appCtx.put(UimaAsynchronousEngine.CasPoolSize, 100);
		
		CollectionReader collectionReaderB = UIMAFramework.produceCollectionReader(getCollectionReaderDescriptorTaskB(inputFile));
		ClassificationStatusCallBackListener listenerB = new ClassificationStatusCallBackListener();
		uimaAsEngine.addStatusCallbackListener(listenerB);
		
		uimaAsEngine.initialize(appCtx);
		
		double startA = System.currentTimeMillis();
		while (collectionReaderB.hasNext()) {
			CAS cas = CasCreationUtils.createCas(TypeSystemDescriptionFactory.createTypeSystemDescription(),
					null, null);
			collectionReaderB.getNext(cas);
			uimaAsEngine.sendCAS(cas);
		}
		uimaAsEngine.collectionProcessingComplete();
		double endA = System.currentTimeMillis();
		double secondsA = (endA - startA)/1000;
		System.out.println(secondsA+" seconds");
		
		uimaAsEngine.stop();
		
	}
	
	
	
	public void multiplierTest() throws Exception {
		
//		BrokerService broker = new BrokerService();
//		broker.addConnector("tcp://localhost:61616");
//		broker.start();
//
//		UimaAsynchronousEngine uimaAsEngine = new BaseUIMAAsynchronousEngine_impl();
//		
//		String id1 = Starter.depoyFeatureExtraction(uimaAsEngine,"tcp://localhost:61616","en","featureExtractionQueue",1,true,false,true);
//		String id2 = Starter.depoyClassification(uimaAsEngine,"tcp://localhost:61616","en","classificationQueue",1,
//				"/home/sromeo/workspaces/UIMA/workspace/S3QACoreFramework/1521745849273.mdl",
//				"tcp://localhost:61616","featureExtractionQueue");
//				
//		String file = generateInputTestFile(true);
//		runTestTaskB(true, "/home/sromeo/workspaces/UIMA/workspace/S3QACoreFramework/src/test/resources/data/XML/SemEval/English/SemEval2016-Task3-CQA-QL-dev.xml");
//		runTestTaskA(true, file);
//		Starter.undeployPipeline(id1,uimaAsEngine);
//		Starter.undeployPipeline(id2,uimaAsEngine);
//
//		Thread.sleep(100);
//		uimaAsEngine.stop();
//		broker.stop();
		
	}
	
	public static void main(String args[]) throws Exception {
		new ClassificationTest().multiplierTest();
	}
}
