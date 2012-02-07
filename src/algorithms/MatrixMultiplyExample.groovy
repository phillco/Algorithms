package algorithms

/* MULTIPLIES A and B =============
 using Strassen's O(n^2.81) method

   A = [1  3]      B = [6  8]
       [7  5]          [4  2]

 C = A * B = ?
 =================================*/

// Step 1: Split A and B into half-sized matrices of size 1x1 (scalars).
def a11 = 1
def a12 = 3
def a21 = 7
def a22 = 5
def b11 = 6
def b12 = 8
def b21 = 4
def b22 = 2

// Define the "S" matrix.
def s1 = b12 - b22 // 6
def s2 = a11 + a12 // 4
def s3 = a21 + a22 // 12
def s4 = b21 - b11 // -2
def s5 = a11 + a22 // 6
def s6 = b11 + b22 // 8
def s7 = a12 - a22 // -2
def s8 = b21 + b22 // 6
def s9 = a11 - a21 // -6
def s10 = b11 + b12 // 14

// Define the "P" matrix.
def p1 = a11 * s1 // 6
def p2 = s2 * b22 // 8
def p3 = s3 * b11 // 72
def p4 = a22 * s4 // -10
def p5 = s5 * s6 // 48
def p6 = s7 * s8 // -12
def p7 = s9 * s10 // -84

// Fill in the resultant "C" matrix.
def c11 = p5 + p4 - p2 + p6 // 18
def c12 = p1 + p2 //  14
def c21 = p3 + p4 // 62
def c22 = p5 + p1 - p3 - p7 // 66

/* RESULT: =================

     C = [ 18   14]
         [ 62   66]

=========================*/