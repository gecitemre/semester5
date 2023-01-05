"""Output generator for CengPlaylist."""

import os

output_file_name = input("Enter output file name: ").removesuffix(".txt")
report = open("report.txt", "w", encoding="utf-8")
os.system("rm *.class")
os.system("javac CengPlaylist.java")


for i in range(0, 11):
    FOLDER_PATH = "./testCases/shuffle" + str(i)
    # os.makedirs(FOLDER_PATH + "/outputs/print1")
    os.system(
        "echo print1 | cat "
        + FOLDER_PATH
        + "/input.txt - | java CengPlaylist 1 0 "
        + FOLDER_PATH
        + "/input.txt > "
        + FOLDER_PATH
        + "/outputs/print1/"
        + output_file_name
        + ".txt"
    )
    report.write(
        "shuffle "
        + str(i)
        + " print1:\n"
        + str(
            os.system(
                "diff "
                + FOLDER_PATH
                + "/outputs/print1/"
                + output_file_name
                + ".txt "
                + FOLDER_PATH
                + "/outputs/print1/emre.txt"
            )
        )
        + "\n"
    )

    # os.makedirs(FOLDER_PATH + "/outputs/print2")
    os.system(
        "echo print2 | cat "
        + FOLDER_PATH
        + "/input.txt - | java CengPlaylist 1 0 "
        + FOLDER_PATH
        + "/input.txt > "
        + FOLDER_PATH
        + "/outputs/print2/"
        + output_file_name
        + ".txt"
    )
    report.write(
        "shuffle "
        + str(i)
        + " print2:\n"
        + str(
            os.system(
                "diff "
                + FOLDER_PATH
                + "/outputs/print2/"
                + output_file_name
                + ".txt "
                + FOLDER_PATH
                + "/outputs/print2/emre.txt"
            )
        )
        + "\n"
    )

    # os.makedirs(FOLDER_PATH + "/outputs/search")
    with open(
        FOLDER_PATH + "/outputs/search/" + output_file_name + ".txt",
        "w",
        encoding="utf-8",
    ) as output_file:
        SEARCH_QUERY = (
            "'" + "\n".join(["search|" + str(j) for j in range(1, 51, 10)]) + "'"
        )
        for j in range(1, 51, 10):
            os.system(
                "printf "
                + SEARCH_QUERY
                + " | cat "
                + FOLDER_PATH
                + "/input.txt - | java CengPlaylist 1 0 "
                + FOLDER_PATH
                + "/input.txt >> "
                + FOLDER_PATH
                + "/outputs/search/"
                + output_file_name
                + ".txt"
            )
    report.write(
        "shuffle "
        + str(i)
        + " search:\n"
        + str(
            os.system(
                "diff "
                + FOLDER_PATH
                + "/outputs/search/"
                + output_file_name
                + ".txt "
                + FOLDER_PATH
                + "/outputs/search/emre.txt"
            )
        )
        + "\n"
    )
    print("shuffle " + str(i) + " done")

report.close()
