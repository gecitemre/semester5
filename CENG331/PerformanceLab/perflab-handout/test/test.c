void optimized_conv(int dim, int *src, int *ker, unsigned *dst)
{
    int i_base, j, k, i, l, j_base, src_i, dst_i, ker_i;
    const int B = 16;

    for (j_base = 0; j_base < dim - 7; j_base += B)
    {
        for (i = 0; i < dim - 7; i++)
        {
            for (j = j_base; j < j_base + B; j++)
            {
                for (src_i = i; src_i < i + 8; src_i++)
                    for (ker_i = i; ker_i <= src_i; ker_i++)
                    {
                        printf(" ");
                    }
            }
        }
    }
}

int main()
{
    int dim = 32;
    int *src = malloc(dim * dim * sizeof(int));
    int *ker = malloc(8 * 8 * sizeof(int));
    unsigned *dst = malloc(dim * dim * sizeof(unsigned));
    optimized_conv(dim, src, ker, dst);
    return 0;
}