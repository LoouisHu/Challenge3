package com.example.noblenotebooklouis.challenge3;

/**
 * Created by Noble Notebook Louis on 22-Sep-17.
 */

public class Variance {
    /**
     * Calculates the mean of a buffer
     *
     * @param buffer    The buffer of which the mean needs to be calculated
     * @return          The mean of this buffer
     */
    private static double mean (double[] buffer) {
        double total = 0;
        for (double data : buffer) {
            total = total + data;
        }
        return total/buffer.length;
    }

    /**
     * Calculates the variance of a buffer
     *
     * @param buffer    The buffer of which the variance needs to be calculated
     * @return          The variance of this buffer
     */
    public static double variance (double[] buffer) {
        double variance = 0;
        double mean = mean(buffer);
        for (double data : buffer) {
            variance = variance + ((data - mean)*(data - mean));
        }
        return variance/buffer.length;
    }
}
