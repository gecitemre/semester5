"""Output generator for CengPlaylist."""

import os

output_file_name = input("Enter output file name: ")
report = open("./testCases/report.txt", "w", encoding="utf-8")
os.system("rm *.class")
os.system("javac CengPlaylist.java")


for shuffle in range(0, 2):
    for order in (1, 2, 4, 8):
        FOLDER_PATH = "./testCases/shuffle" + str(shuffle)
        # os.makedirs(FOLDER_PATH + "/outputs/print1")
        os.system(
            "echo print1 | cat "
            + FOLDER_PATH
            + "/input.txt - | java CengPlaylist "
            + str(order)
            + " 0 "
            + FOLDER_PATH
            + "/input.txt > "
            + FOLDER_PATH
            + "/outputs/print1/"
            "order" + str(order) + output_file_name + ".txt"
        )
        report.write(
            "shuffle "
            + str(shuffle)
            + " print1:\n"
            + str(
                os.system(
                    "diff " + FOLDER_PATH + "/outputs/print1/"
                    "order"
                    + str(order)
                    + output_file_name
                    + ".txt "
                    + FOLDER_PATH
                    + "/outputs/print1/" + "order" + str(order) + "emre.txt"
                )
            )
            + "\n"
        )

        # os.makedirs(FOLDER_PATH + "/outputs/print2")
        os.system(
            "echo print2 | cat "
            + FOLDER_PATH
            + "/input.txt - | java CengPlaylist "
            + str(order)
            + " 0 "
            + FOLDER_PATH
            + "/input.txt > "
            + FOLDER_PATH
            + "/outputs/print2/"
            "order" + str(order) + output_file_name + ".txt"
        )
        report.write(
            "shuffle "
            + str(shuffle)
            + " print2:\n"
            + str(
                os.system(
                    "diff " + FOLDER_PATH + "/outputs/print2/"
                    "order"
                    + str(order)
                    + output_file_name
                    + ".txt "
                    + FOLDER_PATH
                    + "/outputs/print2/" + "order" + str(order) + "emre.txt"
                )
            )
            + "\n"
        )

        # os.makedirs(FOLDER_PATH + "/outputs/search")
        with open(
            FOLDER_PATH + "/outputs/search/"
            "order" + str(order) + output_file_name + ".txt",
            "w",
            encoding="utf-8",
        ) as output_file:
            SEARCH_QUERY = (
                "'" + "\n".join(["search|" + str(j) for j in range(1, 51, 10)]) + "'"
            )
            for j in range(10, 51, 10):
                os.system(
                    "printf "
                    + SEARCH_QUERY
                    + " | cat "
                    + FOLDER_PATH
                    + "/input.txt - | java CengPlaylist "
                    + str(order)
                    + " 0 "
                    + FOLDER_PATH
                    + "/input.txt >> "
                    + FOLDER_PATH
                    + "/outputs/search/"
                    "order" + str(order) + output_file_name + ".txt"
                )
        report.write(
            "shuffle "
            + str(shuffle)
            + " search:\n"
            + str(
                os.system(
                    "diff " + FOLDER_PATH + "/outputs/search/"
                    "order"
                    + str(order)
                    + output_file_name
                    + ".txt "
                    + FOLDER_PATH
                    + "/outputs/search/" + "order" + str(order) + "emre.txt"
                )
            )
            + "\n"
        )
        print("shuffle " + str(shuffle) + " done")

report.close()
