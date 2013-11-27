Video: http://www.youtube.com/watch?v=ot5cN2hiOcQ

How to run: 

1. cd joram/samples/src/joram
2. edit names.txt to configure client names and number (each line corresponds to a client; the names must be unique)
3. ant clean compile
4. ant reset single_server
5. open a new tab
6. ant scheduler_admin
7. ant scheduler_client