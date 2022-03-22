# petstore_api_test
This test task is part of an interview process

Environment :

    Development tools :  Java 1.8.0_261, RestAssured
    IDE               :  IntelliJ IDEA 2019.2 (Community Edition)
    Operating system  :  Microsoft Windows 10 Pro (ver: 10.0.19042)
    
    
    
Running video : https://www.youtube.com/watch?v=HFPFpn0O95Y

Screenshot    : screenshots\test_results.png

Test execution : the tests were executed from IntelliJ
        
Code : - the source code executes the tests described in the task description

       - the code does some additional testing, such as "trying to delete already deleted pet", "trying to get not existing pet (because it was deleted)
       
       - the test execution order is controlled by priority as parameter of @Test annotations. If priority is not set, then some tests may fail, as the test may try to 
         delete a Pet, that was not uploaded (operation POST must preceed DELETE), etc.
       
       


    
   
