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

    "e2448918",        /* Third student ID */
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

char convolution_descr[] = "Convolution: Current working version";
void convolution(int dim, pixel *src, pixel *ker, unsigned *dst)
{
    int i = dim - 7, j, src_subtract = 7 * dim + 6;
    while (i--)
    {
        j = dim - 7;
        while(j--)
        {
            int sum = 0;
            // int ik_dim_j = i_dim + j;

            // loop unrolling
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src += dim - 7;

            // ik_dim_j += dim;

            // loop unrolling
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src += dim - 7;

            // ik_dim_j += dim;

            // loop unrolling
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src += dim - 7;

            // ik_dim_j += dim;

            // loop unrolling
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src += dim - 7;

            // ik_dim_j += dim;

            // loop unrolling
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src += dim - 7;

            // ik_dim_j += dim;

            // loop unrolling
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src += dim - 7;

            // ik_dim_j += dim;

            // loop unrolling
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src += dim - 7;

            // ik_dim_j += dim;

            // loop unrolling
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            ker++;
            src++;
            sum += src->red * ker->red;
            sum += src->green * ker->green;
            sum += src->blue * ker->blue;
            src -= src_subtract;
            ker -= 63;
            *(dst++) = sum;
        }
        dst += 7;
        src += 7;
    }
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
    int i, j, red, green, blue, dimension = dim >> 1, index_temp = 0, x = 0;
    ;
    for (i = 0; i < dimension; i++)
    {
        for (j = 0; j < dimension; j++)
        {
            red = (src[x].red + src[x + 1].red + src[x + dim].red + src[x + 1 + dim].red) >> 2;
            green = (src[x].green + src[x + 1].green + src[x + dim].green + src[x + 1 + dim].green) >> 2;
            blue = (src[x].blue + src[x + 1].blue + src[x + dim].blue + src[x + 1 + dim].blue) >> 2;
            dst[index_temp + j] = (pixel){red, green, blue};
            x += 2;
        }
        x += dim;
        index_temp += dimension;
    }
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
