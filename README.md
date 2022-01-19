### Test project for MyNetDairy team. 
The goal is to make an app which allows to show users list, to add or edit new users. Requirements:
https://docs.google.com/drawings/d/1UPHFOO1_IXLpoRi9mtEGVXmdJD6QHLQKiuRyY1xCTTs/edit

### Modules
#### app
Application presentation layer which gathers together all app features
### common
Contains some common for each module logic
### common-di
Contains some common related to dependency injection logic
### common-ui
Contains some common related to user interface logic
### feature-clients-common
Contains domain and data layers for clients related logic, such as Client entity and ClientsRepository interface
### feature-clients-inmemory-repository
Contains in memory storage implementation of ClientsRepository
### feature-clients-list
Presentation layer of clients list feature
### feature-client-edit
Feature module which contains UI and logic of clients creating and editing
