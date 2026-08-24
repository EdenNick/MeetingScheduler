## Project

MeetingScheduler

## Project Description

A program that automatically schedules optimal meeting times based off of individual prefrences.

When a meeting needs to be scheduled, the user selects who they want to attend the meeting, the program will then show a set of times
that work for all selected individuals or the times that work for most people in the case that not all preferences overlap in some way.

## Features

1. user input for a worker and there prefered time in the form of a text prompt or file input

info includes:
-name
-employee ID
-available meeting days
-available meeting times

2. user selection to choose:
-who will attend the meeting
-date/time of the meeting

3. option to save a meeting and its information in a file
-save one or more meetings

## File Descriptions

PROG - PROGRAM: Denotes the File is a part of the program and necessary for its operation.

DATA: Denotes a file that exists purely to store information.

FILE: Denotes a file whose function(s) include Read, Write or Deleting a file.

INFO: Denotes a file whose function(s) include recieve, organize or moving information typically user submitted info.

TEST: Denotes a file whose Function is to test various aspects of the program.


## project blueprint

1. start program

- four options avaiable (buttons)
    a. add info
    b. select people for a meeting
    c. Instructions
    d. exit

- exit: exits the program

2. Select add info

- two options available
    a. add info manually through app
    b. add info through file (specific format - contains more than one person)

- Return: returns to previous page

3. Select people for meeting

- can choose
    a. specific day of the week
    b. time(s) of day
    c. people in the meeting

- program will display meeting times with option to save one or more meetings to a file

- Return: returns to previous page
