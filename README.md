# MySQL-Android-Fetch-Data
This is volley library description and use of library. In this library we are fetching MySQL database containing images, data (text,number,etc) in an android application. There is no way to connect any type of not secure site to an Android application.

Read all the points carefully which I describe as follows:

- You must have a MySQL server side database. (obviously).

- Then inside your android application you need these files:

            |--- These three .java files ---|
            
            1. The MainActiviy.java/(Activity in which you want MySQL database).
            2. The another java file ActivityAdapter.java.
            3. If you want to fetch more than 1 row of a table then you require a class conatining members of row corrosponding column to get and put into an activity (MainActivity for example.)
  - Inside layout folder
  
            |--- These two .xml files ---|       
            1. For e.g. actitvity_main.xml (containing recyclerView Layout(it is really easy)).
            2. RecyclerLayout containing all elements to be updated.
  
- Your site on which you are fetching data must be SSL (secure) otherwise it will not work. And there is no way to connect any type of not secure site to an Android application.

- Here are requirement of PHP file.

            |---Here is one PHP file---|
            1. This file contain something like SELECT * FROM <table-name> WHERE condition;

- You can add like button as instagram/facebook or click to whole object to open new activity.

# Output will look like this

![screenshot-1](https://github.com/mahisharma-cs/MySQL-Android-Fetch-Data/blob/master/MySQL-Android-Fetch-Data/zimages/4.png)
&nbsp 
![screenshot-2](https://github.com/mahisharma-cs/MySQL-Android-Fetch-Data/blob/master/MySQL-Android-Fetch-Data/zimages/5.png)
