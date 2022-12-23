ac = int(input())

head = f"""#/* $begin abscopy-ys */
##################################################################
# abscopy.ys - copy the absolute values of a src block of n words to dst.
# Return the sum of copied (absolute) values.
#
# name: Emre Geçit
# id: 2521581
# I have tried different configurations for loop count.
# Best performance is achieved with 5 loops.

##################################################################
# Do not modify this portion
# Function prologue.
# %rdi = src, %rsi = dst, %rdx = n
abscopy:
##################################################################
# You can modify this portion
        irmovq $1, %r11         # %r11 = 1, will be used inside the loop
        irmovq $8, %r8          # %r8 = 8, will be used inside the loop
        irmovq ${ac*8}, %r9\n"""

i = 0
AC_loader = f"irmovq ${ac}, %rcx\n"

check = """        # Loop header
        xorq %rax,%rax          # sum = 0;
Check:
        subq %rcx, %rdx         # %rdx -= %rcx
        jl Remaining            # if n < AC, goto Remaining\n
"""


remaining = """
        addq %r9, %rdi          # src += AC*8
        addq %r9, %rsi          # dst += AC*8
        jmp Check               # goto Check

Remaining:
        addq %rcx, %rdx         # %rdx += %rcx
Loop:
        je Done                # if n == 0, goto Done
        mrmovq (%rdi), %r10     # read val from src...
        andq %r10, %r10         # val >= 0?
        jge Positive            # if so, skip negating
        isubq $0, %r10          # Use isubq to negate val
Positive:
        addq %r10, %rax         # sum += absval   
        rmmovq %r10, (%rsi)     # ...and store it to dst
        # irmovq $1, %r10 | This costs an extra cycle each loop and unnecessary. Instead dedicated register %r11 is used.
        # irmovq $8, %r10 | This costs an extra cycle each loop and unnecessary. Instead dedicated register %r8 is used.
        addq %r8, %rsi          # dst++
        addq %r8, %rdi          # src++
        subq %r11, %rdx         # n--
        andq %rdx, %rdx         # n > 0?
        jg Loop                 # if so, goto Loop:
        
##################################################################
# Do not modify the following section of code
# Function epilogue.
Done:
        ret
##################################################################
# Keep the following label at the end of your function
End:
#/* $end abscopy-ys */\n"""

y86 = head + AC_loader + check
for i in range(1, ac + 1):
    y86 += f"""Loop{i}:
        mrmovq {8*(i-1)}(%rdi), %r10     # read val from src...
        andq %r10, %r10         # val >= 0?
        jge Positive{i}           # if so, skip negating
        isubq $0, %r10          # Use isubq to negate val
Positive{i}:
        addq %r10, %rax         # sum += absval   
        rmmovq %r10, {8*(i-1)}(%rsi)     # ...and store it to dst
        # irmovq $1, %r10 | This costs an extra cycle each loop and unnecessary. Instead dedicated register %r11 is used.
        # irmovq $8, %r10 | This costs an extra cycle each loop and unnecessary. Instead dedicated register %r8 is used.\n"""

y86 += remaining

with open("generated_abscopy.ys", "w") as f:
    f.write(y86)