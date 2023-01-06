/********************************************************
 * Kernels to be optimized for the CS:APP Performance Lab
 ********************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "defs.h"
/*
 * Please fill in the following team_t struct
 */
team_t team = {
    "TEAM19", /* Team Name */

    "e2521581",   /* First student ID */
    "Emre Geçit", /* First student name */

    "e2448702",       /* Second student ID */
    "Arda Numanoğlu", /* Second student name */

    "e31",             /* Third student ID */
    "Taner Sarp Tonay" /* Third student Name */
};

/********************
 * CONVOLUTION KERNELvoid register_conv_functions() {
add_conv_function(&convolution, convolution_descr);
}
 ********************/

/***************************************************************
 * Your different versions of the convolution functions  go here
 ***************************************************************/

/*
 * naive_conv - The naive baseline version of convolution
 */
char naive_conv_descr[] = "naive_conv: Naive baseline implementation";
void naive_conv(int dim, pixel *src, pixel *ker, unsigned *dst)
{
    int i, j, k, l;

    for (i = 0; i < dim - 8 + 1; i++)
        for (j = 0; j < dim - 8 + 1; j++)
        {
            dst[RIDX(i, j, dim)] = 0;
            for (k = 0; k < 8; k++)
                for (l = 0; l < 8; l++)
                {
                    dst[RIDX(i, j, dim)] += src[RIDX((i + k), (j + l), dim)].red * ker[RIDX(k, l, 8)].red;
                    dst[RIDX(i, j, dim)] += src[RIDX((i + k), (j + l), dim)].green * ker[RIDX(k, l, 8)].green;
                    dst[RIDX(i, j, dim)] += src[RIDX((i + k), (j + l), dim)].blue * ker[RIDX(k, l, 8)].blue;
                }
        }
}

char optimized_conv_descr[] = "optimized_conv: Optimized implementation";
void optimized_conv(int dim, pixel *src, pixel *ker, unsigned *dst)
{
    int i, j, k, l;
    for (i = 0; i < dim - 7; i++)
        for (j = 0; j < dim - 7; j++)
            dst[RIDX(i, j, dim)] = 0;
    // i, j is the kernel's top left corner
    int i_dim = 0;
    for (i = 0; i < dim - 7; i++) {
        int k_dim = 0;
        int ker_index_base = 0;
        for (k = 0; k < 8; k++) {
            int i_dim_j_dim_k_l = i_dim + k_dim;
            for (j = 0; j < dim - 7; j++) {
            int ker_index = ker_index_base;
                int sum = dst[i_dim + j];
                {
                    sum += src[i_dim_j_dim_k_l].red * ker[ker_index].red;
                    sum += src[i_dim_j_dim_k_l].green * ker[ker_index].green;
                    sum += src[i_dim_j_dim_k_l].blue * ker[ker_index].blue;
                    ker_index++;
                    // loop unrolling
                    sum += src[i_dim_j_dim_k_l + 1].red * ker[ker_index].red;
                    sum += src[i_dim_j_dim_k_l + 1].green * ker[ker_index].green;
                    sum += src[i_dim_j_dim_k_l + 1].blue * ker[ker_index].blue;
                    ker_index++;

                    sum += src[i_dim_j_dim_k_l + 2].red * ker[ker_index].red;
                    sum += src[i_dim_j_dim_k_l + 2].green * ker[ker_index].green;
                    sum += src[i_dim_j_dim_k_l + 2].blue * ker[ker_index].blue;
                    ker_index++;

                    sum += src[i_dim_j_dim_k_l + 3].red * ker[ker_index].red;
                    sum += src[i_dim_j_dim_k_l + 3].green * ker[ker_index].green;
                    sum += src[i_dim_j_dim_k_l + 3].blue * ker[ker_index].blue;
                    ker_index++;

                    sum += src[i_dim_j_dim_k_l + 4].red * ker[ker_index].red;
                    sum += src[i_dim_j_dim_k_l + 4].green * ker[ker_index].green;
                    sum += src[i_dim_j_dim_k_l + 4].blue * ker[ker_index].blue;
                    ker_index++;

                    sum += src[i_dim_j_dim_k_l + 5].red * ker[ker_index].red;
                    sum += src[i_dim_j_dim_k_l + 5].green * ker[ker_index].green;
                    sum += src[i_dim_j_dim_k_l + 5].blue * ker[ker_index].blue;
                    ker_index++;

                    sum += src[i_dim_j_dim_k_l + 6].red * ker[ker_index].red;
                    sum += src[i_dim_j_dim_k_l + 6].green * ker[ker_index].green;
                    sum += src[i_dim_j_dim_k_l + 6].blue * ker[ker_index].blue;
                    ker_index++;

                    sum += src[i_dim_j_dim_k_l + 7].red * ker[ker_index].red;
                    sum += src[i_dim_j_dim_k_l + 7].green * ker[ker_index].green;
                    sum += src[i_dim_j_dim_k_l + 7].blue * ker[ker_index].blue;
                    ker_index++;
                }
                dst[i_dim + j] = sum;
                i_dim_j_dim_k_l += 1;
            }
            k_dim += dim;
            ker_index_base += 8;
        }
        i_dim += dim;
    }
}

/*
 * convolution - Your current working version of convolution
 * IMPORTANT: This is the version you will be graded on
 */
char convolution_descr[] = "Convolution: Current working version";
void convolution(int dim, pixel *src, pixel *ker, unsigned *dst)
{
    int i, j, k, l, i_dim = 0;
    for (i = 0; i < dim - 8 + 1; i++)
    {
        for (j = 0; j < dim - 8 + 1; j++)
        {
            int sum = 0;
            int ker_index = 0;
            int ik_dim_j = i_dim + j;
            for (k = 0; k < 8; k++)
            {
                // loop unrolling
                sum += src[ik_dim_j].red * ker[ker_index].red;
                sum += src[ik_dim_j].green * ker[ker_index].green;
                sum += src[ik_dim_j].blue * ker[ker_index].blue;
                ker_index++;
                sum += src[ik_dim_j + 1].red * ker[ker_index].red;
                sum += src[ik_dim_j + 1].green * ker[ker_index].green;
                sum += src[ik_dim_j + 1].blue * ker[ker_index].blue;
                ker_index++;
                sum += src[ik_dim_j + 2].red * ker[ker_index].red;
                sum += src[ik_dim_j + 2].green * ker[ker_index].green;
                sum += src[ik_dim_j + 2].blue * ker[ker_index].blue;
                ker_index++;
                sum += src[ik_dim_j + 3].red * ker[ker_index].red;
                sum += src[ik_dim_j + 3].green * ker[ker_index].green;
                sum += src[ik_dim_j + 3].blue * ker[ker_index].blue;
                ker_index++;
                sum += src[ik_dim_j + 4].red * ker[ker_index].red;
                sum += src[ik_dim_j + 4].green * ker[ker_index].green;
                sum += src[ik_dim_j + 4].blue * ker[ker_index].blue;
                ker_index++;
                sum += src[ik_dim_j + 5].red * ker[ker_index].red;
                sum += src[ik_dim_j + 5].green * ker[ker_index].green;
                sum += src[ik_dim_j + 5].blue * ker[ker_index].blue;
                ker_index++;
                sum += src[ik_dim_j + 6].red * ker[ker_index].red;
                sum += src[ik_dim_j + 6].green * ker[ker_index].green;
                sum += src[ik_dim_j + 6].blue * ker[ker_index].blue;
                ker_index++;
                sum += src[ik_dim_j + 7].red * ker[ker_index].red;
                sum += src[ik_dim_j + 7].green * ker[ker_index].green;
                sum += src[ik_dim_j + 7].blue * ker[ker_index].blue;
                ker_index++;

                ik_dim_j += dim;
            }
            dst[i_dim + j] = sum;
        }
        i_dim += dim;
    }
    // int i,j,k,l;

    // int i_dim = 0;
    // for(i = 0; i < dim-8+1; i++)
    // {
    //     int i_dim_j = i_dim;
    //     for(j = 0; j < dim-8+1; j++) {
    //         int d = 0;
    //         int s_base = i_dim_j, t = 0;
    //         for(k = 0; k < 8; k++) {
    //             int s = s_base;
    //             for (l = 0; l < 8; l++) { // TODO: create 8 variables, loop unrolling
    //                 d += src[s].red * ker[t].red + src[s].green * ker[t].green + src[s].blue * ker[t].blue;
    //                 s++;
    //                 t++;
    //             }
    //             s_base += dim;
    //         }
    //         dst[i_dim_j] = d;
    //         i_dim_j ++;
    //     }
    // }
}

/*********************************************************************
 * register_conv_functions - Register all of your different versions
 *     of the convolution functions  with the driver by calling the
 *     add_conv_function() for each test function. When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.
 *********************************************************************/

void register_conv_functions()
{
    add_conv_function(&naive_conv, naive_conv_descr);
    add_conv_function(&convolution, convolution_descr);
    add_conv_function(&optimized_conv, optimized_conv_descr);
    /* ... Register additional test functions here */
}

/************************
 * AVERAGE POOLING KERNEL
 ************************/

/*********************************************************
 * Your different versions of the average pooling  go here
 *********************************************************/

/*
 * naive_average_pooling - The naive baseline version of average pooling
 */
char naive_average_pooling_descr[] = "Naive Average Pooling: Naive baseline implementation";
void naive_average_pooling(int dim, pixel *src, pixel *dst)
{
    int i, j, k, l;

    for (i = 0; i < dim / 2; i++)
        for (j = 0; j < dim / 2; j++)
        {
            dst[RIDX(i, j, dim / 2)].red = 0;
            dst[RIDX(i, j, dim / 2)].green = 0;
            dst[RIDX(i, j, dim / 2)].blue = 0;
            for (k = 0; k < 2; k++)
            {
                for (l = 0; l < 2; l++)
                {
                    dst[RIDX(i, j, dim / 2)].red += src[RIDX(i * 2 + k, j * 2 + l, dim)].red;
                    dst[RIDX(i, j, dim / 2)].green += src[RIDX(i * 2 + k, j * 2 + l, dim)].green;
                    dst[RIDX(i, j, dim / 2)].blue += src[RIDX(i * 2 + k, j * 2 + l, dim)].blue;
                }
            }
            dst[RIDX(i, j, dim / 2)].red /= 4;
            dst[RIDX(i, j, dim / 2)].green /= 4;
            dst[RIDX(i, j, dim / 2)].blue /= 4;
        }
}

/*
 * average_pooling - Your current working version of average_pooling
 * IMPORTANT: This is the version you will be graded on
 */
char average_pooling_descr[] = "Average Pooling: Current working version";
void average_pooling(int dim, pixel *src, pixel *dst)
{

    naive_average_pooling(dim, src, dst);
}

/******************************************************************************
 * register_average_pooling_functions - Register all of your different versions
 *     of the average pooling  with the driver by calling the
 *     add_average_pooling_function() for each test function. When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.
 ******************************************************************************/

void register_average_pooling_functions()
{
    add_average_pooling_function(&naive_average_pooling, naive_average_pooling_descr);
    add_average_pooling_function(&average_pooling, average_pooling_descr);
    /* ... Register additional test functions here */
}
