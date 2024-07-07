package csen1002.main.task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Write your info here
 * 
 * @name Maram Hossam El-Deen Mohamed
 * @id 49-1891
 * @labNumber 18
 */

public class NfaToDfa {
	
	private String alphabetString;
	private ArrayList<ArrayList<String>> DFAsetOfStates = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> DFAfinalSetOfStates = new ArrayList<ArrayList<String>>();
	private String[] NFAsetOfStates; //this dictates your position in the DFA arrayList azon
	private String[] NFAtransitionStates;
	private ArrayList<String> DFAtransitionStates = new ArrayList<String>();
	private ArrayList<ArrayList<String>> DFAacceptStates = new ArrayList<ArrayList<String>>();
	private String[] nfaAcceptStates;
	private String initialState = "";
	private String nfaInitialState;

	/**
	 * Constructs a DFA corresponding to an NFA
	 * 
	 * @param input A formatted string representation of the NFA for which an
	 *              equivalent DFA is to be constructed. The string representation
	 *              follows the one in the task description
	 */
	public NfaToDfa(String input) {
		String [] inputArray = input.split("#");
		
		this.alphabetString = inputArray[1];
		
		this.NFAsetOfStates = inputArray[0].split(";");
		InitEpsilonEnclosure(NFAsetOfStates);
		
		this.NFAtransitionStates = inputArray[2].split(";");
		this.nfaAcceptStates = inputArray[4].split(";");
		this.nfaInitialState = inputArray[3];
		
		//System.out.println(nfaInitialState);
		
		StateEpsilonClosures(DFAsetOfStates);
		StateTransitionClosures(DFAsetOfStates);
		AlphabetTransitions();
		AcceptedStates();
		//toString();
		
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
public void InitEpsilonEnclosure(String[] nfaStates) {
		
		for (int i = 0; i < nfaStates.length; i++) {
			ArrayList<String> basicEpsilon = new ArrayList<String>();
			basicEpsilon.add(nfaStates[i]);
			//System.out.println("BASIC EPSILON: " + nfaStates[i]);
			int index = Integer.parseInt(nfaStates[i]);
			//System.out.println("INDEX: " + index);
			
			
			while (index >= DFAsetOfStates.size()) {
                DFAsetOfStates.add(null);
            }
			
			DFAsetOfStates.set(index, basicEpsilon);
		}
		
		//System.out.println("DFAsetOfStates: " + DFAsetOfStates);
	}
	
	
	
	public void StateEpsilonClosures(ArrayList<ArrayList<String>> dfaStates) {
		for (int i = 0; i < NFAtransitionStates.length; i++) {
			String[] currentTstate = NFAtransitionStates[i].split(",");
			if (currentTstate[1].equals("e")) {
				int DFAindex = Integer.parseInt(currentTstate[0]);
				//System.out.println("DFA STATE ABL: " + dfaStates.get(DFAindex));
				if (! (dfaStates.get(DFAindex).contains(currentTstate[2]))) {
					dfaStates.get(DFAindex).add(currentTstate[2]);
				}
				
				//System.out.println("DFA STATE BA3D: " + dfaStates.get(DFAindex));
				//System.out.println("ANA HENA " + NFAtransitionStates[i] );
			}
		}
		//System.out.println("dfaStates: " + dfaStates);
	}
	
	
	public void StateTransitionClosures(ArrayList<ArrayList<String>> dfaStates) {
		for (int i = 0; i < dfaStates.size(); i++) {
			for(int j = 0; j < dfaStates.get(i).size(); j++) {
				int subArrayElement = Integer.parseInt(dfaStates.get(i).get(j));
				ArrayList<String> replacementArray = dfaStates.get(subArrayElement);
				//System.out.println("My element: " + subArrayElement);
				//System.out.println("My subArray: " + replacementArray);
				ArrayList<String> currentArray = dfaStates.get(i);
				ArrayList<String> updatedSubArray = isPresentDontAdd(currentArray, replacementArray);
				//System.out.println("Updated subArray = " + updatedSubArray);
				dfaStates.set(i, updatedSubArray);
				//System.out.println("DFA ARRAY BEFORE SORTING: " + dfaStates);
				
				
			}
		}
		
		sortSubArrays(dfaStates);
		//System.out.println("DFA ARRAY AFTER SORTING: " + dfaStates);
	}
	
	
	public ArrayList<String> isPresentDontAdd(ArrayList<String> ogArray, ArrayList<String> replacementArray) {
		ArrayList<String> updatedSubArray = new ArrayList<String>();
		updatedSubArray.addAll(ogArray);
		for (int i = 0; i < replacementArray.size(); i++) {
			if(!(ogArray.contains(replacementArray.get(i)))) {
				updatedSubArray.add(replacementArray.get(i));
			}
		}
		//System.out.println("My subArray BEFORE SORTING: " + updatedSubArray);
		//Collections.sort(updatedSubArray, (a, b) -> Integer.compare(Integer.parseInt(a), Integer.parseInt(b)));
		//System.out.println("My subArray AFTER SORTING: " + updatedSubArray);
		return updatedSubArray;
	}
	
	
	public void sortSubArrays(ArrayList<ArrayList<String>> dfaStates) {
		for (int i = 0; i < dfaStates.size(); i++) {
			Collections.sort(dfaStates.get(i), (a, b) -> Integer.compare(Integer.parseInt(a), Integer.parseInt(b)));
		}
	}
	
	
	public static void sortNewState(ArrayList<String> newState) {
        Collections.sort(newState, (a, b) -> Integer.compare(Integer.parseInt(a), Integer.parseInt(b)));
    }
	
	
	public void sortArrayListOfArrayLists(ArrayList<ArrayList<String>> dfaStates) {
	    Collections.sort(dfaStates, new Comparator<ArrayList<String>>() {
	        public int compare(ArrayList<String> list1, ArrayList<String> list2) {
	        	int minSize;
	        	if (list1.size() > list2.size()) {
	        		minSize = list2.size();
	        	}
	        	else if (list2.size() > list1.size()) {
	        		minSize = list1.size();
	        	}
	        	
	        	else {
	        		minSize = list2.size();
	        	}
	        	
	        	for (int i = 0; i < minSize; i++) {
	        		String Element1List1 = list1.get(i);
	   	            String Element1List2 = list2.get(i);
	   	            int intElement1List1 = Integer.parseInt(Element1List1);
	   	            int intElement1List2 = Integer.parseInt(Element1List2);
	   	            
	   	         if (intElement1List1 != intElement1List2) {
	   	        	return Integer.compare(intElement1List1, intElement1List2);
	   	         }
	   	         
	   	        
	        	}
	        	
	        	return 0;
	        }
	    });
	}
	
	
	
	
//	public void sortTstates(ArrayList<String> dfaTStates) {
//		
//		Collections.sort(dfaTStates, new Comparator<String>() {
//            @Override
//            public int compare(String t1, String t2) {
//                String[] t1String = t1.split(",");
//                String[] t2String = t2.split(",");
//
//                String[] t1Num1 = t1String[0].split("/");
//                String[] t2Num1 = t2String[0].split("/");
//
//                int minT1Number = getMinNumber(t1Num1);
//                int minT2Number = getMinNumber(t2Num1);
//                
//                
//                int compareNumbers = Integer.compare(minT1Number, minT2Number);
//                if (compareNumbers != 0) {
//                    return compareNumbers;
//                }
//
//                // Compare non-numeric parts
//                int compareFirst = t1String[1].compareTo(t2String[1]);
//                if (compareFirst != 0) {
//                    return compareFirst;
//                }
//
//                return t1String[2].compareTo(t2String[2]);
//            }
//
//            private int getMinNumber(String[] numbers) {
//                int min = Integer.MAX_VALUE;
//                for (int i = 0; i < numbers.length; i++ ) {
//                    int current = Integer.parseInt(numbers[i]);
//                    if (current < min) {
//                        min = current;
//                    }
//                }
//                return min;
//            }
//        });
//    }
	
	
public void sortTstates(ArrayList<String> dfaTStates) {
		
		Collections.sort(dfaTStates, new Comparator<String>() {
            @Override
            public int compare(String t1, String t2) {
                String[] t1String = t1.split(",");
                String[] t2String = t2.split(",");

                String[] t1Num1 = t1String[0].split("/");
                String[] t2Num1 = t2String[0].split("/");


                int minT1Number = getMinNumber1(t1Num1);
                int minT2Number = getMinNumber1(t2Num1);

                
                
                
                int compareNumbers = Integer.compare(minT1Number, minT2Number);
                if (compareNumbers != 0) {
                    return compareNumbers;
                }
                
              
//                else if (t1Num1.length > t2Num1.length){
//                	return Integer.compare(t1Num1.length, t2Num1.length);
//                    }
                
                else {
                	 int compRemaining = CompareRemaining(t1Num1, t2Num1, minT2Number);
                 	if (compRemaining != 0) {
                         return compRemaining;
                     }
                }
                
                
               

                // alphabet
                int compareFirst = t1String[1].compareTo(t2String[1]);
                if (compareFirst != 0) {
                    return compareFirst;
                }

                return t1String[2].compareTo(t2String[2]);
            }

            private int getMinNumber1(String[] numbers) {
                int min = Integer.MAX_VALUE;
                for (int i = 0; i < numbers.length; i++ ) {
                    int current = Integer.parseInt(numbers[i]);
                    if (current < min) {
                        min = current;
                    }
                }
                return min;
            }
            
            
            private int CompareRemaining(String[] t1Num1, String[] t2Num1, int min) {
            	
            	int minLength;
            	if (t1Num1.length > t2Num1.length) {
            		minLength = t2Num1.length;
            	}
            	
            	else if (t2Num1.length > t1Num1.length) {
            		minLength = t1Num1.length;
            	}
            	
            	else {
            		minLength = t1Num1.length;
            	}
            	
            	
                for (int i = 0; i < minLength; i++) {
                    if (!(t1Num1[i].equals(String.valueOf(min)))  && !(t2Num1[i].equals(String.valueOf(min)))) {
                    	//System.out.println(t1Num1[i]);
                    	//System.out.println(t2Num1[i]);
                                int compare = Integer.compare(Integer.parseInt(t1Num1[i]), Integer.parseInt(t2Num1[i]));
                                if (compare != 0) {
                                    return compare;
                                }
                            }
                        }
            return Integer.compare(t1Num1.length, t2Num1.length);
            
        }});
    }
                
                
                
	
	public void AlphabetTransitions() {
		String[] NFAalphabet = alphabetString.split(";");
		
		
		
		//adding in the initial state to final DFA
		//for (int i = 0; i < DFAsetOfStates.size(); i++) {
			//if (! (DFAsetOfStates.get(i).equals(null))) {
				int initDFAStateIndex = Integer.parseInt(nfaInitialState);
				DFAfinalSetOfStates.add(DFAsetOfStates.get(initDFAStateIndex));
			    PreserveInitialState(DFAsetOfStates.get(initDFAStateIndex));
				
//				System.out.println("MY SET OF RUINED STATES " + DFAsetOfStates);
				//System.out.println("Im adding my initial state " + DFAsetOfStates.get(i));
				//break;
			//}
		//}
		
		
		for (int i = 0; i < DFAfinalSetOfStates.size(); i++) {
		//	String tState = "";
				
				for (int k = 0; k < NFAalphabet.length ; k++) {
					
					//System.out.println("ana and element meen now: " + DFAfinalSetOfStates.get(i) + " at iteration " + i);
					//System.out.println("My current alphabet: " + NFAalphabet[k]);
				
					
					//ArrayList<String> newStateElements = NewState(DFAfinalSetOfStates.get(i), NFAalphabet[k]);
					NewState(DFAfinalSetOfStates.get(i), NFAalphabet[k]);
					//break; //SHELEENY
					
					//System.out.println("newStateElements: " + newStateElements);
			}
		}
	}

	
	
	
	public void NewState(ArrayList<String> state, String alphabet) {
		//String[] NFAalphabet = alphabetString.split(";");
		ArrayList<String> returnState = new ArrayList<String>();
		String newTstate = "";
		
		//for (int i = 0; i < NFAalphabet.length; i++) {
			for(int i = 0; i < NFAtransitionStates.length; i++) {
				String[] currTransitionState = NFAtransitionStates[i].split(",");
				
				for (int j = 0; j < state.size(); j++) {
					
					if(currTransitionState[0].equals(state.get(j)) && currTransitionState[1].equals(alphabet)) {
						int dfaStateIndex = Integer.parseInt(currTransitionState[2]);
						for (int k = 0; k < DFAsetOfStates.get(dfaStateIndex).size(); k++) {
							if (! (returnState.contains(DFAsetOfStates.get(dfaStateIndex).get(k)))) {
								returnState.add(DFAsetOfStates.get(dfaStateIndex).get(k));
							}
						}
						
						//System.out.println("The T State: " + NFAtransitionStates[i]);
						//System.out.println("The Return States: " + returnState);
						
				}
					
				}
			}
			
			sortNewState(returnState);
			if (returnState.isEmpty()) {
				returnState.add("-1");
			}
			
			for (int k = 0; k < state.size(); k++) {
				if (newTstate.equals("")) {
					newTstate = state.get(k);
				}
				
				else {
					newTstate = newTstate + "/" + state.get(k);
				}
			}
			
			newTstate = newTstate + "," + alphabet + ",";
			
			for (int k = 0; k < returnState.size(); k++) {
				if (k == 0) {
					newTstate = newTstate + returnState.get(k);
				}
				
				else {
					newTstate = newTstate + "/" + returnState.get(k);
				}
			}
			
			
			if (!(DFAtransitionStates.contains(newTstate))) {
				//System.out.println("newTstate: " + newTstate);
				DFAtransitionStates.add(newTstate);
				//System.out.println("returnState: " + returnState);
				//System.out.println(returnState);
				//System.out.println(returnState);
				if (! (DFAfinalSetOfStates.contains(returnState))) {
					DFAfinalSetOfStates.add(returnState);
				}
				
				//System.out.println("DFAfinalSetOfStates: " + DFAfinalSetOfStates);
			}
		
	}
	
	
	
	public void AcceptedStates() {
		for (int i = 0; i < nfaAcceptStates.length; i++ ) {
			for (int j = 0; j < DFAfinalSetOfStates.size(); j++) {
				if (DFAfinalSetOfStates.get(j).contains(nfaAcceptStates[i])) {
					if (! (DFAacceptStates.contains(DFAfinalSetOfStates.get(j)))) {
						DFAacceptStates.add(DFAfinalSetOfStates.get(j));
					}
					
				}
			}
		}
	}
	
	
	public void PreserveInitialState(ArrayList<String> initState) {
		for (int i = 0; i < initState.size(); i++) {
			if (initialState.equals("")) {
				initialState = initState.get(i);
			}
			
			else {
				initialState = initialState + "/" + initState.get(i);
			}
		}
		
		//System.out.println("initialState: " + initialState);
	}
	

	/**
	 * @return Returns a formatted string representation of the DFA. The string
	 *         representation follows the one in the task description
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String Q;
		String Q1 = ""; //main Q
		String T = "";
		String F;
		String F1 = "";
		
		
		
		//Q
		sortArrayListOfArrayLists(DFAfinalSetOfStates);
		for (int i = 0; i < DFAfinalSetOfStates.size(); i++) {
			Q = "";
			for (int j = 0; j < DFAfinalSetOfStates.get(i).size(); j++) {
				if (Q.equals("")) {
					Q = DFAfinalSetOfStates.get(i).get(j);
				}
				
				else {
					Q = Q + "/" + DFAfinalSetOfStates.get(i).get(j);

				}
			}
			
			if (Q1.equals("")) {
				Q1 = Q;
			}
			
			else {
				Q1 = Q1 + ";" + Q;
			}
		}
		
		//T
		sortTstates(DFAtransitionStates);
		for (int i = 0; i < DFAtransitionStates.size(); i++) {
			if (T.equals("")) {
				T = DFAtransitionStates.get(i);
			}
			
			else {
				T = T + ";" + DFAtransitionStates.get(i);
			}
		}
		
		//F
		sortArrayListOfArrayLists(DFAacceptStates);
		for (int i = 0; i < DFAacceptStates.size(); i++) {
			F = "";
			for (int j = 0; j < DFAacceptStates.get(i).size(); j++) {
				if (F.equals("")) {
					F = DFAacceptStates.get(i).get(j);
				}
				
				else {
					F = F + "/" + DFAacceptStates.get(i).get(j);

				}
			}
			
			if (F1.equals("")) {
				F1 = F;
			}
			
			else {
				F1 = F1 + ";" + F;
			}
		}
		
		
		String finalOutputaya = Q1 + "#" + alphabetString + "#" + T + "#" + initialState + "#" + F1  ;
		//System.out.println(finalOutputaya);
		return finalOutputaya;
	}
	
	
	
	
	
	public static void main(String[] args) { 
		
		//String input = "0;1;2;3;4;5;6;7;8;9;10#j;o#0,j,0;0,j,6;0,j,9;0,j,10;0,o,0;0,o,1;0,o,4;0,o,10;1,e,1;1,j,0;1,j,1;1,j,2;1,j,4;1,j,5;1,j,6;1,j,10;1,o,1;1,o,4;1,o,5;1,o,9;1,o,10;2,e,2;2,j,2;2,j,3;2,j,4;2,j,6;2,j,7;2,j,8;2,o,0;2,o,1;2,o,3;2,o,8;2,o,10;3,e,3;3,j,0;3,j,1;3,j,2;3,j,4;3,j,10;3,o,0;3,o,2;3,o,3;3,o,4;3,o,5;3,o,6;3,o,9;4,j,4;4,j,5;4,j,7;4,j,8;4,j,9;4,j,10;4,o,1;4,o,2;4,o,5;4,o,7;4,o,10;5,j,0;5,j,3;5,j,8;5,j,9;5,o,1;5,o,5;5,o,6;5,o,7;5,o,8;5,o,10;6,j,3;6,j,4;6,j,8;6,j,9;6,j,10;6,o,1;6,o,2;6,o,3;6,o,6;6,o,9;6,o,10;7,e,7;7,j,1;7,j,2;7,j,3;7,j,4;7,j,5;7,j,6;7,j,7;7,o,0;7,o,2;7,o,3;7,o,4;7,o,8;8,e,8;8,j,1;8,j,3;8,j,6;8,j,8;8,o,1;8,o,3;8,o,4;8,o,6;8,o,7;8,o,9;8,o,10;9,e,9;9,j,4;9,j,5;9,j,7;9,j,8;9,j,9;9,j,10;9,o,0;9,o,1;9,o,2;9,o,3;9,o,6;9,o,9;9,o,10;10,e,10;10,j,0;10,j,2;10,j,4;10,j,5;10,j,6;10,j,9;10,j,10;10,o,1;10,o,2;10,o,3;10,o,6#10#0;1;2;7";
		//String input = "0;1;2;3#a;b#0,a,0;0,b,0;0,b,1;1,a,2;1,e,2;2,b,3;3,a,3;3,b,3#0#3";
		//String input = "0;1;2;3;4;5;6;7;8;9;10#a;b#0,e,1;1,b,2;2,e,3;3,e,4;3,e,9;4,e,5;4,e,7;5,a,6;6,e,4;6,e,9;7,b,8;8,e,4;8,e,9;9,a,10#0#10";
		//NfaToDfa nfa = new NfaToDfa(input);
		//toString();
		
//		System.out.println(nfa.alphabetString);
//		System.out.println("hena");
		
	
//		for (int i = 0; i < nfa.DFAsetOfStates.size(); i++) {
//			System.out.println(nfa.DFAsetOfStates.get(i));
//		}
		
//		
//		for (int i = 0; i < nfa.DFAfinalSetOfStates.size(); i++) {
//		System.out.println(nfa.DFAfinalSetOfStates.get(i));
//	}
		
		
//		for (int i = 0; i < nfa.NFAtransitionStates.length; i++) {
//		System.out.println(nfa.NFAtransitionStates[i]);
//	}
		
		
//		for (int i = 0; i < nfa.nfaAcceptStates.length; i++) {
//		System.out.println(nfa.nfaAcceptStates[i]);
//	}
		
//		for (int i = 0; i < nfa.DFAtransitionStates.size(); i++) {
//		System.out.println(nfa.DFAtransitionStates.get(i));
//	}
		
//		for (int i = 0; i < nfa.DFAacceptStates.size(); i++) {
//		System.out.println(nfa.DFAacceptStates.get(i));
//	}
		
//		System.out.println(nfa.DFAacceptStates);
		
	}

}
