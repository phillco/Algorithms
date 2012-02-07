package algorithms

/**
 * Uses a smart, O(n) function to find the maximum subarray.
 */
def find_max_subarray_smart( array ) {

    int max, max_start, max_end, start, tentative = 0;
    for ( int i = 0; i < array.size(); i++ ) {

        tentative += array[i];

        if ( tentative > max ) {
            max = tentative;
            max_end = i;
            max_start = start;
        }

        if ( tentative < 0 ) {
            tentative = 0;
            start = i + 1;
        }

    }

    return [max: max, start: max_start, end: max_end]
}

/**
 * Uses a brute-force O(n^2) method to find the maximum subarray.
 */
def find_max_subarray_brute( array ) {

    int max, start, end;
    for ( int i = 0; i < array.size(); i++ ) {

        int sum = 0;
        for ( int j = i; j < array.size(); j++ ) {

            sum += array[j];

            // Is this the new max?
            if ( sum > max ) {
                max = sum;
                start = i;
                end = j;
            }
        }
    }

    return [max: max, start: start, end: end]
}

//=====================================
// TESTING
//=====================================

def sucessful_results = [[-5, -10, 8, 20, 3, 8, -50, 39] : [max: 39, start: 2, end: 5],
                         [5, 10, 8, 20, 3, 8, 50, 39] : [max: 143, start: 0, end: 7], // All positive numbers -> the whole array should be returned.
                         [13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7] : [max: 43, start: 7, end: 10]] // The example from the book (remember that they use 1-based indexing).

// Run the test cases with the different methods.
sucessful_results.each { array, result ->

    assert find_max_subarray_smart( array ) == result
    assert find_max_subarray_brute( array ) == result
}

println "Success!"
