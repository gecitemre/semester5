all: p1 p2 p3 p4

p1: phase1/phase1.raw

phase1/phase1.raw: phase1/ctarget.l1
	./hex2raw -i phase1/ctarget.l1 > phase1/phase1.raw

p2: phase2/phase2.raw phase2/phase2.dump

phase2/phase2.raw: phase2/ctarget.l2
	./hex2raw -i phase2/ctarget.l2 > phase2/phase2.raw

phase2/phase2.dump: phase2/phase2.o
	objdump -d phase2/phase2.o > phase2/phase2.dump

p3: phase3/phase3.raw phase3/phase3.dump

phase3/phase3.dump: phase3/phase3.o
	objdump -d phase3/phase3.o > phase3/phase3.dump

phase3/phase3.raw: phase3/ctarget.l3
	./hex2raw -i phase3/ctarget.l3 > phase3/phase3.raw

p4: phase4/phase4.raw

phase4/phase4.raw: phase4/rtarget.l2
	./hex2raw -i phase4/rtarget.l2 > phase4/phase4.raw