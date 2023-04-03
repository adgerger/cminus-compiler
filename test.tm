* Standard prelude:
0: LD   6,0(0)	 load gp with maxaddress
1: LDA  5,0(6)	 copy to gp to fp
2: ST   0,0(0)	 clear location 0
* Jump around I/O functions
* Code for Input Routine
4: ST 0,-1(5)	Store return
5: IN 0,0,0	Input
6: LD 7,-1(5)	Return caller
* Code for Output Routine
7: ST 0,-1(5)	Store return
8: LD 0,-2(5)	Load output value
9: OUT 0,0,0	Output
10: LD 7,-1(5)	Return caller
3: LDA 7,7(7)	Jump around I/O code
* End of standard prelude.
