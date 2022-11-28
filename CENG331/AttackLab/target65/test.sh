#bin/bash
for i in 1 2 3; do
    ./hex2raw -i phase$i/ctarget.l$i | ./ctarget -i
done
./hex2raw -i phase4/rtarget.l2 | ./ctarget -i
tar -czvf e2521581.tar.gz phase1/ctarget.l1 phase2/ctarget.l2 phase3/ctarget.l3 phase4/rtarget.l2