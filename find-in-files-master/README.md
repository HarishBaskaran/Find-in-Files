Find in files
====

This is a Java Eclipse project.
The main goal is to search text inside files.

There are 3 text files with configuration:
* **search-paths.txt**: each line is a root folder. The program searchs inside subfolders.
* **search-words.txt**: each line is a phrase to search. This is **not** a regex. The program will search for the exact text. The program searches **one line at time**, so if you need to search in several lines, this program will not work.
* **search-constraints.txt**: the content is optional. You can filter the files by name and extension. **Each value is a Java Regex**.

The output of the program is the full path of the files found, the line number and the content found.