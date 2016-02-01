# Build-It-Bigger
Repository for project 4 for Udacity's Android nanodegreee.

This project touches on building apps with multiple product flavors (paid vs. free) as well as working with a multi-module project that is easily configured with gradle. The three modules are:

1) Google Cloud Endpoint server

2) Android library

3) Java Library

The app uses a GCE module which is a server that returns jokes via the Java library module (randomly chosen jokes!). The app (client) uses an AsyncTask to read jokes from the endpoint, and show them with the Android library module.
There was extra work done to create connected android tests, as well as supporting ads in the free version vs. the paid version. 
