all: p1 p2 p3

p1: phase1/phase1.raw

phase1/phase1.raw: phase1/phase1.hex
	./hex2raw -i phase1/phase1.hex > phase1/phase1.raw

p2: phase2/phase2.raw phase2/phase2.dump

phase2/phase2.raw: phase2/phase2.hex
	./hex2raw -i phase2/phase2.hex > phase2/phase2.raw

phase2/phase2.dump: phase2/phase2.o
	objdump -d phase2/phase2.o > phase2/phase2.dump

p3: phase3/phase3.raw phase3/phase3.dump

phase3/phase3.dump: phase3/phase3.o
	objdump -d phase3/phase3.o > phase3/phase3.dump

phase3/phase3.raw: phase3/phase3.hex
	./hex2raw -i phase3/phase3.hex > phase3/phase3.raw