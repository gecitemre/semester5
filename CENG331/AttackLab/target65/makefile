all: phase1.raw phase2.raw

phase1.raw: phase1.hex
	./hex2raw -i phase1.hex > phase1.raw

phase2.raw: phase2.hex
	./hex2raw -i phase2.hex > phase2.raw

phase1.hex: phase1.sh
	phase1.sh > phase1.hex

phase2.dump: phase2.s
	gcc -c phase2.s
	objdump -d phase2.o > phase2.dump
	rm phase2.o

phase3.dump: phase3.s
	gcc -c phase3.s
	objdump -d phase3.o > phase3.dump
	rm phase3.o

phase3.raw: phase3.hex
	./hex2raw -i phase3.hex > phase3.raw