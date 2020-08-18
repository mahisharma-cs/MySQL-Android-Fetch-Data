# MySQL-Android-Fetch-Data
This is volley library description and use of library. In this library we are fetching MySQL database containing images, data (text,number,etc) in an android application. There is no way to connect any type of not secure site to an Android application.

Read all the points carefully which I describe as follows:

- You must have a MySQL server side database. (obviously).

- Then inside your android application you need these files:

            |--- These three .java files ---|
            
  (i) The MainActiviy.java/(Activity in which you want MySQL database).
  (ii) The another java file ActivityAdapter.java
  (iii) If you want to fetch more than 1 row of a table then you require a class conatining members of row corrosponding column to get and put into an activity (MainActivity for example.)
  
            |--- These two .xml files ---|
            
  (i) For e.g. actitvity_main.xml
  (ii) RecyclerLayout containing all elements to be updated.
  
- Your site on which you are fetching data must be SSL (secure) otherwise it will not work. And there is no way to connect any type of not secure site to an Android application.

- You can add like button as instagram/facebook or click to whole object to open new activity.
