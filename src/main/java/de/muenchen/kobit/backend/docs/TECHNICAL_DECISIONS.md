# technical decisions

This section contains information on technical decisions made. 

## competence and decision tree

The competence and decision tree were supposed to be static. This has changed towards the end of the project. 
Due to lak of time the refactoring of the competences could just been started. 

Competences are now hardcoded in the enum Class. This is used for the decision tree and competences assigned to a contact point. 
Also, there is a database table competence. This contains all enums of the Competence class. This is needed to represent a path. 
A Path is needed to clarify on which position a certain contact point comes after finishing the decision tree. 

### possible on going 

The table competence will replace the enum class completely. This will enable admins to change the decision tree. New
competences could be created. Then the paths of the tree were also changeable and no longer hard coded. 

