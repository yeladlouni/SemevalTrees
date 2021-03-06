

/* First created by JCasGen Sun Mar 04 15:52:51 CET 2018 */
package qa.qcri.iyas.type.cqa;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Mar 22 09:14:35 CET 2018
 * XML source: /home/sromeo/workspaces/UIMA/workspace/S3QACoreFramework/src/main/resources/descriptors/qa/qcri/iyas/type/CQATypeSystemDescriptor.xml
 * @generated */
public class RelatedQuestion extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(RelatedQuestion.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected RelatedQuestion() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public RelatedQuestion(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public RelatedQuestion(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public RelatedQuestion(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: concatenated

  /** getter for concatenated - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getConcatenated() {
    if (RelatedQuestion_Type.featOkTst && ((RelatedQuestion_Type)jcasType).casFeat_concatenated == null)
      jcasType.jcas.throwFeatMissing("concatenated", "qa.qcri.iyas.type.cqa.RelatedQuestion");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((RelatedQuestion_Type)jcasType).casFeatCode_concatenated);}
    
  /** setter for concatenated - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setConcatenated(boolean v) {
    if (RelatedQuestion_Type.featOkTst && ((RelatedQuestion_Type)jcasType).casFeat_concatenated == null)
      jcasType.jcas.throwFeatMissing("concatenated", "qa.qcri.iyas.type.cqa.RelatedQuestion");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((RelatedQuestion_Type)jcasType).casFeatCode_concatenated, v);}    
   
    
  //*--------------*
  //* Feature: candidateIDs

  /** getter for candidateIDs - gets 
   * @generated
   * @return value of the feature 
   */
  public StringArray getCandidateIDs() {
    if (RelatedQuestion_Type.featOkTst && ((RelatedQuestion_Type)jcasType).casFeat_candidateIDs == null)
      jcasType.jcas.throwFeatMissing("candidateIDs", "qa.qcri.iyas.type.cqa.RelatedQuestion");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedQuestion_Type)jcasType).casFeatCode_candidateIDs)));}
    
  /** setter for candidateIDs - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCandidateIDs(StringArray v) {
    if (RelatedQuestion_Type.featOkTst && ((RelatedQuestion_Type)jcasType).casFeat_candidateIDs == null)
      jcasType.jcas.throwFeatMissing("candidateIDs", "qa.qcri.iyas.type.cqa.RelatedQuestion");
    jcasType.ll_cas.ll_setRefValue(addr, ((RelatedQuestion_Type)jcasType).casFeatCode_candidateIDs, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for candidateIDs - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getCandidateIDs(int i) {
    if (RelatedQuestion_Type.featOkTst && ((RelatedQuestion_Type)jcasType).casFeat_candidateIDs == null)
      jcasType.jcas.throwFeatMissing("candidateIDs", "qa.qcri.iyas.type.cqa.RelatedQuestion");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedQuestion_Type)jcasType).casFeatCode_candidateIDs), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedQuestion_Type)jcasType).casFeatCode_candidateIDs), i);}

  /** indexed setter for candidateIDs - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setCandidateIDs(int i, String v) { 
    if (RelatedQuestion_Type.featOkTst && ((RelatedQuestion_Type)jcasType).casFeat_candidateIDs == null)
      jcasType.jcas.throwFeatMissing("candidateIDs", "qa.qcri.iyas.type.cqa.RelatedQuestion");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedQuestion_Type)jcasType).casFeatCode_candidateIDs), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RelatedQuestion_Type)jcasType).casFeatCode_candidateIDs), i, v);}
   
    
  //*--------------*
  //* Feature: ID

  /** getter for ID - gets 
   * @generated
   * @return value of the feature 
   */
  public String getID() {
    if (RelatedQuestion_Type.featOkTst && ((RelatedQuestion_Type)jcasType).casFeat_ID == null)
      jcasType.jcas.throwFeatMissing("ID", "qa.qcri.iyas.type.cqa.RelatedQuestion");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RelatedQuestion_Type)jcasType).casFeatCode_ID);}
    
  /** setter for ID - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setID(String v) {
    if (RelatedQuestion_Type.featOkTst && ((RelatedQuestion_Type)jcasType).casFeat_ID == null)
      jcasType.jcas.throwFeatMissing("ID", "qa.qcri.iyas.type.cqa.RelatedQuestion");
    jcasType.ll_cas.ll_setStringValue(addr, ((RelatedQuestion_Type)jcasType).casFeatCode_ID, v);}    
  }

    