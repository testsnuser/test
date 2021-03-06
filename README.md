========================= GIT Integration =======================================

This is Sample Git Repository to Validate the Git Pushes to Remote Instance calls with the updates.
Hopefully, You will learn the integrations though this.

Pre-requistes: 
    
    Make sure Python (with requests module) is installed
    Configure the SNOW_URL, SNOW_USER_NAME, SNOW_USER_PASS
    If you do not want the diff to be included, Set the INCLUDE_DIFF to False

For Testing we will looking into the one of the commits:
    
    commit a2b1adeca4b1d908cd0a9517e4dc39dc73c7b324
    Author: tarun.murala <tarun.murala@servicenow.com>
    Date:   Wed Nov 8 15:24:48 2017 -0800
        STRY0010015: Commit2


Prior commit
    
    commit ba398801e018f5e1be53a3a1d3533a04f3a0c56f
    Author: tarun.murala <tarun.murala@servicenow.com>
    Date:   Wed Nov 8 15:21:40 2017 -0800
        STRY0010015: First Commit


The Post Recieve Hook reads the "Commit2" performs the following:
1. Gets the Commit Details
2. Gets the branch on which the commit was made
3. Get the diff from the previous commit
4. Prepare the JSON with the list of Added, Updated, Renamed or Removed files.
5. Invokes the Instance with Commit message (Assuming the instance details are configured)

The Sample JSON generated as below:

    {
      "commit_id": "a2b1adeca4b1d908cd0a9517e4dc39dc73c7b324",
      "committer": "'tarun.murala@servicenow.com'",
      "branch": "refs/heads/master",
      "added_files": [
        {
          "file_name": "second.js",
          "diff": "@@ -0,0 +1,3 @@\n+function second (message) {\n+    console.log('Hello' + message);\n+}\n\\ No newline at end of file"
        }
      ],
      "deleted_files": [],
      "repo_url": "https://testsnuser@github.com/testsnuser/test.git",
      "renamed_files": [],
      "updated_files": [
        {
          "file_name": "first.js",
          "diff": "@@ -1,3 +1,6 @@\n function first (name) {\n     console.log('Hi ' + name);\n+}\n+function first_udpated (name) {\n+    console.log('Hello ' + name);\n }\n\\ No newline at end of file"
        }
      ],
      "message": "STRY0010015: Commit2",
      "source_versioning": "GIT"
    }


Sample run can made like:

    call_snow_instance('ba398801e018f5e1be53a3a1d3533a04f3a0c56f', 'a2b1adeca4b1d908cd0a9517e4dc39dc73c7b324')



========================= Jenkins Integration =======================================

For posting the Build and Test results, Post Build Actions as to be defined.
The Post Build Actions are coded in the Groovy. 

Post Build Action can be either Declarative or Scripted. 
We have taken the Scripted Groovy Post Build Action approach.

Update the script according to include
  
  SNOW_URL
  SNOW_USER_NAME
  SNOW_USER_PASS

To Include the Log, Set the following to True
  
  INCLUDE_LOG

Refer the following:
When the Build is complete
  
  postBuild.groovy

This will post the results to BUILD_INTEGRATION_API (can be configured).
The sample Build JSON generated:


    {   build:
            {   build_name:TarunBuildProject,
                build_number:13, 
                build_url:job/TarunBuildProject/13/, 
                build_result:SUCCESS, 
                build_source:JENKINS, 
                branch_name: '*/master',
                log:'Started by user jenkins.admin@e.com ...'
            }
    }

When the Tests are complete
  
  postTest.groovy

This will post the results to TEST_INTEGRATION_API (can be configured).
The sample Test JSON generated:
      

      {
        "build": {
          "build_name": "TarunTestProject",
          "build_number": "19",
          "build_url": "job/TarunTestProject/19/",
          "build_source": "JENKINS",
          "branch_name": "*/master",
          "total_count": 2,
          "failed_count": 0,
          "skipped_count": 0
        },
        "tests": {
          "com.tarun.test.AdditionTest": [
            {
              "name": "testAdd",
              "status": "PASSED",
              "url": "/junit/com.tarun.test/AdditionTest/testAdd",
              "details": "null"
            }
          ],
          "com.tarun.test.SubtractionTest": [
            {
              "name": "testSubtract",
              "status": "PASSED",
              "url": "/junit/com.tarun.test/SubtractionTest/testSubtract",
              "details": "null"
            }
          ]
        }
      }



        !!!!! HAPPY INTEGRATING !!!!!

Thanks,
Tarun