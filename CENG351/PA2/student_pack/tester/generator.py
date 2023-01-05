import random
import os
import shutil


os.system("javac CengPlaylist.java")
sampleInput = open("./sampleInput1.txt", "r")

lines = sampleInput.readlines()

shutil.rmtree("./testCases", ignore_errors=True)
os.makedirs("./testCases/sampleInput1")
with open("./testCases/sampleInput1/input.txt", "w") as f:
    f.writelines(lines)
    os.makedirs("./testCases/sampleInput1/outputs/print1")
    os.system("echo print1 | cat sampleInput1.txt - | java CengPlaylist 1 0 sampleInput1.txt > ./testCases/sampleInput1/outputs/print1/50.txt")
    

for i in range(1, 11):
    folder_path = "./testCases/shuffle" + str(i)
    os.makedirs(folder_path)

    random.shuffle(lines)
    with open(folder_path + "/input.txt", "w") as f:
        f.writelines(lines)

    for j in (1, 2, 4, 8, 16, 32, 50):
        os.makedirs(folder_path + "/outputs/print1")
        with open(folder_path + "/outputs/print1/" + str(j) + ".txt", "w") as f:
            os.system("echo print1 | cat " + folder_path + "/input.txt - | java CengPlaylist 1 0 " + folder_path + "/input.txt > " + folder_path + "/outputs/print1/" + str(j) + ".txt")
