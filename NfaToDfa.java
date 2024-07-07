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
	private String[] NFAsetOfStates; 
	private String[] NFAtransitionStates;
	private ArrayList<String> DFAtransitionStates = new ArrayList<String>();
	private ArrayList<ArrayList<String>> DFAacceptStates = new ArrayList<ArrayList<String>>();
	private String[] nfaAcceptStates;
	private String initialState = "";
	private String nfaInitialState;

	
	public NfaToDfa(String input) {
		String [] inputArray = input.split("#");
		
		this.alphabetString = inputArray[1];
		
		this.NFAsetOfStates = inputArray[0].split(";");
		InitEpsilonEnclosure(NFAsetOfStates);
		
		this.NFAtransitionStates = inputArray[2].split(";");
		this.nfaAcceptStates = inputArray[4].split(";");
		this.nfaInitialState = inputArray[3];
				
		StateEpsilonClosures(DFAsetOfStates);
		StateTransitionClosures(DFAsetOfStates);
		AlphabetTransitions();
		AcceptedStates();		
		
	}
	
	
	
	
	
public void InitEpsilonEnclosure(String[] nfaStates) {
		
		for (int i = 0; i < nfaStates.length; i++) {
			ArrayList<String> basicEpsilon = new ArrayList<String>();
			basicEpsilon.add(nfaStates[i]);
			int index = Integer.parseInt(nfaStates[i]);
			
			
			while (index >= DFAsetOfStates.size()) {
                DFAsetOfStates.add(null);
            }
			
			DFAsetOfStates.set(index, basicEpsilon);
		}
		
	}
	
	
	
	public void StateEpsilonClosures(ArrayList<ArrayList<String>> dfaStates) {
		for (int i = 0; i < NFAtransitionStates.length; i++) {
			String[] currentTstate = NFAtransitionStates[i].split(",");
			if (currentTstate[1].equals("e")) {
				int DFAindex = Integer.parseInt(currentTstate[0]);
				if (! (dfaStates.get(DFAindex).contains(currentTstate[2]))) {
					dfaStates.get(DFAindex).add(currentTstate[2]);
				}
				
			}
		}
	}
	
	
	public void StateTransitionClosures(ArrayList<ArrayList<String>> dfaStates) {
		for (int i = 0; i < dfaStates.size(); i++) {
			for(int j = 0; j < dfaStates.get(i).size(); j++) {
				int subArrayElement = Integer.parseInt(dfaStates.get(i).get(j));
				ArrayList<String> replacementArray = dfaStates.get(subArrayElement);
				ArrayList<String> currentArray = dfaStates.get(i);
				ArrayList<String> updatedSubArray = isPresentDontAdd(currentArray, replacementArray);
				dfaStates.set(i, updatedSubArray);
				
				
			}
		}
		
		sortSubArrays(dfaStates);
	}
	
	
	public ArrayList<String> isPresentDontAdd(ArrayList<String> ogArray, ArrayList<String> replacementArray) {
		ArrayList<String> updatedSubArray = new ArrayList<String>();
		updatedSubArray.addAll(ogArray);
		for (int i = 0; i < replacementArray.size(); i++) {
			if(!(ogArray.contains(replacementArray.get(i)))) {
				updatedSubArray.add(replacementArray.get(i));
			}
		}
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
                
                else {
                	 int compRemaining = CompareRemaining(t1Num1, t2Num1, minT2Number);
                 	if (compRemaining != 0) {
                         return compRemaining;
                     }
                }
                
                
               

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
		
				int initDFAStateIndex = Integer.parseInt(nfaInitialState);
				DFAfinalSetOfStates.add(DFAsetOfStates.get(initDFAStateIndex));
			    PreserveInitialState(DFAsetOfStates.get(initDFAStateIndex));
		
		
		for (int i = 0; i < DFAfinalSetOfStates.size(); i++) {
				
				for (int k = 0; k < NFAalphabet.length ; k++) {
					NewState(DFAfinalSetOfStates.get(i), NFAalphabet[k]);
			}
		}
	}

	
	
	
	public void NewState(ArrayList<String> state, String alphabet) {
		ArrayList<String> returnState = new ArrayList<String>();
		String newTstate = "";
		
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
			
				DFAtransitionStates.add(newTstate);

				if (! (DFAfinalSetOfStates.contains(returnState))) {
					DFAfinalSetOfStates.add(returnState);
				}
				
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
		
	}
	
	@Override
	public String toString() {
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
		return finalOutputaya;
	}
	

}
