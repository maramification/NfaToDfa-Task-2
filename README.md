# NfaToDfa-Task-2

## German University in Cairo
### Department of Computer Science
### Assoc. Prof. Haythem O. Ismail

### CSEN1002 Compilers Lab, Spring Term 2024
**Task 2: Non-Deterministic Finite Automata to Deterministic Finite Automata**

## Overview
This project involves implementing the classical algorithm for constructing a deterministic finite automaton (DFA) equivalent to a given non-deterministic finite automaton (NFA). The implementation should follow the guidelines and requirements outlined below.

## Objective
Implement the classical algorithm to convert a given NFA into an equivalent DFA. An NFA is defined by a quintuple (Q, Σ, δ, q0, F), where:
- Q: A non-empty, finite set of states.
- Σ: A non-empty, finite set of symbols (alphabet).
- δ: The transition function, δ: Q × (Σ ∪ {ε}) → P(Q).
- q0: The start state (q0 ∈ Q).
- F: The set of accept states (F ⊆ Q).

## Requirements

1. **Assumptions:**
   - The alphabet Σ is always a subset of the Latin alphabet (excluding 'e').
   - The letter 'e' represents ε.
   - The set of NFA states Q is always of the form {0, ..., n}, for some n ∈ N.

2. **Implementation:**
   - Implement a class constructor `NfaToDfa` and a method `toString`.
   - `NfaToDfa` takes one parameter, a string describing an NFA in the format `Q#A#T#I#F`:
     - `Q`: Semicolon-separated sequence of sorted integer literals representing the set of states.
     - `A`: Semicolon-separated sequence of alphabetically sorted symbols representing the input alphabet.
     - `T`: Semicolon-separated sequence of triples representing the transition function. Each triple is a comma-separated sequence `i,a,j`, where `i` is a state in Q, `a` is a symbol in A or 'e', and `j` is a state in Q.
     - `I`: Integer literal representing the initial state.
     - `F`: Semicolon-separated sequence of sorted integer literals representing the set of accept states.

   Example:
   - For an NFA with the state diagram below, the string representation might be:
     ```
     0;1;2;3#a;b#0,a,0;0,b,0;0,b,1;1,a,2;1,e,2;2,b,3;3,a,3;3,b,3#0#3
     ```

3. **Output Format:**
   - `toString` returns a string representing the constructed DFA in the format `Q#A#T#I#F`:
     - DFA states are sets of states of the original NFA, represented as `/`-separated sequences of numerals.
     - DFA states are sorted by their first numerals, and within the same numeral, by their suffixes.
     - A DFA state corresponding to the empty set of NFA states is represented by `-1`.
   
   Example:
   - For the above NFA, the equivalent DFA might be represented as:
     ```
     0;0/1/2;0/1/2/3;0/2;0/2/3;0/3#a;b#0,a,0;0,b,0/1/2;0/1/2,a,0/2;0/1/2,b,0/1/2/3;0/1/2/3,a,0/2/3;0/1/2/3,b,0/1/2/3;0/2,a,0;0/2,b,0/1/2/3;0/2/3,a,0/3;0/2/3,b,0/1/2/3;0/3,a,0/3;0/3,b,0/1/2/3#0#0/1/2/3;0/2/3;0/3
     ```
     
For any further details or clarifications, refer to the lab manual or contact the course instructor.
